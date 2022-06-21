package app.tusee.auth

import app.tusee.auth.models.{LoginReturn, LoginValues}
import de.mkammerer.argon2.Argon2Factory
import org.scalatra.{CorsSupport, ScalatraServlet}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import slick.jdbc.PostgresProfile.api._

import java.time.Instant
import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

class LoginServlet(val db: Database)  extends ScalatraServlet with JacksonJsonSupport with CorsSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/") {
    val result = parsedBody.extract[LoginValues]
    val action = Tables.users.filter(_.email === result.email).result
    val dbResult: Future[Seq[(String, String, String, String, Instant, Boolean, Boolean, String)]] = db.run(action)
    val resolved = Await.result(dbResult, Duration.Inf)
    if (resolved.nonEmpty) {
      val argon2 = Argon2Factory.create()
      println(resolved.head._4)
      val res = argon2.verify(resolved.head._4, result.password.toCharArray)
      println(res)
      if (res) {
        LoginReturn(error = false, reason = "Credentials are VALID")
      } else {
        println("a")
        LoginReturn(error = true, reason = "Credentials are invalid")
      }
    } else {
      println("b")
      LoginReturn(error = true, reason = "Credentials are invalid")
    }
  }
}
