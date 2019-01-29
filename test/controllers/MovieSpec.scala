package controllers

import helpers.{JodaDateTimeJson, TestApplication}
import models.json.news.NewMovieJson
import models.news.NewMovie
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import play.api.test.Helpers._
import _root_.slick.dbio.DBIO
import core.models.Nix

class MovieSpec extends TestApplication with JodaDateTimeJson with NewMovieJson {

  "Movie API" should {

    val newMovieJson = Json.parse(
      """
        |{
        |	"name": "New Movie",
        |	"ranking": "4",
        |	"releaseDate": "27/01/2019"
        |}
      """.stripMargin)
    val newMovie = NewMovie("New Movie", 4, DateTime.parse("27/01/2019",DateTimeFormat.forPattern("dd/MM/yyyy")))
    val movie = newMovie.toMovie()

    "Create a movie" in {
      (movieDAOMock.add _).when(*).returns(DBIO.successful(movie))
      val response = route(app, withHeader(POST, "/api/movies").withJsonBody(newMovieJson)).get
      status(response) mustEqual OK

      val json = contentAsJson(response)
      (json \ "name").as[String] must ===(newMovie.name)
      (json \ "ranking").as[Int] must ===(newMovie.ranking)
      (json \ "releaseDate").as[DateTime] must ===(newMovie.releaseDate)
    }

    "List all movies" in {
      (movieDAOMock.list _).when().returns(DBIO.successful(Seq(movie)))
      val response = route(app, withHeader(GET, "/api/movies")).get
      status(response) mustEqual OK

      val json = contentAsJson(response)
      (json \ 0 \ "id").as[String] must ===(movie.id.toString)
      (json \ 0 \ "name").as[String] must ===(movie.name)
      (json \ 0 \ "ranking").as[Int] must ===(movie.ranking)
      (json \ 0 \ "releaseDate").as[DateTime] must ===(movie.releaseDate)
    }

    "Get a movie" in {
      (movieDAOMock.getById _).when(movie.id).returns(DBIO.successful(Some(movie)))
      val response = route(app, withHeader(GET, s"/api/movies/${movie.id}")).get
      status(response) must ===(OK)

      val json = contentAsJson(response)
      (json \ "id").as[String] must ===(movie.id.toString)
      (json \ "name").as[String] must ===(movie.name)
      (json \ "ranking").as[Int] must ===(movie.ranking)
      (json \ "releaseDate").as[DateTime] must ===(movie.releaseDate)
    }

    "Update a movie" in {
      (movieDAOMock.update _).when(*, *).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(PUT, s"/api/movies/${movie.id}").withJsonBody(newMovieJson)).get
      status(response) must ===(NO_CONTENT)

      (movieDAOMock.update _).verify(movie.id, newMovie)
    }

    "Delete a movie" in {
      (movieDAOMock.delete _).when(movie.id).returns(DBIO.successful(Nix))
      val response = route(app, withHeader(DELETE, s"/api/movies/${movie.id}")).get
      status(response) must ===(NO_CONTENT)

      (movieDAOMock.delete _).verify(movie.id)
    }

  }

}
