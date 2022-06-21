package app.tusee.auth.models

case class LoginReturn(error: Boolean, reason: String, boards: Option[Array[Board]], keys: Option[Array[Key]])
