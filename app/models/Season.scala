package models

import core.models.UUID
import org.joda.time.DateTime

case class Season(
 id: UUID,
 releaseDate: DateTime,
 showId: UUID
)
