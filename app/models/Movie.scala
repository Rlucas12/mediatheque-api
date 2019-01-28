package models

import core.models.UUID
import org.joda.time.DateTime

case class Movie(
  id: UUID,
  name: String,
  ranking: Int,
  releaseDate: DateTime
)
