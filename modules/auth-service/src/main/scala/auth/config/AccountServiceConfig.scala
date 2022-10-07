package auth.config

import sttp.model.Uri
import zio.config.magnolia.{Descriptor, descriptor}
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{PropertyTreePath, ReadError, read}
import zio.{Layer, ZLayer}

final case class AccountServiceConfig(baseUri: Uri)

object AccountServiceConfig {

  implicit val sttpUriDescriptor: Descriptor[Uri] =
    Descriptor[String].transformOrFailLeft(Uri.parse)(_.toString)

  val layer: Layer[ReadError[String], AccountServiceConfig] =
    ZLayer {
      read {
        descriptor[AccountServiceConfig].from(
          TypesafeConfigSource.fromResourcePath.at(PropertyTreePath.$("accountService"))
        )
      }
    }

}
