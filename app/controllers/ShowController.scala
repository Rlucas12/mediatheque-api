package controllers

import java.util.UUID

import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import services.ShowService
import models.json._

import scala.concurrent.ExecutionContext

class ShowController(showService: ShowService, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Circe with ShowJson with SeasonJson with EpisodeJson {

  def getById(showId: String) = Action.async {
    val id = UUID.fromString(showId)
    showService.getById(id).map { v => v match {
      case Some(show) => Ok(show.asJson)
      case None => NotFound
    }}
  }

  def list() = Action.async {
    showService.list().map { shows =>
      Ok(shows.asJson)
    }
  }

  def getSeasonById(showId: String, seasonId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)
    showService.getSeasonById(showIdUUID, seasonIdUUID).map { v => v match {
      case Some(season) => Ok(season.asJson)
      case None => NotFound
    }}
  }

  def listSeasons(showId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    showService.listSeasons(showIdUUID).map { seasons =>
      Ok(seasons.asJson)
    }
  }

  def getEpisodeById(showId: String, seasonId: String, episodeId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)
    val episodeIdUUID = UUID.fromString(episodeId)
    showService.getEpisodeById(showIdUUID, seasonIdUUID, episodeIdUUID).map { v => v match {
      case Some(episode) => Ok(episode.asJson)
      case None => NotFound
    }}
  }

  def listEpisodes(showId: String, seasonId: String) = Action.async {
    val showIdUUID = UUID.fromString(showId)
    val seasonIdUUID = UUID.fromString(seasonId)
    showService.listEpisodes(showIdUUID, seasonIdUUID).map { episodes =>
      Ok(episodes.asJson)
    }
  }

}
