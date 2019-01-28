package services

import core.models.{Nix, UUID}
import models.Movie
import models.news.NewMovie

import scala.concurrent.Future

trait MovieService {

  def add(newMovie: NewMovie): Future[Movie]
  def list(): Future[Seq[Movie]]
  def getById(movieId: UUID): Future[Option[Movie]]
  def update(movieId: UUID, newMovie: NewMovie): Future[Nix]
  def delete(movieId: UUID): Future[Nix]

}
