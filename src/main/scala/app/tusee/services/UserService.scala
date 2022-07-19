package app.tusee.services

import app.tusee.{DbClient, Tables}

import java.time.Instant
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import slick.jdbc.PostgresProfile.api._

trait UserDbCalls {
    def getUserByEmail(email: String): Seq[(String, String, String, String, Instant, Boolean, Boolean, String)] = {
      val db = DbClient.db.get
      val action = Tables.users.filter(_.email === email).result
      val dbResult: Future[Seq[(String, String, String, String, Instant, Boolean, Boolean, String)]] = db.run(action)
      Await.result(dbResult, Duration.Inf)
    }
  }

object UserService extends UserDbCalls {
}
