lazy val commonSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.11.6"
)

lazy val root = (project in file(".")).
  aggregate(library, plugin, application). // run Tasks on all subprojects
  settings(commonSettings).
  settings(
    (run in Compile) <<= (run in (application, Compile)) // overwrite run Task to use the run Task of the application project
  )

lazy val library = project.
  settings(commonSettings)

lazy val plugin = project.
  settings(commonSettings).
  dependsOn(library)

lazy val application = project.
  settings(commonSettings).
  settings(
    (run in Compile) <<= (run in Compile) dependsOn (packageBin in (plugin, Compile)) // package needs to be build before run of application
  ).
  dependsOn(library)
