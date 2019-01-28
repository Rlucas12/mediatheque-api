package services.impl

import core.models.{Nix, UUID}
import core.{DbContext, DbExecutor}
import daos.ShowDAO
import models.Show
import models.news.NewShow
import services.ShowService

import scala.concurrent.{ExecutionContext, Future}

class ShowServiceImpl(showDAO: ShowDAO, protected val dbExecutor: DbExecutor)(implicit ec: ExecutionContext) extends DbContext with ShowService {

  override def add(newShow: NewShow): Future[Show] = {
    showDAO
      .add(newShow.toShow())
      .commit()
  }

  override def list(): Future[Seq[Show]] = {
    showDAO
      .list()
      .commit()
  }

  override def getById(showId: UUID): Future[Option[Show]] = {
    showDAO
      .getById(showId)
      .commit()
  }

  override def update(showId: UUID, newShow: NewShow): Future[Nix] = {
    showDAO
      .update(showId, newShow)
      .commit()
  }

  override def delete(showId: UUID): Future[Nix] = {
    showDAO
      .delete(showId)
      .commit()
  }

}
