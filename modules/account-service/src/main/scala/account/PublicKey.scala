package account

import account.config.PublicKeyConfig
import pdi.jwt.JwtUtils
import zio.{ZIO, ZLayer}

import java.security.spec.{ECGenParameterSpec, ECParameterSpec, ECPoint, ECPublicKeySpec}
import java.security.{AlgorithmParameters, KeyFactory, PublicKey => JavaPublicKey}

object PublicKey {

  val layer: ZLayer[PublicKeyConfig, Throwable, JavaPublicKey] =
    ZLayer.fromZIO {
      for {
        config <- ZIO.service[PublicKeyConfig]
        publicKey <- ZIO.attempt {
                       val ecPoint         = new ECPoint(config.xCoord.bigInteger, config.yCoord.bigInteger)
                       val algorithmParams = AlgorithmParameters.getInstance(JwtUtils.ECDSA)

                       algorithmParams.init(new ECGenParameterSpec(config.stdName))

                       val ecParameterSpec = algorithmParams.getParameterSpec(classOf[ECParameterSpec])
                       val keySpec         = new ECPublicKeySpec(ecPoint, ecParameterSpec)

                       KeyFactory.getInstance(JwtUtils.ECDSA).generatePublic(keySpec)
                     }
      } yield publicKey
    }

}
