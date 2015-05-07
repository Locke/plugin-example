lazy val commonSettings = Seq(
  version := "0.0.2",
  scalaVersion := "2.11.6"
)

lazy val root = (project in file(".")).
  aggregate(library, plugin, application). // run Tasks on all subprojects
  settings(commonSettings)

lazy val library = project.
  settings(commonSettings)

lazy val copyPluginToApplication = taskKey[Unit]("package Plugin and copy jar file to application/src/pack/exampleplugin.jar")

lazy val plugin = project.
  settings(commonSettings).
  settings(
    copyPluginToApplication := {
      val (art, file) = packagedArtifact.in(Compile, packageBin).value
      "cp " + file.getAbsolutePath + " ./application/src/pack/lib/exampleplugin.jar" !
    }
  ).
  dependsOn(library)

lazy val application = project.
  settings(commonSettings).
  settings(
    packAutoSettings,
    pack <<= pack dependsOn (copyPluginToApplication in plugin)
  ).
  dependsOn(library)

lazy val runPacked = taskKey[Unit]("pack Application and run it")

runPacked := {
  "application/target/pack/bin/boot" !
}

runPacked <<= runPacked dependsOn (pack in application)
