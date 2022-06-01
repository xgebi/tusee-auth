package app.tusee.auth

import app.tusee.auth.models.LoginValues
import org.scalatra.ScalatraServlet
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class LoginServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/") {
    val result = parsedBody.extract[LoginValues]
    println(result)
    result
  }
}
