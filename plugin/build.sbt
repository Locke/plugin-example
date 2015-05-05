name := "Plugin for plugin-example"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

artifactPath in Compile in packageBin <<=
   baseDirectory { base => base / ".." / "artifacts" / "exampleplugin.jar" }
