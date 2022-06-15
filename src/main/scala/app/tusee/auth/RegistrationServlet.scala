package app.tusee.auth

import app.tusee.auth.models.{RegistrationReturn, RegistrationValues}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import slick.jdbc.PostgresProfile.api._
import app.tusee.auth.Tables
import de.mkammerer.argon2.Argon2Factory

import java.sql.{Date, Timestamp}
import java.time.{Instant, LocalDate}
import java.util
import java.util.UUID
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class RegistrationServlet(val db: Database)  extends ScalatraServlet with JacksonJsonSupport with FutureSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  before() {
    contentType = formats("json")
  }

  post("/") {
    val result = parsedBody.extract[RegistrationValues]
    val action = Tables.users.filter(_.email === result.email).result
    val dbResult: Future[Seq[(String, String, String, String, Instant, Boolean, Boolean, String)]] = db.run(action)
    val resolved = Await.result(dbResult, Duration.Inf)
    if (resolved.isEmpty) {
      val argon2 = Argon2Factory.create()
      val uuid = UUID.randomUUID().toString()

      val userTuple = Tuple8(uuid, result.displayName, result.email, argon2.hash(10,65536, 1, result.password),  Instant.now(), true, true, "" )
      val withAddedUser = Tables.users += userTuple
      val resolvedAdd = Await.result(db.run(withAddedUser), Duration.Inf)
      RegistrationReturn(error = false, reason = "User registered")
    } else {
      RegistrationReturn(error = true, reason = "Email already in use")
    }
  }
}
