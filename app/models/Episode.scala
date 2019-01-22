package models

import java.util.UUID

import org.joda.time.DateTime

case class Episode(
 id: UUID,
 name: String,
 description: String,
 releaseDate: DateTime,
 seasonId: UUID
)
