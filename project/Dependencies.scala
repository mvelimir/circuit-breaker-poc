import sbt._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Dependencies {

  object Versions {
    val FlyWay          = "9.4.0"
    val H2Database      = "2.1.214"
    val JwtScala        = "9.1.1"
    val Laminar         = "0.14.2"
    val OrganizeImports = "0.6.0"
    val QuillJdbcZio    = "4.5.0"
    val SttpClient3     = "3.8.2"
    val Waypoint        = "0.5.0"
    val ZioConfig       = "3.0.2"
    val ZioHttp         = "2.0.0-RC11"
    val ZioJson         = "0.3.0"
  }

  val ScalaFix = Def.setting(
    List(
      "com.github.liancheng" %% "organize-imports" % Versions.OrganizeImports
    )
  )

  val Client = Def.setting(
    List(
      "com.raquo" %%% "laminar"  % Versions.Laminar,
      "com.raquo" %%% "waypoint" % Versions.Waypoint
    )
  )

  val AccountService = Def.setting(
    List(
      "org.flywaydb"   % "flyway-core"         % Versions.FlyWay,
      "com.h2database" % "h2"                  % Versions.H2Database,
      "io.getquill"   %% "quill-jdbc-zio"      % Versions.QuillJdbcZio,
      "dev.zio"       %% "zio-config"          % Versions.ZioConfig,
      "dev.zio"       %% "zio-config-magnolia" % Versions.ZioConfig,
      "dev.zio"       %% "zio-config-typesafe" % Versions.ZioConfig
    )
  )

  val AuthService = Def.setting(
    List(
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % Versions.SttpClient3,
      "com.softwaremill.sttp.client3" %% "zio-json"                      % Versions.SttpClient3,
      "dev.zio"                       %% "zio-config"                    % Versions.ZioConfig,
      "dev.zio"                       %% "zio-config-magnolia"           % Versions.ZioConfig,
      "dev.zio"                       %% "zio-config-typesafe"           % Versions.ZioConfig
    )
  )

  val Shared = Def.setting(
    List(
      "dev.zio"              %% "zio-json" % Versions.ZioJson,
      "io.d11"               %% "zhttp"    % Versions.ZioHttp,
      "com.github.jwt-scala" %% "jwt-core" % Versions.JwtScala
    )
  )

}
