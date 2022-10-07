package server

sealed trait AppError extends Throwable

object AppError {

  case object MissingAuthorizationError               extends AppError
  case object MissingBodyError                        extends AppError
  case object JwtDecodingError                        extends AppError
  case object JwtMissingSubject                       extends AppError
  final case class JsonDecodingError(message: String) extends AppError
  final case class InvalidIdError(message: String)    extends AppError

}
