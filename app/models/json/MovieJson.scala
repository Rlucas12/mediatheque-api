package models.json

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, Encoder, HCursor}
import io.tabmo.circe.extra.rules.{IntRules, StringRules}
import io.tabmo.extra.rules.joda.JodaRules
import io.tabmo.json.rules._
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

  implicit val movieDecoder: Decoder[Movie] = Decoder.instance[Movie] { (c: HCursor) =>
    for {
      id          <- c.downField("id").as[UUID]
      name        <- c.downField("name").read(StringRules.maxLength(50))
      ranking     <- c.downField("ranking").read(IntRules.min(0) |+| IntRules.max(10))
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
    } yield Movie(id, name, ranking, releaseDate)
  }

}
