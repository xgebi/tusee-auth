package app.tusee.servlets

import app.tusee.RegistrationServlet
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatra.test.scalatest.ScalatraFunSuite

class RegistrationServletTests extends ScalatraFunSuite with MockFactory with Matchers {

  addServlet(classOf[RegistrationServlet], "/*")

  test("GET / on RegistrationServlet should return status 200") {
    post("/") {
      status should equal (200)
    }
  }

  override def header = ???
}

