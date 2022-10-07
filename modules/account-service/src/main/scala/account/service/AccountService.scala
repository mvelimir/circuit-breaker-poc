package account.service

import model.{Account, AccountId}
import zio.Task

trait AccountService {

  def create(username: String, password: String): Task[Account]

  def get(id: AccountId): Task[Option[Account]]

  def getByUsername(username: String): Task[Option[Account]]

  def update(id: AccountId, username: Option[String] = None, password: Option[String] = None): Task[Unit]

}
