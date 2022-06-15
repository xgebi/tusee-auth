package app.tusee.auth

import app.tusee.auth.models.RegistrationValues
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

class RegistrationServlet  extends ScalatraServlet with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/") {
    val result = parsedBody.extract[RegistrationValues]
    println(result)
    result
  }
}
