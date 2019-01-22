package models

import java.util.UUID

import org.joda.time.DateTime

case class Season(
 id: UUID,
 releaseDate: DateTime,
 showId: UUID
)
