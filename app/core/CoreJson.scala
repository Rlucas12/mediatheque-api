package core

import io.circe.{Encoder, Json}
import org.joda.time.DateTime

trait CoreJson {

  implicit val dateTimeEncoder: Encoder[DateTime] = new Encoder[DateTime] {
    override def apply(a: DateTime): Json = Encoder.encodeLong(a.getMillis)
  }

}
