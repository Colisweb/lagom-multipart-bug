import com.lightbend.lagom.sbt.LagomImport
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

val testKitLibs = Seq(
  LagomImport.component("lagom-scaladsl-testkit") % Test,
  "org.mockito"                                   % "mockito-core" % "2.10.0" % Test,
  "org.scalacheck"                                %% "scalacheck" % "1.13.5" % Test,
  "org.scalactic"                                 %% "scalactic" % "3.0.4" % Test,
  "org.scalatest"                                 %% "scalatest" % "3.0.4" % Test
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
    .enablePlugins(LagomScala)
    .settings(
      libraryDependencies ++= Seq(
        ws,
        lagomScaladslServer,
        filters
      ) ++ macwire("2.2.5") ++ testKitLibs
    )

lazy val uploadApi  = scalaServiceApi("upload-api")
lazy val uploadImpl = scalaServiceImpl("upload-impl").dependsOn(uploadApi)
