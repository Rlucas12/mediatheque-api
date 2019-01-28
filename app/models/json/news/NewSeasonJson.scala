package models.json.news

import java.util.UUID

import core.CoreJson
import io.circe.{Decoder, HCursor}
import io.tabmo.json.rules._
import io.tabmo.extra.rules.joda.JodaRules
import models.news.NewSeason

trait NewSeasonJson extends CoreJson {

  implicit val newSeasonDecoder: Decoder[NewSeason] = Decoder.instance[NewSeason] { (c: HCursor) =>
    for {
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
      showId      <- c.downField("showId").as[UUID]
    } yield NewSeason(releaseDate, showId)
  }

}
