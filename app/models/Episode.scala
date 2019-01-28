package models

import java.util.UUID

import org.joda.time.DateTime

case class Episode(
 id: UUID = UUID.randomUUID,
 name: String,
 description: String,
 releaseDate: DateTime,
 seasonId: UUID
)
