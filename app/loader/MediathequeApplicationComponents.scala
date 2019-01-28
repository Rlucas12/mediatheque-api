package loader

import com.softwaremill.macwire.wire
import controllers._
import core.{DbExecutorFromDatabase, PostgresProfile}
import daos.slick.{EpisodeDAOSlick, MovieDAOSlick, SeasonDAOSlick, ShowDAOSlick}
import play.api.BuiltInComponentsFromContext
import play.api.db.evolutions.{DynamicEvolutions, EvolutionsComponents}
import play.api.db.slick.{DbName, DefaultSlickApi, SlickApi}
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.api.routing.Router
import router.Routes
import services.impl.{EpisodeServiceImpl, MovieServiceImpl, SeasonServiceImpl, ShowServiceImpl}

trait MediathequeApplicationComponents extends BuiltInComponentsFromContext
  with AssetsComponents
  with SlickEvolutionsComponents
  with EvolutionsComponents
  with play.filters.HttpFiltersComponents {


  // Database
  override lazy val api: SlickApi = new DefaultSlickApi(environment, configuration, applicationLifecycle)
  implicit lazy val dbExecutor = new DbExecutorFromDatabase(api.dbConfig[PostgresProfile](DbName("default")))
  override lazy val dynamicEvolutions: DynamicEvolutions = new DynamicEvolutions
  applicationEvolutions.start()

  // Controllers
  lazy val mediathequeController  = wire[MediathequeController]
  lazy val showController         = wire[ShowController]
  lazy val seasonController       = wire[SeasonController]
  lazy val episodeController      = wire[EpisodeController]
  lazy val movieController        = wire[MovieController]

  // Services
  lazy val showService    = wire[ShowServiceImpl]
  lazy val seasonService  = wire[SeasonServiceImpl]
  lazy val episodeService = wire[EpisodeServiceImpl]
  lazy val movieService   = wire[MovieServiceImpl]

  // DAOs
  lazy val showDAO    = wire[ShowDAOSlick]
  lazy val seasonDAO  = wire[SeasonDAOSlick]
  lazy val episodeDAO = wire[EpisodeDAOSlick]
  lazy val movieDAO   = wire[MovieDAOSlick]

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}