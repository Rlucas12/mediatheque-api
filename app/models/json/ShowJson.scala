package models.json

import core.CoreJson
import io.circe.Encoder
import models.Show

trait ShowJson extends CoreJson {

  implicit val showEncoder: Encoder[Show] = {
    Encoder.forProduct4(
      "id",
      "name",
      "ranking",
      "releaseDate"
    )((show: Show) =>
      (show.id,
        show.name,
        show.ranking,
        show.releaseDate
      ))
  }

}
