package exampleplugin

import com.typesafe.config.{ Config, ConfigFactory }

import examplelib.MyPlugin

class MyPluginImplementation extends MyPlugin {
  def printInfo(): Unit = {
    val config: Config = ConfigFactory.load()

    val a: String = config.getString("example.a")

    println("MyPluginImplementation.printInfo: config key 'a' = '" + a + "'")
  }
}
