package helpers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Reads

trait JodaDateTimeJson {
  val dateFormat = "dd/MM/yyyy"

  implicit val jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
    )
  )
}
