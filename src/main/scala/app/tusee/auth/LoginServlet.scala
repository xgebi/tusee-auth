package app.tusee.auth

import app.tusee.auth.models.{Board, Key, LoginReturn, LoginValues}
import de.mkammerer.argon2.Argon2Factory
import org.scalatra.{CorsSupport, ScalatraServlet}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import slick.jdbc.PostgresProfile.api._

import java.time.Instant
import java.util.UUID
import scala.collection.mutable.ArrayBuffer
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
      val res = argon2.verify(resolved.head._4, result.password.toCharArray)
      val boards = new ArrayBuffer[Board]()
      if (res) {
        val keyAction = Tables.keys.filter(_.userUuid === resolved.head._1).result
        val keysResult = Await.result(db.run(keyAction), Duration.Inf).map((key) => {
          val boardAction = Tables.boards.filter(_.boardUuid === key._1).result
          boards += Await.result(db.run(boardAction), Duration.Inf).map((b) => Board(boardUuid = b._1, name = b._2, description = b._3, owner = b._4, created = b._5, columns = b._6 )).head

          Key(keyUuid = key._1, key = key._3, board = key._4)
        })

        LoginReturn(error = false, reason = "Credentials are VALID", boards = Some(boards.toArray), keys = Some(keysResult.toArray))
      } else {
        println("a")
        LoginReturn(error = true, reason = "Credentials are invalid", boards = None, keys = None)
      }
    } else {
      println("b")
      LoginReturn(error = true, reason = "Credentials are invalid", boards = None, keys = None)
    }
  }
}
