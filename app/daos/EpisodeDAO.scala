package daos

import core.models.{Nix, UUID}
import _root_.slick.dbio.DBIO
import models.Episode
import models.news.NewEpisode

trait EpisodeDAO {

  def add(episode: Episode): DBIO[Episode]
  def getById(episodeId: UUID): DBIO[Option[Episode]]
  def update(episodeId: UUID, newEpisode: NewEpisode): DBIO[Nix]
  def delete(episodeId: UUID): DBIO[Nix]

  def listByShowIdAndSeasonId(showId: UUID, seasonId: UUID): DBIO[Seq[Episode]]
  def getByShowIdAndSeasonId(showId: UUID, seasonId: UUID, episodeId: UUID): DBIO[Option[Episode]]

}
