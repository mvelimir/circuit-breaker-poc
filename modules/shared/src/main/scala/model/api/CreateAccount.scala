package model.api

import zio.json.{DeriveJsonCodec, JsonCodec}

final case class CreateAccount(username: String, password: String)

object CreateAccount {

  implicit val codec: JsonCodec[CreateAccount] = DeriveJsonCodec.gen[CreateAccount]

}
