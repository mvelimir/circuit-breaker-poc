package auth.server

import auth.config.HttpServerConfig
import zhttp.service.Server
import zio.{Task, URLayer, ZLayer}

final case class AuthServer(authRoutes: AuthRoutes, config: HttpServerConfig) {

  val start: Task[Unit] = {
    val server = Server.bind(config.host, config.port) ++ Server.app(authRoutes.routes)

    server.startDefault
  }

}

object AuthServer {

  val layer: URLayer[AuthRoutes with HttpServerConfig, AuthServer] = ZLayer.fromFunction(AuthServer.apply _)

}
