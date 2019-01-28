package services

import core.models.{Nix, UUID}
import models.Episode
import models.news.NewEpisode

import scala.concurrent.Future

trait EpisodeService {

  def add(newEpisode: NewEpisode): Future[Episode]
  def getById(episodeId: UUID): Future[Option[Episode]]
  def update(episodeId: UUID, newEpisode: NewEpisode): Future[Nix]
  def delete(episodeId: UUID): Future[Nix]

  def listByShowIdAndSeasonId(showId: UUID, seasonId: UUID): Future[Seq[Episode]]
  def getByShowIdAndSeasonId(showId: UUID, seasonId: UUID, episodeId: UUID): Future[Option[Episode]]

}
