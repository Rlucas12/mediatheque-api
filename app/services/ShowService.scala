package services

import java.util.UUID

import models.{Episode, Season, Show}

import scala.concurrent.Future

trait ShowService {

  def getById(showId: UUID): Future[Option[Show]]
  def list(): Future[Seq[Show]]

  def getSeasonById(showId: UUID, seasonId: UUID): Future[Option[Season]]
  def listSeasons(showId: UUID): Future[Seq[Season]]

  def getEpisodeById(showId: UUID, seasonId: UUID, episodeId: UUID): Future[Option[Episode]]
  def listEpisodes(showId: UUID, seasonId: UUID): Future[Seq[Episode]]

}
