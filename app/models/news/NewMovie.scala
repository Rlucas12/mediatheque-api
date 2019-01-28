package models.news

import java.util.UUID

import models.Movie
import org.joda.time.DateTime

case class NewMovie(
  name: String,
  ranking: Int,
  releaseDate: DateTime
) {
  def toMovie(): Movie = {
    Movie(
      id = UUID.randomUUID(),
      name = name,
      ranking = ranking,
      releaseDate = releaseDate,
    )
  }
  def toMovie(movie: Movie): Movie = {
    movie.copy(
      name = name,
      ranking = ranking,
      releaseDate = releaseDate
    )
  }
}
