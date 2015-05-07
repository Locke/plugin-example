package exampleapp

import java.io.File

import com.typesafe.config.{ Config, ConfigFactory }

import examplelib.MyLib

object Boot {
  def main(args: Array[String]): Unit = {
    val config: Config = ConfigFactory.load()

    val a: String = config.getString("example.a")

    println("exampleapp.Boot: config key 'a' = '" + a + "'")

    val pluginFiles = Map(
        "exampleplugin.MyPluginImplementation" -> new File("../lib/exampleplugin.jar") // TODO: this is relative to pack/bin folder
      )

    val lib = new MyLib(pluginFiles)

    lib.loadPlugins

    lib.printInfo
  }
}
