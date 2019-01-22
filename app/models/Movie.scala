package models

import java.util.UUID

import org.joda.time.DateTime

case class Movie(
  id: UUID,
  name: String,
  ranking: Int,
  releaseDate: DateTime
)
