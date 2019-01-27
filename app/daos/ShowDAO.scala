package daos

import java.util.UUID

import models.{Episode, Season, Show}
import _root_.slick.dbio.DBIO

trait ShowDAO {

  def getById(showId: UUID): DBIO[Option[Show]]
  def list(): DBIO[Seq[Show]]

  def getSeasonById(showId: UUID, seasonId: UUID): DBIO[Option[Season]]
  def listSeasons(showId: UUID): DBIO[Seq[Season]]

  def getEpisodeById(showId: UUID, seasonId: UUID, episodeId: UUID): DBIO[Option[Episode]]
  def listEpisodes(showId: UUID, seasonId: UUID): DBIO[Seq[Episode]]

}
