package account.server

import account.config.HttpServerConfig
import zhttp.service.Server
import zio.{Task, URLayer, ZLayer}

final case class AccountServer(accountRoutes: AccountRoutes, config: HttpServerConfig) {

  val start: Task[Unit] = {
    val server = Server.bind(config.host, config.port) ++ Server.app(accountRoutes.routes)

    server.startDefault
  }

}

object AccountServer {

  val layer: URLayer[AccountRoutes with HttpServerConfig, AccountServer] = ZLayer.fromFunction(AccountServer.apply _)

}
