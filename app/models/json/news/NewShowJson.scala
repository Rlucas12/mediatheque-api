package models.json.news

import core.CoreJson
import io.circe.{Decoder, HCursor}
import io.tabmo.circe.extra.rules.{IntRules, StringRules}
import io.tabmo.extra.rules.joda.JodaRules
import io.tabmo.json.rules._
import models.news.NewShow

trait NewShowJson extends CoreJson {

  implicit val newShowDecoder: Decoder[NewShow] = Decoder.instance[NewShow] { (c: HCursor) =>
    for {
      name        <- c.downField("name").read(StringRules.maxLength(150))
      ranking     <- c.downField("ranking").read(IntRules.min(0) |+| IntRules.max(10))
      releaseDate <- c.downField("releaseDate").read(JodaRules.jodaDateWithPattern("dd/MM/yyyy"))
    } yield NewShow(name, ranking, releaseDate)
  }

}
