package services.impl

import core.models.{Nix, UUID}
import core.{DbContext, DbExecutor}
import daos.MovieDAO
import models.Movie
import models.news.NewMovie
import services.MovieService

import scala.concurrent.{ExecutionContext, Future}

class MovieServiceImpl(movieDAO: MovieDAO, protected val dbExecutor: DbExecutor)(implicit ec: ExecutionContext) extends DbContext with MovieService {

  override def add(newMovie: NewMovie): Future[Movie] = {
    movieDAO
      .add(newMovie.toMovie())
      .commit()
  }

  override def list(): Future[Seq[Movie]] = {
    movieDAO
      .list()
      .commit()
  }

  override def getById(movieId: UUID): Future[Option[Movie]] = {
    movieDAO
      .getById(movieId)
      .commit()
  }

  override def update(movieId: UUID, newMovie: NewMovie): Future[Nix] = {
    movieDAO
      .update(movieId, newMovie)
      .commit()
  }

  override def delete(movieId: UUID): Future[Nix] = {
    movieDAO
      .delete(movieId)
      .commit()
  }

}
