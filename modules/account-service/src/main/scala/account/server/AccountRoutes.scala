package account.server

import account.service.AccountService
import model.api.{CreateAccount, UpdateAccount}
import server.ServerUtils.{parseAuthorizationHeader, parseBody}
import zhttp.http.{!!, ->, /, Http, HttpApp, Method, Request, Response}
import zio.json.EncoderOps
import zio.{URLayer, ZLayer}

import java.security.PublicKey

final case class AccountRoutes(service: AccountService, publicKey: PublicKey) {

  val routes: HttpApp[Any, Throwable] = Http.collectZIO[Request] {

    case req @ Method.GET -> !! / "account" =>
      for {
        accountId <- parseAuthorizationHeader(req, publicKey)
        account   <- service.get(accountId)
      } yield Response.json(account.toJson)

    case Method.GET -> !! / "accounts" / username / password =>
      service
        .getByUsername(username)
        .map(accountOption => Response.json(accountOption.filter(_.password == password).toJson))

    case req @ Method.POST -> !! / "accounts" =>
      for {
        createAccount <- parseBody[CreateAccount](req)
        account       <- service.create(createAccount.username, createAccount.password)
      } yield Response.json(account.toJson)

    case req @ Method.PATCH -> !! / "account" =>
      for {
        accountId     <- parseAuthorizationHeader(req, publicKey)
        updateAccount <- parseBody[UpdateAccount](req)
        _             <- service.update(accountId, updateAccount.username, updateAccount.password)
      } yield Response.ok

  }

}

object AccountRoutes {

  val layer: URLayer[AccountService with PublicKey, AccountRoutes] = ZLayer.fromFunction(AccountRoutes.apply _)

}
