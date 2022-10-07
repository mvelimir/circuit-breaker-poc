package account

import account.config.{HttpServerConfig, PublicKeyConfig}
import io.getquill.LowerCase
import io.getquill.jdbczio.Quill.{DataSource, H2}
import zio.{Task, ZIO, ZIOAppDefault}

import service.AccountServiceLive
import server.{AccountServer, AccountRoutes}

object Main extends ZIOAppDefault {

  def run: Task[Unit] = {
    val app = for {
      _ <- ZIO.serviceWithZIO[Migrations](_.migrate)
      _ <- ZIO.serviceWithZIO[AccountServer](_.start)
    } yield ()

    app
      .provide(
        Migrations.layer,
        H2.fromNamingStrategy(LowerCase),
        DataSource.fromPrefix("database"),
        AccountServiceLive.layer,
        AccountRoutes.layer,
        HttpServerConfig.layer,
        PublicKeyConfig.layer,
        PublicKey.layer,
        AccountServer.layer
      )
  }

}
