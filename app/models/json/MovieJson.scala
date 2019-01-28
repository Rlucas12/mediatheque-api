package models.json

import core.CoreJson
import io.circe.Encoder
import models.Movie

trait MovieJson extends CoreJson {

  implicit val movieEncoder: Encoder[Movie] = {
    Encoder.forProduct4(
      "id",
      "name",
      "ranking",
      "releaseDate"
    )((movie: Movie) =>
      (movie.id,
        movie.name,
        movie.ranking,
        movie.releaseDate
      ))
  }

}
