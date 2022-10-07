package server

import model.AccountId
import pdi.jwt.Jwt
import zhttp.http.Request
import zhttp.http.middleware.Auth.Credentials
import zio.json.{DecoderOps, JsonDecoder}
import zio.{IO, ZIO}

import java.security.PublicKey

object ServerUtils {

  def parseBasicAuthorization(request: Request): IO[AppError.MissingAuthorizationError.type, Credentials] =
    ZIO.from(request.basicAuthorizationCredentials).orElseFail(AppError.MissingAuthorizationError)

  def parseAuthorizationHeader(request: Request, publicKey: PublicKey): IO[AppError, AccountId] =
    for {
      token     <- ZIO.from(request.bearerToken).orElseFail(AppError.MissingAuthorizationError)
      jwtClaim  <- ZIO.from(Jwt.decode(token, publicKey)).orElseFail(AppError.JwtDecodingError)
      subject   <- ZIO.from(jwtClaim.subject).orElseFail(AppError.JwtMissingSubject)
      accountId <- parseAccountId(subject)
    } yield accountId

  def parseBody[A: JsonDecoder](request: Request): IO[AppError, A] =
    for {
      body   <- request.body.asString.orElseFail(AppError.MissingBodyError)
      parsed <- ZIO.from(body.fromJson[A]).mapError(AppError.JsonDecodingError)
    } yield parsed

  def parseAccountId(id: String): IO[AppError.InvalidIdError, AccountId] =
    AccountId.fromString(id).orElseFail(AppError.InvalidIdError("Invalid Account ID"))

}
