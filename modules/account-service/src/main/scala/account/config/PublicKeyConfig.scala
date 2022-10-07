package account.config

import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{PropertyTreePath, ReadError, read}
import zio.{Layer, ZLayer}

final case class PublicKeyConfig(xCoord: BigInt, yCoord: BigInt, stdName: String)

object PublicKeyConfig {

  val layer: Layer[ReadError[String], PublicKeyConfig] =
    ZLayer {
      read {
        descriptor[PublicKeyConfig].from(
          TypesafeConfigSource.fromResourcePath
            .at(PropertyTreePath.$("publicKey"))
        )
      }
    }

}
