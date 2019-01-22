package models.json

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, Encoder, HCursor}
import io.tabmo.circe.extra.rules._
import io.tabmo.extra.rules.joda.JodaRules
import io.tabmo.json.rules._
import models.Episode

trait EpisodeJson extends CoreJson {

  implicit val episodeEncoder: Encoder[Episode] = {
    Encoder.forProduct5(
      "id",
      "name",
      "description",
      "releaseDate",
      "seasonId"
    )((episode: Episode) =>
      (episode.id,
        episode.name,
        episode.description,
        episode.releaseDate,
        episode.seasonId
      ))
  }

  implicit val episodeDecoder: Decoder[Episode] = Decoder.instance[Episode] { (c: HCursor) =>
    for {
      id          <- c.downField("id").as[UUID]
      name        <- c.downField("name").read(StringRules.maxLength(50))
      description <- c.downField("description").read(StringRules.minLength(50))
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
      seasonId    <- c.downField("seasonId").as[UUID]
    } yield Episode(id, name, description, releaseDate, seasonId)
  }

}
