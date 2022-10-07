package model

import zio.UIO
import zio.json.{DeriveJsonCodec, JsonCodec}

final case class Account(id: AccountId, username: String, password: String)

object Account {

  def make(username: String, password: String): UIO[Account] =
    AccountId.random.map(Account(_, username, password))

  implicit val codec: JsonCodec[Account] = DeriveJsonCodec.gen[Account]

}
