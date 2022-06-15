package app.tusee.auth

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import slick.jdbc.PostgresProfile.api._

class PasswordServlet(val db: Database)   extends ScalatraServlet with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats
}
