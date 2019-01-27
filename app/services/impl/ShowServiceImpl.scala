package services.impl

import java.util.UUID

import core.{DbContext, DbExecutor}
import daos.ShowDAO
import db.Fixture
import models.{Episode, Season, Show}
import services.ShowService

import scala.concurrent.{ExecutionContext, Future}

class ShowServiceImpl(showDAO: ShowDAO, protected val dbExecutor: DbExecutor)(implicit ec: ExecutionContext) extends DbContext with ShowService {

  override def getById(showId: UUID): Future[Option[Show]] = {
    showDAO
      .getById(showId)
      .commit()
  }

  override def list(): Future[Seq[Show]] = {
    showDAO
      .list()
      .commit()
  }

  override def getSeasonById(showId: UUID, seasonId: UUID): Future[Option[Season]] = {
    Future.successful(
      Fixture
        .seasons
        .find(s => s.id == seasonId && s.showId == showId)
    )
  }

  override def listSeasons(showId: UUID): Future[Seq[Season]] = {
    Future.successful(
      Fixture
        .seasons
        .filter(_.showId == showId)
    )
  }

  override def getEpisodeById(showId: UUID, seasonId: UUID, episodeId: UUID): Future[Option[Episode]] = {
    val validSeason: Season = Fixture.seasons.find(_.showId == showId).get
    Future.successful(
      Fixture
        .episodes
        .find(e => e.id == episodeId && e.seasonId == seasonId && validSeason.id == seasonId)
    )
  }

  override def listEpisodes(showId: UUID, seasonId: UUID): Future[Seq[Episode]] = {
    val validSeason: Season = Fixture.seasons.find(_.showId == showId).get
    Future.successful(
      Fixture
        .episodes
        .filter(_.seasonId == validSeason.id)
        .filter(_.seasonId == seasonId)
    )
  }

}
