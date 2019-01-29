package controllers

import _root_.slick.dbio.DBIO
import core.models.Nix
import helpers.{JodaDateTimeJson, TestApplication}
import models.json.news.NewShowJson
import models.news.NewShow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import play.api.test.Helpers._

class ShowSpec extends TestApplication with JodaDateTimeJson with NewShowJson {

  "Show API" should {

    val newShowJson = Json.parse(
      """
        |{
        |	"name": "New Show",
        |	"ranking": "6",
        |	"releaseDate": "27/01/2019"
        |}
      """.stripMargin)
    val newShow = NewShow("New Show", 6, DateTime.parse("27/01/2019",DateTimeFormat.forPattern("dd/MM/yyyy")))
    val show = newShow.toShow()

    "Create a show" in {
      (showDAOMock.add _).when(*).returns(DBIO.successful(show))
      val response = route(app, withHeader(POST, "/api/shows").withJsonBody(newShowJson)).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "name").as[String] must ===(newShow.name)
      (json \ "ranking").as[Int] must ===(newShow.ranking)
      (json \ "releaseDate").as[DateTime] must ===(newShow.releaseDate)
    }

    "List all shows" in {
      (showDAOMock.list _).when().returns(DBIO.successful(Seq(show)))
      val response = route(app, withHeader(GET, "/api/shows")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ 0 \ "id").as[String] must ===(show.id.toString)
      (json \ 0 \ "name").as[String] must ===(show.name)
      (json \ 0 \ "ranking").as[Int] must ===(show.ranking)
      (json \ 0 \ "releaseDate").as[DateTime] must ===(show.releaseDate)
    }

    "Get a show" in {
      (showDAOMock.getById _).when(show.id).returns(DBIO.successful(Some(show)))
      val response = route(app, withHeader(GET, s"/api/shows/${show.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(show.id.toString)
      (json \ "name").as[String] must ===(show.name)
      (json \ "ranking").as[Int] must ===(show.ranking)
      (json \ "releaseDate").as[DateTime] must ===(show.releaseDate)
    }

    "Update a show" in {
      (showDAOMock.update _).when(*, *).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(PUT, s"/api/shows/${show.id}").withJsonBody(newShowJson)).get
      status(response) must ===(NO_CONTENT)

      (showDAOMock.update _).verify(show.id, newShow)
    }

    "Delete a show" in {
      (showDAOMock.delete _).when(show.id).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(DELETE, s"/api/shows/${show.id}")).get
      status(response) must ===(NO_CONTENT)

      (showDAOMock.delete _).verify(show.id)
    }

  }

}
