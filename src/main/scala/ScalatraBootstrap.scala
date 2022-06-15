import app.tusee.auth._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new LoginServlet, "/login")
    context.mount(new RegistrationServlet, "/register")
    context.mount(new PasswordServlet, "/password")
    context.mount(new TuseeServlet, "/*")
  }
}
