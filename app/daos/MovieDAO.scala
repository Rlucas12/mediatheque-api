package daos

import _root_.slick.dbio.DBIO
import core.models.{Nix, UUID}
import models.Movie
import models.news.NewMovie

trait MovieDAO {

  def add(movie: Movie): DBIO[Movie]
  def list(): DBIO[Seq[Movie]]
  def getById(movieId: UUID): DBIO[Option[Movie]]
  def update(movieId: UUID, newMovie: NewMovie): DBIO[Nix]
  def delete(movieId: UUID): DBIO[Nix]

}
