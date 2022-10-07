package model

import zio.json.JsonCodec
import zio.{Random, Task, UIO, ZIO}

import java.util.UUID

final case class AccountId(id: UUID) extends AnyVal

object AccountId {

  def random: UIO[AccountId] = Random.nextUUID.map(AccountId(_))

  def fromString(id: String): Task[AccountId] =
    ZIO.attempt {
      AccountId(UUID.fromString(id))
    }

  implicit val codec: JsonCodec[AccountId] = JsonCodec[UUID].transform(AccountId(_), _.id)

}
