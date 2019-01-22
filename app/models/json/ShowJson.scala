package models.json

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, Encoder, HCursor}
import io.tabmo.circe.extra.rules.{IntRules, StringRules}
import io.tabmo.extra.rules.joda.JodaRules
import io.tabmo.json.rules._
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

  implicit val showDecoder: Decoder[Show] = Decoder.instance[Show] { (c: HCursor) =>
    for {
      id          <- c.downField("id").as[UUID]
      name        <- c.downField("name").read(StringRules.maxLength(50))
      ranking     <- c.downField("ranking").read(IntRules.min(0) |+| IntRules.max(10))
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
    } yield Show(id, name, ranking, releaseDate)
  }

}
