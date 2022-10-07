package auth

import pdi.jwt.JwtUtils
import zio.{TaskLayer, ZIO, ZLayer}

import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyFactory, PrivateKey => JavaPrivateKey}
import java.util.Base64

object PrivateKey {

  val layer: TaskLayer[JavaPrivateKey] = ZLayer.fromZIO {
    ZIO.attempt {
      val PrivateKeyString =
        "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCCST1ul9fc78M7winDfSJ5He3VAFHAfnxkzNRLKjhg4/g=="
      val keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder.decode(PrivateKeyString))

      KeyFactory.getInstance(JwtUtils.ECDSA).generatePrivate(keySpec)
    }
  }

}
