import app.tusee.{LoginServlet, PasswordServlet, RegistrationServlet, TuseeServlet}
import app.tusee._
import org.scalatra._
import org.slf4j.{Logger, LoggerFactory}
import com.mchange.v2.c3p0.ComboPooledDataSource
import slick.jdbc.PostgresProfile.api._

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  override def init(context: ServletContext): Unit = {
    val db = Database.forDataSource(cpds, None)

    context.mount(new LoginServlet(db), "/login")
    context.mount(new RegistrationServlet(db), "/register")
    context.mount(new PasswordServlet(db), "/password")
    context.mount(new TuseeServlet, "/*")
  }

  private def closeDbConnection(): Unit = {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }

  override def destroy(context: ServletContext): Unit = {
    super.destroy(context)
    closeDbConnection()
  }
}
