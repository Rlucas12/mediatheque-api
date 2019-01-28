package services

import core.models.{Nix, UUID}
import models.Season
import models.news.NewSeason

import scala.concurrent.Future

trait SeasonService {

  def add(newSeason: NewSeason): Future[Season]
  def getById(episodeId: UUID): Future[Option[Season]]
  def update(seasonId: UUID, newSeason: NewSeason): Future[Nix]
  def delete(seasonId: UUID): Future[Nix]

  def listByShowId(showId: UUID): Future[Seq[Season]]
  def getByShowId(showId: UUID, seasonId: UUID): Future[Option[Season]]

}
