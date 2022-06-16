package app.tusee.auth

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import slick.jdbc.PostgresProfile.api._

class TuseeServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  get("/") {
    views.html.hello()
  }

}
