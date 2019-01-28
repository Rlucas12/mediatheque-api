package models.news

import java.util.UUID

import models.Show
import org.joda.time.DateTime

case class NewShow(
  name: String,
  ranking: Int,
  releaseDate: DateTime
){
  def toShow(): Show = {
    Show(
      id = UUID.randomUUID(),
      name = name,
      ranking = ranking,
      releaseDate = releaseDate
    )
  }
  def toShow(show: Show): Show = {
    show.copy(
      name = name,
      ranking = ranking,
      releaseDate = releaseDate
    )
  }
}
