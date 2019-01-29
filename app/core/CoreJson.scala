package core

import io.circe.{Encoder, Json}
import org.joda.time.DateTime

trait CoreJson {

  implicit val dateTimeEncoder: Encoder[DateTime] = {
    Encoder[String].contramap(_.toString("dd/MM/yyyy"))
  }

}
