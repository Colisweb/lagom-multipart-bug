import com.lightbend.lagom.sbt.LagomImport._

name := "lagom-multipart-bug"

version := "1.0"

scalaVersion in ThisBuild := "2.11.11"
autoCompilerPlugins := true
lagomCassandraEnabled in ThisBuild := false
scalafmtOnCompile in ThisBuild := true

val macwire = (version: String) =>
  Seq(
    "com.softwaremill.macwire" %% "macros" % version,
    "com.softwaremill.macwire" %% "util"   % version,
    "com.softwaremill.macwire" %% "proxy"  % version
  )

def project(id: String) =
  Project(id = id, base = file(id))
    .settings(
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in packageDoc := false,
      sources in (Compile, doc) := Seq.empty,
      scalacOptions ++= Seq(
        "-deprecation",
        "-target:jvm-1.8",
        "-encoding",
        "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-unchecked",
        "-Xlint",
        "-Xlint:missing-interpolator",
        "-Yno-adapted-args",
        "-Ywarn-unused",
        "-Ywarn-dead-code",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture",
        "-Ywarn-unused-import"
      )
    )

def scalaServiceApi(id: String) =
  project(id)
    .settings(
      resolvers ++= Seq(Resolver.sonatypeRepo("public")),
      libraryDependencies ++= Seq(
        lagomScaladslApi,
        lagomScaladslPersistenceJdbc
      )
    )

def scalaServiceImpl(id: String) =
  project(id)
    .enablePlugins(LagomScala, LagomConductRPlugin)
    .settings(
      libraryDependencies ++= Seq(
        ws,
        lagomScaladslServer,
        filters
      ) ++ macwire("2.2.5")
    )

lazy val uploadApi  = scalaServiceApi("upload-api")
lazy val uploadImpl = scalaServiceImpl("upload-impl").dependsOn(uploadApi)
