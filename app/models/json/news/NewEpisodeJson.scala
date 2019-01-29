package models.json.news

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, HCursor}
import io.tabmo.circe.extra.rules.StringRules
import io.tabmo.json.rules._
import io.tabmo.extra.rules.joda.JodaRules
import models.news.NewEpisode

trait NewEpisodeJson extends CoreJson {

  implicit val newEpisodeDecoder: Decoder[NewEpisode] = Decoder.instance[NewEpisode] { (c: HCursor) =>
    for {
      name        <- c.downField("name").read(StringRules.maxLength(150))
      description <- c.downField("description").read(StringRules.minLength(10))
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
      seasonId    <- c.downField("seasonId").as[UUID]
    } yield NewEpisode(name, description, releaseDate, seasonId)
  }

}
