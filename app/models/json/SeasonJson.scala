package models.json

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, Encoder, HCursor}
import io.tabmo.extra.rules.joda.JodaRules
import io.tabmo.json.rules._
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

  implicit val seasonDecoder: Decoder[Season] = Decoder.instance[Season] { (c: HCursor) =>
    for {
      id          <- c.downField("id").as[UUID]
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
      showId      <- c.downField("showId").as[UUID]
    } yield Season(id, releaseDate, showId)
  }

}
