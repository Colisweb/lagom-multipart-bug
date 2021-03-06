addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.3.6")

// I you want more logs, you will need to use your own build of the Lagom master branch or the 1.3.x branch:
//addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.3.7-SNAPSHOT")
//addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.0-SNAPSHOT")

addSbtPlugin("com.lightbend.conductr" % "sbt-conductr" % "2.3.4")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.3")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC6")
