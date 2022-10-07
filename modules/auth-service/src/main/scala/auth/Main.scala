package auth

import auth.config.{AccountServiceConfig, HttpServerConfig}
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio.{Task, ZIO, ZIOAppDefault}

import service.AuthServiceLive
import server.{AuthServer, AuthRoutes}

object Main extends ZIOAppDefault {

  def run: Task[Unit] =
    ZIO
      .serviceWithZIO[AuthServer](_.start)
      .provide(
        AuthServiceLive.layer,
        AsyncHttpClientZioBackend.layer(),
        AccountServiceConfig.layer,
        HttpServerConfig.layer,
        AuthRoutes.layer,
        AuthServer.layer,
        PrivateKey.layer
      )

}
