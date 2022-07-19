package app.tusee

import app.tusee.models.{RegistrationReturn, RegistrationValues}
import app.tusee.services.UserService
import de.mkammerer.argon2.Argon2Factory
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{FutureSupport, ScalatraServlet}
import slick.jdbc.PostgresProfile.api._

import java.time.Instant
import java.util.UUID
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class RegistrationServlet()  extends ScalatraServlet with JacksonJsonSupport with FutureSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats
  protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  before() {
    contentType = formats("json")
  }

  post("/") {
    val result = parsedBody.extract[RegistrationValues]
    val resolved = UserService.getUserByEmail(result.email)
//    if (resolved.isEmpty) {
//      val db = DbClient.db.get
//      val argon2 = Argon2Factory.create()
//      val uuid = UUID.randomUUID().toString
//      val hashedPassword = argon2.hash(10,65536, 1, result.password.toCharArray)
//      val userTuple = Tuple8(uuid, result.displayName, result.email, hashedPassword,  Instant.now(), true, true, "" )
//      val withAddedUser = Tables.users += userTuple
//      if (Await.result(db.run(withAddedUser), Duration.Inf) > 0) {
//        val keysWithAddedKey = Tables.keys += Tuple4(UUID.randomUUID().toString, uuid, result.key, null)
//        if (Await.result(db.run(keysWithAddedKey), Duration.Inf) > 0) {
//          RegistrationReturn(error = false, reason = "User registered")
//        } else {
//          RegistrationReturn(error = true, reason = "Database error")
//        }
//      } else {
//        RegistrationReturn(error = true, reason = "Database error")
//      }
//    } else {
//      RegistrationReturn(error = true, reason = "Email already in use")
//    }
  }
}
