package models.news

import java.util.UUID

import models.Episode
import org.joda.time.DateTime

case class NewEpisode(
 name: String,
 description: String,
 releaseDate: DateTime,
 seasonId: UUID
) {
  def toEpisode(): Episode = {
    Episode(
      id = UUID.randomUUID(),
      name = name,
      description = description,
      releaseDate = releaseDate,
      seasonId = seasonId
    )
  }
  def toEpisode(episode: Episode): Episode = {
    episode.copy(
      name = name,
      description = description,
      releaseDate = releaseDate,
      seasonId = seasonId
    )
  }
}
