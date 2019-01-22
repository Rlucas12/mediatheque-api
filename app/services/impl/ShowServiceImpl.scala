package services.impl

import java.util.UUID

import daos.Fixture
import models.{Episode, Season, Show}
import services.ShowService

import scala.concurrent.Future

class ShowServiceImpl extends ShowService {

  override def getById(showId: UUID): Future[Option[Show]] = {
    Future.successful(
      Fixture
        .shows
        .find(_.id == showId)
    )
  }

  override def list(): Future[Seq[Show]] = {
    Future.successful(
      Fixture
        .shows
    )
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
