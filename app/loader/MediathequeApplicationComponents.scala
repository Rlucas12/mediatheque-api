package loader

import com.softwaremill.macwire.wire
import controllers._
import core.{DbExecutor, DbExecutorFromDatabase, PostgresProfile}
import daos._
import daos.slick._
import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator}
import play.api.db.evolutions.{DynamicEvolutions, EvolutionsComponents}
import play.api.db.slick.{DbName, DefaultSlickApi, SlickApi}
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.api.routing.Router
import router.Routes
import services._
import services.impl._

class MediathequeApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with SlickEvolutionsComponents
  with EvolutionsComponents
  with play.filters.HttpFiltersComponents {

  // Logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  // Database
  override lazy val api: SlickApi = new DefaultSlickApi(environment, configuration, applicationLifecycle)
  implicit lazy val dbExecutor: DbExecutor = new DbExecutorFromDatabase(api.dbConfig[PostgresProfile](DbName("default")))
  override lazy val dynamicEvolutions: DynamicEvolutions = new DynamicEvolutions
  applicationEvolutions.start()

  // Controllers
  lazy val mediathequeController: MediathequeController  = wire[MediathequeController]
  lazy val movieController: MovieController              = wire[MovieController]
  lazy val showController: ShowController                = wire[ShowController]
  lazy val seasonController: SeasonController            = wire[SeasonController]
  lazy val episodeController: EpisodeController          = wire[EpisodeController]

  // Services
  lazy val movieService: MovieService      = wire[MovieServiceImpl]
  lazy val showService: ShowService        = wire[ShowServiceImpl]
  lazy val seasonService: SeasonService    = wire[SeasonServiceImpl]
  lazy val episodeService: EpisodeService  = wire[EpisodeServiceImpl]

  // DAOs
  lazy val movieDAO: MovieDAO      = wire[MovieDAOSlick]
  lazy val showDAO: ShowDAO        = wire[ShowDAOSlick]
  lazy val seasonDAO: SeasonDAO    = wire[SeasonDAOSlick]
  lazy val episodeDAO: EpisodeDAO  = wire[EpisodeDAOSlick]

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}