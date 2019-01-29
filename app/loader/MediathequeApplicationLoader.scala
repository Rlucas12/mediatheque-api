package loader

import daos._
import play.api.ApplicationLoader.Context
import play.api._

class MediathequeApplicationLoader extends ApplicationLoader {
  def load(context: Context) = new MediathequeApplicationComponents(context).application
}

class TestMediathequeApplicationLoader(
  movieDAOMock: MovieDAO,
  showDAOMock: ShowDAO,
  seasonDAOMock: SeasonDAO,
  episodeDAOMock: EpisodeDAO) extends ApplicationLoader {
  override def load(context: Context): Application = {
    new MediathequeApplicationComponents(context) {
      override lazy val movieDAO: MovieDAO      = movieDAOMock
      override lazy val showDAO: ShowDAO        = showDAOMock
      override lazy val seasonDAO: SeasonDAO    = seasonDAOMock
      override lazy val episodeDAO: EpisodeDAO  = episodeDAOMock
    }.application
  }
}