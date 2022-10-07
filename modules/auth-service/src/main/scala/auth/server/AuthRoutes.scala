package auth.server

import auth.service.AuthService
import server.ServerUtils.parseBasicAuthorization
import zhttp.http.{!!, ->, /, Http, HttpApp, Method, Request, Response}
import zio.{URLayer, ZLayer}

final case class AuthRoutes(service: AuthService) {

  val routes: HttpApp[Any, Throwable] = Http.collectZIO[Request] { case req @ Method.GET -> !! / "login" =>
    for {
      credentials <- parseBasicAuthorization(req)
      token       <- service.login(credentials.uname, credentials.upassword)
    } yield Response.text(token)

  }

}

object AuthRoutes {

  val layer: URLayer[AuthService, AuthRoutes] = ZLayer.fromFunction(AuthRoutes(_))

}
