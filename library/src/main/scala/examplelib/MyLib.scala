package examplelib

import java.io.File
import java.net.{ URL, URLClassLoader }

import com.typesafe.config.{ Config, ConfigFactory }

class MyLib(val pluginFiles: Map[String, File]) {
  var plugins: Seq[MyPlugin] = Seq()

  def loadPlugins(): Unit = {

    val urls: Array[URL] = pluginFiles.values.map(_.toURI.toURL).toArray

    val loader = new URLClassLoader(urls, getClass().getClassLoader())

    for (className <- pluginFiles.keys) {
      val clazz = loader.loadClass(className)

      val o = clazz.newInstance

      o match {
        case x: MyPlugin => plugins +:= x
        case x => println("unable to load, expected MyPlugin: " + x)
      }
    }
  }

  def printInfo(): Unit = {
    println("MyLib.printInfo: pluginFiles: " + pluginFiles)
    println("MyLib.printInfo: plugins: " + plugins)

    val config: Config = ConfigFactory.load()

    val a: String = config.getString("example.a")

    println("MyLib.printInfo: config key 'a' = '" + a + "'")

    for (plugin <- plugins) {
      plugin.printInfo
    }
  }
}
