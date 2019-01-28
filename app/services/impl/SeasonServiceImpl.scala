package services.impl

import core.models.{Nix, UUID}
import core.{DbContext, DbExecutor}
import daos.SeasonDAO
import models.Season
import models.news.NewSeason
import services.SeasonService

import scala.concurrent.Future

class SeasonServiceImpl(seasonDAO: SeasonDAO, protected val dbExecutor: DbExecutor) extends SeasonService with DbContext {

  override def add(newSeason: NewSeason): Future[Season] = {
    seasonDAO
      .add(newSeason.toSeason())
      .commit()
  }

  override def getById(episodeId: UUID): Future[Option[Season]] = {
    seasonDAO
      .getById(episodeId)
      .commit()
  }

  override def update(seasonId: UUID, newSeason: NewSeason): Future[Nix] = {
    seasonDAO
      .update(seasonId, newSeason)
      .commit()
  }

  override def delete(seasonId: UUID): Future[Nix] = {
    seasonDAO
      .delete(seasonId)
      .commit()
  }

  override def listByShowId(showId: UUID): Future[Seq[Season]] = {
    seasonDAO
      .listByShowId(showId)
      .commit()
  }

  override def getByShowId(showId: UUID, seasonId: UUID): Future[Option[Season]] = {
    seasonDAO
      .getByShowId(showId, seasonId)
      .commit()
  }

}
