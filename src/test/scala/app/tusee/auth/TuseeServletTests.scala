package app.tusee.auth

import app.tusee.TuseeServlet
import org.scalatra.test.scalatest._

class TuseeServletTests extends ScalatraFunSuite {

  addServlet(classOf[TuseeServlet], "/*")

  test("GET / on TuseeServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

  override def header = ???
}
