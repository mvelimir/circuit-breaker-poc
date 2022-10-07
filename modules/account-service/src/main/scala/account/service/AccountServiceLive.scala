package account.service

import io.getquill.LowerCase
import io.getquill.jdbczio.Quill
import model.{Account, AccountId}
import zio.{Task, URLayer, ZLayer}

final case class AccountServiceLive(quill: Quill.H2[LowerCase]) extends AccountService {

  import quill._

  override def create(username: String, password: String): Task[Account] =
    for {
      user <- Account.make(username, password)
      _    <- run(query[Account].insertValue(lift(user)))
    } yield user

  override def get(id: AccountId): Task[Option[Account]] =
    run(query[Account].filter(_.id == lift(id))).map(_.headOption)

  def getByUsername(username: String): Task[Option[Account]] =
    run(query[Account].filter(_.username == lift(username))).map(_.headOption)

  override def update(id: AccountId, username: Option[String] = None, password: Option[String] = None): Task[Unit] =
    run(
      dynamicQuery[Account]
        .filter(_.id == lift(id))
        .update(
          setOpt(_.username, username),
          setOpt(_.password, password)
        )
    ).unit

}

object AccountServiceLive {

  val layer: URLayer[Quill.H2[LowerCase], AccountServiceLive] = ZLayer.fromFunction(AccountServiceLive.apply _)

}
