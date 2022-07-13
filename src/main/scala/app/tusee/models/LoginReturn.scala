package app.tusee.models

case class LoginReturn(error: Boolean, reason: String, token: Option[String], boards: Option[Array[Board]], keys: Option[Array[Key]])
