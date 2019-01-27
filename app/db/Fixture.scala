package db

import java.util.UUID

import models.{Episode, Season, Show}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object Fixture {

  def setDateToCorrectFormat(date: String): DateTime = {
    DateTime.parse(date, DateTimeFormat.forPattern("dd/MM/yyyy"))
  }

  lazy val shows: Seq[Show] = Seq(
    thePunisher
  )

  lazy val seasons: Seq[Season] = Seq(
    thePunisherS1,
    thePunisherS2
  )

  lazy val episodes: Seq[Episode] = Seq(
    thePunisherS1E1,
    thePunisherS1E2
  )

  lazy val thePunisher: Show = Show(
    UUID.randomUUID,
    "The Punisher",
    7,
    setDateToCorrectFormat("17/11/2017")
  )

  lazy val thePunisherS1: Season = Season(
    UUID.randomUUID,
    setDateToCorrectFormat("17/11/2017"),
    thePunisher.id
  )

  lazy val thePunisherS2: Season = Season(
    UUID.randomUUID,
    setDateToCorrectFormat("18/01/2019"),
    thePunisher.id
  )

  lazy val thePunisherS1E1: Episode = Episode(
    UUID.randomUUID,
    "3 heures du matin",
    "L'ancien marine Frank Castle édicte ses propres lois et se fait justice lui-même tout en s'efforçant d'accepter les traumatismes du passé.",
    thePunisherS1.releaseDate,
    thePunisherS1.id
  )

  lazy val thePunisherS1E2: Episode = Episode(
    UUID.randomUUID,
    "Deux hommes morts",
    "Un mystérieux appel force la main de Frank. Dinah Madani cherche des suspects. Curtis fait passer un message.",
    thePunisherS1.releaseDate,
    thePunisherS1.id
  )

}
