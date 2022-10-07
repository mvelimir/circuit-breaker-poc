package auth.service

import auth.config.AccountServiceConfig
import model.{Account, AccountId}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3.ziojson.asJson
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import zio.{Task, URLayer, ZIO, ZLayer}

import java.security.PrivateKey
import java.time.Clock

import AuthServiceLive.{Backend, clock}

final case class AuthServiceLive(accountServiceConfig: AccountServiceConfig, backend: Backend, privateKey: PrivateKey)
    extends AuthService {

  override def login(username: String, password: String): Task[String] =
    backend
      .send(
        basicRequest.get(uri"${accountServiceConfig.baseUri}/accounts/$username/$password").response(asJson[Account])
      )
      .flatMap { response =>
        ZIO.fromEither(response.body).map(account => jwtEncode(account.id))
      }

  private def jwtEncode(accountId: AccountId): String = {
    val jwtClaim = JwtClaim(subject = Option(accountId.id.toString)).issuedNow.expiresIn(180)

    Jwt.encode(jwtClaim, privateKey, JwtAlgorithm.ES256)
  }

}

object AuthServiceLive {

  type Backend = SttpBackend[Task, ZioStreams with WebSockets]

  implicit val clock: Clock = Clock.systemUTC

  val layer: URLayer[AccountServiceConfig with Backend with PrivateKey, AuthService] =
    ZLayer.fromFunction(AuthServiceLive.apply _)

}
