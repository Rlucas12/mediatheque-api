package models.news

import java.util.UUID

import models.Season
import org.joda.time.DateTime

case class NewSeason(
 releaseDate: DateTime,
 showId: UUID
) {
  def toSeason(): Season = {
    Season(
      id = UUID.randomUUID(),
      releaseDate = releaseDate,
      showId = showId
    )
  }
  def toSeason(season: Season): Season = {
    season.copy(
      releaseDate = releaseDate,
      showId = showId
    )
  }
}
