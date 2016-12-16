package sbux.common

import com.typesafe.config.ConfigFactory

object AppConfig {
  protected val config = ConfigFactory.load()

  protected val appConfig = config.getConfig("app")
  val name = appConfig.getString("name")
  val magic = appConfig.getInt("magic")
}
