package models.json

import core.CoreJson
import io.circe.Encoder
import models.Season

trait SeasonJson extends CoreJson {

  implicit val seasonEncoder: Encoder[Season] = {
    Encoder.forProduct3(
      "id",
      "releaseDate",
      "showId"
    )((season: Season) =>
      (season.id,
        season.releaseDate,
        season.showId
      ))
  }

}
