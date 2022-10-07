package auth.config

import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{PropertyTreePath, ReadError, read}
import zio.{Layer, ZLayer}

final case class HttpServerConfig(host: String, port: Int)

object HttpServerConfig {

  val layer: Layer[ReadError[String], HttpServerConfig] =
    ZLayer {
      read {
        descriptor[HttpServerConfig].from(
          TypesafeConfigSource.fromResourcePath.at(PropertyTreePath.$("httpServer"))
        )
      }
    }

}
