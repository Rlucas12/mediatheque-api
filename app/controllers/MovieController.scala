package controllers

import java.util.UUID

import io.circe.syntax._
import models.json._
import models.json.news.NewMovieJson
import models.news.NewMovie
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import services.MovieService

import scala.concurrent.ExecutionContext

class MovieController(movieService: MovieService, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Circe with MovieJson with NewMovieJson {

  def add() = Action(circe.json[NewMovie]).async { implicit request =>
    movieService
      .add(request.body)
      .map(movie => Ok(movie.asJson))
  }

  def list() = Action.async {
    movieService.list().map { movies =>
      Ok(movies.asJson)
    }
  }

  def getById(movieId: String) = Action.async {
    val id = UUID.fromString(movieId)

    movieService.getById(id).map {
      case Some(movie) => Ok(movie.asJson)
      case None => NotFound
    }
  }

  def update(movieId: String) = Action(circe.json[NewMovie]).async { implicit request =>
    val id = UUID.fromString(movieId)

    movieService
      .update(id, request.body)
      .map(_ => NoContent)
  }

  def delete(movieId: String) = Action.async {
    val id = UUID.fromString(movieId)

    movieService
      .delete(id)
      .map(_ => NoContent)
  }

}
