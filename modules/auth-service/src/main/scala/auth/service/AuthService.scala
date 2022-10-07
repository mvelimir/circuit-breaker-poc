package auth.service

import zio.Task

trait AuthService {

  def login(username: String, password: String): Task[String]

}
