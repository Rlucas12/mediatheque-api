package daos

import _root_.slick.dbio.DBIO
import core.models.{Nix, UUID}
import models.Season
import models.news.NewSeason

trait SeasonDAO {

  def add(season: Season): DBIO[Season]
  def getById(seasonId: UUID): DBIO[Option[Season]]
  def update(seasonId: UUID, newSeason: NewSeason): DBIO[Nix]
  def delete(seasonId: UUID): DBIO[Nix]

  def listByShowId(showId: UUID): DBIO[Seq[Season]]
  def getByShowId(showId: UUID, seasonId: UUID): DBIO[Option[Season]]

}
