package models.json

import core.CoreJson
import io.circe.Encoder
import models.Episode

trait EpisodeJson extends CoreJson {

  implicit val episodeEncoder: Encoder[Episode] = {
    Encoder.forProduct5(
      "id",
      "name",
      "description",
      "releaseDate",
      "seasonId"
    )((episode: Episode) =>
      (episode.id,
        episode.name,
        episode.description,
        episode.releaseDate,
        episode.seasonId
      ))
  }

}
