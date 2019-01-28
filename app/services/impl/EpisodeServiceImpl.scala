package services.impl

import core.{DbContext, DbExecutor}
import core.models.{Nix, UUID}
import daos.EpisodeDAO
import models.Episode
import models.news.NewEpisode
import services.EpisodeService

import scala.concurrent.Future

class EpisodeServiceImpl(episodeDAO: EpisodeDAO, protected val dbExecutor: DbExecutor) extends EpisodeService with DbContext {

  override def add(newEpisode: NewEpisode): Future[Episode] = {
    episodeDAO
      .add(newEpisode.toEpisode())
      .commit()
  }

  override def getById(episodeId: UUID): Future[Option[Episode]] = {
    episodeDAO
      .getById(episodeId)
      .commit()
  }

  override def update(episodeId: UUID, newEpisode: NewEpisode): Future[Nix] = {
    episodeDAO
      .update(episodeId, newEpisode)
      .commit()
  }

  override def delete(episodeId: UUID): Future[Nix] = {
    episodeDAO
      .delete(episodeId)
      .commit()
  }

  override def listByShowIdAndSeasonId(showId: UUID, seasonId: UUID): Future[Seq[Episode]] = {
    episodeDAO
      .listByShowIdAndSeasonId(showId, seasonId)
      .commit()
  }

  override def getByShowIdAndSeasonId(showId: UUID, seasonId: UUID, episodeId: UUID): Future[Option[Episode]] = {
    episodeDAO
      .getByShowIdAndSeasonId(showId, seasonId, episodeId)
      .commit()
  }

}
