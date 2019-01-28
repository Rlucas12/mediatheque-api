package services

import core.models.{Nix, UUID}
import models.Show
import models.news.NewShow

import scala.concurrent.Future

trait ShowService {

  def add(newShow: NewShow): Future[Show]
  def list(): Future[Seq[Show]]
  def getById(showId: UUID): Future[Option[Show]]
  def update(showId: UUID, newShow: NewShow): Future[Nix]
  def delete(showId: UUID): Future[Nix]

}
