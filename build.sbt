import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  List(
    scalaVersion               := "2.13.8",
    semanticdbEnabled          := true,
    semanticdbVersion          := scalafixSemanticdb.revision,
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
    scalafixDependencies ++= Dependencies.ScalaFix.value,
    scalacOptions ++= List(
      "-Ywarn-unused"
    )
  )
)

addCommandAlias("prepare", "fix; fmt")
addCommandAlias("check", "fixCheck; fmtCheck")
addCommandAlias("fix", "scalafixAll")
addCommandAlias("fixCheck", "scalafixAll --check")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
addCommandAlias("fmtCheck", "all scalafmtSbtCheck scalafmtCheckAll")

lazy val root = project
  .in(file("."))
  .settings(
    name := "circuit-breaker-poc"
  )
  .aggregate(
    client,
    accountService,
    authService,
    shared
  )

lazy val client = project
  .in(file("modules/client"))
  .settings(
    name                            := "circuit-breaker-poc-client",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Dependencies.Client.value
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared)

lazy val accountService = project
  .in(file("modules/account-service"))
  .settings(
    name := "circuit-breaker-poc-account-service",
    libraryDependencies ++= Dependencies.AccountService.value
  )
  .dependsOn(shared)

lazy val authService = project
  .in(file("modules/auth-service"))
  .settings(
    name := "circuit-breaker-poc-auth-service",
    libraryDependencies ++= Dependencies.AuthService.value
  )
  .dependsOn(shared)

lazy val shared = project
  .in(file("modules/shared"))
  .settings(
    name := "circuit-breaker-poc-shared",
    libraryDependencies ++= Dependencies.Shared.value
  )
