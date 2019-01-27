package daos.slick

import java.util.UUID
import core.PostgresProfile.api._
import daos.ShowDAO
import db.Schemas
import models.{Episode, Season, Show}
import slick.lifted.{Compiled, Rep}

class ShowDAOSlick extends ShowDAO {

  val shows = Schemas.shows
  val seasons = Schemas.seasons
  val episodes = Schemas.episodes

  override def getById(showId: UUID): DBIO[Option[Show]] = {
    queryGetById(showId)
      .result
      .headOption
  }

  override def list(): DBIO[Seq[Show]] = {
    shows
      .result
  }

  override def getSeasonById(showId: UUID, seasonId: UUID): DBIO[Option[Season]] = {
    queryGetSeasonById(showId, seasonId)
      .result
      .headOption
  }

  override def listSeasons(showId: UUID): DBIO[Seq[Season]] = {
    seasons
      .result
  }

  override def getEpisodeById(showId: UUID, seasonId: UUID, episodeId: UUID): DBIO[Option[Episode]] = {
    episodes
      .join(seasons).on((episode, season) => episode.seasonId === season.id)
      .join(shows).on { case ((_, season), show) => season.showId === show.id }
      .map { case ((episode, _), _) => episode }
      .result
      .headOption
  }

  override def listEpisodes(showId: UUID, seasonId: UUID): DBIO[Seq[Episode]] = {
    episodes
      .join(seasons).on((episode, season) => episode.seasonId === season.id)
      .join(shows).on { case ((_, season), show) => season.showId === show.id }
      .map { case ((episode, _), _) => episode }
      .result
  }

  private val queryGetById = Compiled((id: Rep[UUID]) =>
    shows.filter(_.id === id)
  )

  private val queryGetSeasonById = Compiled((showId: Rep[UUID], seasonId: Rep[UUID]) =>
    seasons.filter(s => s.showId === showId && s.id === seasonId)
  )

}
