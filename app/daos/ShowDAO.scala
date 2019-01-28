package daos

import models.Show
import _root_.slick.dbio.DBIO
import core.models.{Nix, UUID}
import models.news.NewShow

trait ShowDAO {

  def add(show: Show): DBIO[Show]
  def list(): DBIO[Seq[Show]]
  def getById(showId: UUID): DBIO[Option[Show]]
  def update(showId: UUID, newShow: NewShow): DBIO[Nix]
  def delete(showId: UUID): DBIO[Nix]

}
