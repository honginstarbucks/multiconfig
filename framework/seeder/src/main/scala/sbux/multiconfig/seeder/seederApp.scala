package sbux.multiconfig.seeder

import sbux.common.AppConfig

object seederApp extends App {
  println(s"name= ${AppConfig.name}; magic = ${AppConfig.magic}")
}
