package daos.slick

import core.PostgresProfile.api._
import core.models.{Nix, UUID}
import daos.EpisodeDAO
import db.Schemas
import models.Episode
import models.news.NewEpisode
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext

class EpisodeDAOSlick(implicit ec: ExecutionContext) extends EpisodeDAO {

  val shows = Schemas.shows
  val seasons = Schemas.seasons
  val episodes = Schemas.episodes

  override def add(episode: Episode): DBIO[Episode] = {
    (episodes += episode).map(_ => episode)
  }

  override def getById(episodeId: UUID): DBIO[Option[Episode]] = {
    episodes
      .filter(_.id === episodeId)
      .result
      .headOption
  }

  override def update(episodeId: UUID, newEpisode: NewEpisode): DBIO[Nix] = {
    for {
      episode <- episodes.filter(_.id === episodeId).result.head
      _ <- episodes.update(newEpisode.toEpisode(episode))
    } yield Nix
  }

  override def delete(episodeId: UUID): DBIO[Nix] = {
    episodes
      .filter(_.id === episodeId)
      .delete
      .map(_ => Nix)
  }

  override def listByShowIdAndSeasonId(showId: UUID, seasonId: UUID): DBIO[Seq[Episode]] = {
    episodes
      .join(seasons).on((episode, season) => episode.seasonId === season.id)
      .join(shows).on { case ((_, season), show) => season.showId === show.id }
      .map { case ((episode, _), _) => episode }
      .result
  }

  override def getByShowIdAndSeasonId(showId: UUID, seasonId: UUID, episodeId: UUID): DBIO[Option[Episode]] = {
    episodes
      .join(seasons).on((episode, season) => episode.seasonId === season.id)
      .join(shows).on { case ((_, season), show) => season.showId === show.id }
      .map { case ((episode, _), _) => episode }
      .result
      .headOption
  }

}
