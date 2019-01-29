package helpers

import daos._
import daos.slick._
import loader.TestMediathequeApplicationLoader
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.{Application, ApplicationLoader, Environment, Mode}

trait TestApplication extends PlaySpec with GuiceOneAppPerSuite with MockFactory {

  val movieDAOMock: MovieDAO     = stub[MovieDAOSlick]
  val showDAOMock: ShowDAO       = stub[ShowDAOSlick]
  val seasonDAOMock: SeasonDAO   = stub[SeasonDAOSlick]
  val episodeDAOMock: EpisodeDAO = stub[EpisodeDAOSlick]

  implicit override lazy val app: Application = {
    new TestMediathequeApplicationLoader(movieDAOMock, showDAOMock, seasonDAOMock, episodeDAOMock)
      .load(ApplicationLoader.createContext(new Environment(new java.io.File("."), ApplicationLoader.getClass.getClassLoader, Mode.Test)))
  }

  def withHeader(method: String, path: String) = {
    FakeRequest(method, path)
  }

}
