import app.tusee.auth._
import org.scalatra._
import org.slf4j.LoggerFactory
import com.mchange.v2.c3p0.ComboPooledDataSource
import slick.jdbc.PostgresProfile.api._

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  override def init(context: ServletContext) {
    val db = Database.forDataSource(cpds, None)

    context.mount(new LoginServlet(db), "/login")
    context.mount(new RegistrationServlet(db), "/register")
    context.mount(new PasswordServlet(db), "/password")
    context.mount(new TuseeServlet(db), "/*")
  }

  private def closeDbConnection() = {
    logger.info("Closing c3po connection pool")
    cpds.close
  }

  override def destroy(context: ServletContext) = {
    super.destroy(context)
    closeDbConnection()
  }
}
