package daos.slick

import core.PostgresProfile.api._
import core.models.{Nix, UUID}
import daos.ShowDAO
import db.Schemas
import models.Show
import models.news.NewShow
import slick.lifted.{Compiled, Rep}

import scala.concurrent.ExecutionContext

class ShowDAOSlick(implicit ec: ExecutionContext) extends ShowDAO {

  val shows = Schemas.shows

  override def add(show: Show): DBIO[Show] = {
    (shows += show).map(_ => show)
  }

  override def list(): DBIO[Seq[Show]] = {
    shows
      .result
  }

  override def getById(showId: UUID): DBIO[Option[Show]] = {
    queryGetById(showId)
      .result
      .headOption
  }

  override def update(showId: UUID, newShow: NewShow): DBIO[Nix] = {
    for {
      show <- shows.filter(_.id === showId).result.head
      _ <- shows.update(newShow.toShow(show))
    } yield Nix
  }

  override def delete(showId: UUID): DBIO[Nix] = {
    shows
      .filter(_.id === showId)
      .delete
      .map(_ => Nix)
  }

  private val queryGetById = Compiled((id: Rep[UUID]) =>
    shows.filter(_.id === id)
  )

}
