package controllers

import java.util.UUID

import _root_.slick.dbio.DBIO
import core.models.Nix
import helpers.{JodaDateTimeJson, TestApplication}
import models.json.news.NewSeasonJson
import models.news.NewSeason
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import play.api.test.Helpers._

class SeasonSpec extends TestApplication with JodaDateTimeJson with NewSeasonJson {

  "Season API" should {

    lazy val showId = UUID.randomUUID()
    val newSeasonJson = Json.parse(
      s"""
        |{
        |	"releaseDate": "27/01/2019",
        | "showId": "$showId"
        |}
      """.stripMargin)
    val newSeason = NewSeason(DateTime.parse("27/01/2019",DateTimeFormat.forPattern("dd/MM/yyyy")), showId)
    val season = newSeason.toSeason()

    "Create a season" in {
      (seasonDAOMock.add _).when(*).returns(DBIO.successful(season))
      val response = route(app, withHeader(POST, "/api/seasons").withJsonBody(newSeasonJson)).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(season.id.toString)
      (json \ "releaseDate").as[DateTime] must ===(season.releaseDate)
      (json \ "showId").as[String] must ===(season.showId.toString)
    }

    "Get a season" in {
      (seasonDAOMock.getById _).when(season.id).returns(DBIO.successful(Some(season)))
      val response = route(app, withHeader(GET, s"/api/seasons/${season.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(season.id.toString)
      (json \ "releaseDate").as[DateTime] must ===(season.releaseDate)
      (json \ "showId").as[String] must ===(season.showId.toString)
    }

    "Update a season" in {
      (seasonDAOMock.update _).when(*, *).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(PUT, s"/api/seasons/${season.id}").withJsonBody(newSeasonJson)).get
      status(response) must ===(NO_CONTENT)

      (seasonDAOMock.update _).verify(season.id, newSeason)
    }

    "Delete a season" in {
      (seasonDAOMock.delete _).when(season.id).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(DELETE, s"/api/seasons/${season.id}")).get
      status(response) must ===(NO_CONTENT)

      (seasonDAOMock.delete _).verify(season.id)
    }

    "List all season by showId" in {
      (seasonDAOMock.listByShowId _).when(showId).returns(DBIO.successful(Seq(season)))
      val response = route(app, withHeader(GET, s"/api/shows/$showId/seasons")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ 0 \ "id").as[String] must ===(season.id.toString)
      (json \ 0 \ "releaseDate").as[DateTime] must ===(season.releaseDate)
      (json \ 0 \ "showId").as[String] must ===(season.showId.toString)
    }

    "Get a season by showId" in {
      (seasonDAOMock.getByShowId _).when(showId, *).returns(DBIO.successful(Some(season)))
      val response = route(app, withHeader(GET, s"/api/shows/$showId/seasons/${season.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(season.id.toString)
      (json \ "releaseDate").as[DateTime] must ===(season.releaseDate)
      (json \ "showId").as[String] must ===(season.showId.toString)
    }

  }

}
