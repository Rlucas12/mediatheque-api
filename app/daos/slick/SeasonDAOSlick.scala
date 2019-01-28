package daos.slick

import core.PostgresProfile.api._
import core.models.{Nix, UUID}
import daos.SeasonDAO
import db.Schemas
import models.Season
import models.news.NewSeason
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext

class SeasonDAOSlick(implicit ec: ExecutionContext) extends SeasonDAO {

  val shows = Schemas.shows
  val seasons = Schemas.seasons

  override def add(season: Season): DBIO[Season] = {
    (seasons += season).map(_ => season)
  }

  override def getById(seasonId: UUID): DBIO[Option[Season]] = {
    seasons
      .filter(_.id === seasonId)
      .result
      .headOption
  }

  override def update(seasonId: UUID, newSeason: NewSeason): DBIO[Nix] = {
    for {
      season <- seasons.filter(_.id === seasonId).result.head
      _ <- seasons.update(newSeason.toSeason(season))
    } yield Nix
  }

  override def delete(seasonId: UUID): DBIO[Nix] = {
    seasons
      .filter(_.id === seasonId)
      .delete
      .map(_ => Nix)
  }

  override def listByShowId(showId: UUID): DBIO[Seq[Season]] = {
    seasons
      .join(shows).on { case (season, show) => season.showId === show.id }
      .map { case (season, _) => season }
      .result
  }

  override def getByShowId(showId: UUID, seasonId: UUID): DBIO[Option[Season]] = {
    seasons
      .join(shows).on { case (season, show) => season.showId === show.id }
      .map { case (season, _) => season }
      .result
      .headOption
  }

}
