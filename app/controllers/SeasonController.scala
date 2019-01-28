package controllers

import java.util.UUID

import io.circe.syntax._
import models.json._
import models.json.news.NewSeasonJson
import models.news.NewSeason
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import services.SeasonService

import scala.concurrent.ExecutionContext

class SeasonController(seasonService: SeasonService, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Circe with SeasonJson with NewSeasonJson {

  def add() = Action(circe.json[NewSeason]).async { implicit request =>
    seasonService
      .add(request.body)
      .map(season => Ok(season.asJson))
  }

  def getById(seasonId: String) = Action.async {
    val id = UUID.fromString(seasonId)

    seasonService
      .getById(id)
      .map {
        case Some(season) => Ok(season.asJson)
        case _ => NotFound
      }
  }

  def update(seasonId: String) = Action(circe.json[NewSeason]).async { implicit request =>
    val id = UUID.fromString(seasonId)

    seasonService
      .update(id, request.body)
      .map(_ => NoContent)
  }

  def delete(seasonId: String) = Action.async {
    val id = UUID.fromString(seasonId)

    seasonService
      .delete(id)
      .map(_ => NoContent)
  }

  def listByShowId(showId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)

    seasonService.listByShowId(showIdUUID).map { seasons =>
      Ok(seasons.asJson)
    }
  }

  def getByShowId(showId: String, seasonId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)

    seasonService.getByShowId(showIdUUID, seasonIdUUID).map {
      case Some(episode) => Ok(episode.asJson)
      case None => NotFound
    }
  }

}
