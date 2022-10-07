package model.api

import zio.json.{DeriveJsonCodec, JsonCodec}

final case class UpdateAccount(username: Option[String], password: Option[String])

object UpdateAccount {

  implicit val codec: JsonCodec[UpdateAccount] = DeriveJsonCodec.gen[UpdateAccount]

}
