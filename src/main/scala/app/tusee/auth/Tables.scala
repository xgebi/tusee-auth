package app.tusee.auth

import slick.jdbc.PostgresProfile.api._

import java.sql.Timestamp
import java.time.Instant
import java.util.Date

object Tables {

  class Users(tag: Tag) extends Table[(String, String, String, String, Instant, Boolean, Boolean, String)](tag, "tusee_users") {
    def userUuid = column[String]("user_uuid", O.PrimaryKey)
    def displayName = column[String]("display_name")
    def email = column[String]("email", O.Unique)
    def password = column[String]("password")
    def created = column[Instant]("created")
    def firstLogin = column[Boolean]("first_login")
    def usesTotp = column[Boolean]("uses_totp")
    def totpSecret = column[String]("totp_secret")

    def * = (userUuid, displayName, email, password, created, firstLogin, usesTotp, totpSecret)
  }

  val users = TableQuery[Users]

  class Keys(tag: Tag) extends Table[(String, String, String, String)](tag, "tusee_encrypted_keys") {
    def keyUuid = column[String]("key_uuid", O.PrimaryKey)
    def userUuid = column[String]("tusee_user")
    def key = column[String]("key")
    def board = column[String]("board")

    def * = (keyUuid, userUuid, key, board)
  }

  val keys = TableQuery[Keys]

  class Boards(tag: Tag) extends Table[(String, String, String, String, Instant, String)](tag, "tusee_boards") {
    def boardUuid = column[String]("board_uuid", O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")
    def owner = column[String]("owner")
    def created = column[Instant]("created")
    def columns = column[String]("columns")

    def * = (boardUuid, name, description, owner, created, columns)
  }

  val boards = TableQuery[Boards]
}
