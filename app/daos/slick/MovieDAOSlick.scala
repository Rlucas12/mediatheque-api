package daos.slick

import core.PostgresProfile.api._
import core.models.{Nix, UUID}
import daos.MovieDAO
import db.Schemas
import models.Movie
import models.news.NewMovie
import slick.lifted.{Compiled, Rep}

import scala.concurrent.ExecutionContext

class MovieDAOSlick(implicit ec: ExecutionContext) extends MovieDAO {

  val movies = Schemas.movies

  override def add(movie: Movie): DBIO[Movie] = {
    (movies += movie).map(_ => movie)
  }

  override def list(): DBIO[Seq[Movie]] = {
    movies
      .result
  }

  override def getById(movieId: UUID): DBIO[Option[Movie]] = {
    queryGetById(movieId)
      .result
      .headOption
  }

  override def update(movieId: UUID, newMovie: NewMovie): DBIO[Nix] = {
    for {
      movie <- movies.filter(_.id === movieId).result.head
      _ <- movies.update(newMovie.toMovie(movie))
    } yield Nix
  }

  override def delete(movieId: UUID): DBIO[Nix] = {
    movies
      .filter(_.id === movieId)
      .delete
      .map(_ => Nix)
  }

  private val queryGetById = Compiled((id: Rep[UUID]) =>
    movies.filter(_.id === id)
  )

}
