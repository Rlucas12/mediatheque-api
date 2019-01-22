package models

import java.util.UUID

import org.joda.time.DateTime

case class Show(
  id: UUID,
  name: String,
  ranking: Int,
  releaseDate: DateTime
)
