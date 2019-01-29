package controllers

import java.util.UUID

import _root_.slick.dbio.DBIO
import core.models.Nix
import helpers.{JodaDateTimeJson, TestApplication}
import models.json.news.NewEpisodeJson
import models.news.NewEpisode
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import play.api.test.Helpers._

class EpisodeSpec extends TestApplication with JodaDateTimeJson with NewEpisodeJson {

  "Episode API" should {

    lazy val showId = UUID.randomUUID()
    lazy val seasonId = UUID.randomUUID()
    val newEpisodeJson = Json.parse(
      s"""
        |{
        | "name": "New Episode",
        | "description": "Description of new episode",
        |	"releaseDate": "27/01/2019",
        | "seasonId": "$seasonId"
        |}
      """.stripMargin)
    val newEpisode = NewEpisode("New Episode", "Description of new episode", DateTime.parse("27/01/2019",DateTimeFormat.forPattern("dd/MM/yyyy")), seasonId)
    val episode = newEpisode.toEpisode()

    "Create an episode" in {
      (episodeDAOMock.add _).when(*).returns(DBIO.successful(episode))
      val response = route(app, withHeader(POST, "/api/episodes").withJsonBody(newEpisodeJson)).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(episode.id.toString)
      (json \ "name").as[String] must ===(episode.name)
      (json \ "description").as[String] must ===(episode.description)
      (json \ "releaseDate").as[DateTime] must ===(episode.releaseDate)
      (json \ "seasonId").as[String] must ===(episode.seasonId.toString)
    }

    "Get an episode" in {
      (episodeDAOMock.getById _).when(episode.id).returns(DBIO.successful(Some(episode)))
      val response = route(app, withHeader(GET, s"/api/episodes/${episode.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(episode.id.toString)
      (json \ "name").as[String] must ===(episode.name)
      (json \ "description").as[String] must ===(episode.description)
      (json \ "releaseDate").as[DateTime] must ===(episode.releaseDate)
      (json \ "seasonId").as[String] must ===(episode.seasonId.toString)
    }

    "Update an episode" in {
      (episodeDAOMock.update _).when(*, *).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(PUT, s"/api/episodes/${episode.id}").withJsonBody(newEpisodeJson)).get
      status(response) must ===(NO_CONTENT)

      (episodeDAOMock.update _).verify(episode.id, newEpisode)
    }

    "Delete an episode" in {
      (episodeDAOMock.delete _).when(episode.id).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(DELETE, s"/api/episodes/${episode.id}")).get
      status(response) must ===(NO_CONTENT)

      (episodeDAOMock.delete _).verify(episode.id)
    }

    "List all episodes by showId and seasonId" in {
      (episodeDAOMock.listByShowIdAndSeasonId _).when(showId, seasonId).returns(DBIO.successful(Seq(episode)))
      val response = route(app, withHeader(GET, s"/api/shows/$showId/seasons/$seasonId/episodes")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ 0 \ "id").as[String] must ===(episode.id.toString)
      (json \ 0 \ "name").as[String] must ===(episode.name)
      (json \ 0 \ "description").as[String] must ===(episode.description)
      (json \ 0 \ "releaseDate").as[DateTime] must ===(episode.releaseDate)
      (json \ 0 \ "seasonId").as[String] must ===(episode.seasonId.toString)
    }

    "Get an episode by showId and seasonId" in {
      (episodeDAOMock.getByShowIdAndSeasonId _).when(showId, seasonId, *).returns(DBIO.successful(Some(episode)))
      val response = route(app, withHeader(GET, s"/api/shows/$showId/seasons/$seasonId/episodes/${episode.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(episode.id.toString)
      (json \ "name").as[String] must ===(episode.name)
      (json \ "description").as[String] must ===(episode.description)
      (json \ "releaseDate").as[DateTime] must ===(episode.releaseDate)
      (json \ "seasonId").as[String] must ===(episode.seasonId.toString)
    }

  }

}
