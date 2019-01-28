package controllers

import java.util.UUID

import io.circe.syntax._
import models.json._
import models.json.news.NewEpisodeJson
import models.news.NewEpisode
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import services.EpisodeService

import scala.concurrent.ExecutionContext

class EpisodeController(episodeService: EpisodeService, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Circe with EpisodeJson with NewEpisodeJson {

  def add() = Action(circe.json[NewEpisode]).async { implicit request =>
    episodeService
      .add(request.body)
      .map(episode => Ok(episode.asJson))
  }

  def getById(episodeId: String) = Action.async {
    val id = UUID.fromString(episodeId)

    episodeService
      .getById(id)
      .map {
        case Some(episode) => Ok(episode.asJson)
        case _ => NotFound
      }
  }

  def update(episodeId: String) = Action(circe.json[NewEpisode]).async { implicit request =>
    val id = UUID.fromString(episodeId)

    episodeService
      .update(id, request.body)
      .map(_ => NoContent)
  }

  def delete(episodeId: String) = Action.async {
    val id = UUID.fromString(episodeId)
    episodeService
      .delete(id)
      .map(_ => NoContent)
  }

  def listByShowIdAndSeasonId(showId: String, seasonId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)

    episodeService.listByShowIdAndSeasonId(showIdUUID, seasonIdUUID).map { episodes =>
      Ok(episodes.asJson)
    }
  }

  def getByShowIdAndSeasonId(showId: String, seasonId: String, episodeId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)
    val episodeIdUUID = UUID.fromString(episodeId)

    episodeService.getByShowIdAndSeasonId(showIdUUID, seasonIdUUID, episodeIdUUID).map {
      case Some(episode) => Ok(episode.asJson)
      case None => NotFound
    }
  }

}
