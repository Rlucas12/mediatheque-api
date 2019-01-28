package controllers

import java.util.UUID

import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import services.ShowService
import models.json._
import models.json.news.NewShowJson
import models.news.NewShow

import scala.concurrent.ExecutionContext

class ShowController(showService: ShowService, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Circe with ShowJson with NewShowJson {

  def add() = Action(circe.json[NewShow]).async { implicit request =>
    showService
      .add(request.body)
      .map(show => Ok(show.asJson))
  }

  def list() = Action.async {
    showService.list().map { shows =>
      Ok(shows.asJson)
    }
  }

  def getById(showId: String) = Action.async {
    val id = UUID.fromString(showId)

    showService.getById(id).map {
      case Some(show) => Ok(show.asJson)
      case None => NotFound
    }
  }

  def update(showId: String) = Action(circe.json[NewShow]).async { implicit request =>
    val id = UUID.fromString(showId)

    showService
      .update(id, request.body)
      .map(_ => NoContent)
  }

  def delete(showId: String) = Action.async {
    val id = UUID.fromString(showId)

    showService
      .delete(id)
      .map(_ => NoContent)
  }

}
