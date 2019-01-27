package db

import core.PostgresProfile.api._
import java.util.UUID

import models.{Episode, Movie, Season, Show}
import org.joda.time.DateTime

object Schemas {

  class Episodes(tag: Tag) extends Table[Episode](tag, "episodes") {
    def id = column[UUID]("id")
    def name = column[String]("name")
    def description = column[String]("description")
    def releaseDate = column[DateTime]("release_date")
    def seasonId = column[UUID]("season_id")

    def * = (id, name, description, releaseDate, seasonId) <> (Episode.tupled, Episode.unapply)
  }

  class Movies(tag: Tag) extends Table[Movie](tag, "movies") {
    def id = column[UUID]("id")
    def name = column[String]("name")
    def ranking = column[Int]("ranking")
    def releaseDate = column[DateTime]("release_date")

    def * = (id, name, ranking, releaseDate) <> (Movie.tupled, Movie.unapply)
  }

  class Seasons(tag: Tag) extends Table[Season](tag, "seasons") {
    def id = column[UUID]("id")
    def releaseDate = column[DateTime]("release_date")
    def showId = column[UUID]("show_id")

    def * = (id, releaseDate, showId) <> (Season.tupled, Season.unapply)
  }

  class Shows(tag: Tag) extends Table[Show](tag, "shows") {
    def id = column[UUID]("id")
    def name = column[String]("name")
    def ranking = column[Int]("ranking")
    def releaseDate = column[DateTime]("release_date")

    def * = (id, name, ranking, releaseDate) <> (Show.tupled, Show.unapply)
  }

  val episodes  = TableQuery[Episodes]
  val movies    = TableQuery[Movies]
  val seasons   = TableQuery[Seasons]
  val shows     = TableQuery[Shows]

}
