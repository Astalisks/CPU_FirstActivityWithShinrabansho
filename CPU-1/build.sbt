ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "org.example"


lazy val root = (project in file("."))
  .settings(
    name := "MyChiselProject",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.5.4", // Chiselのメインライブラリ
      "edu.berkeley.cs" %% "chiseltest" % "0.5.4" % "test", // Chiselのテストライブラリ
      "edu.berkeley.cs" %% "chisel-iotesters" % "2.5.6" % "test" // テスト用の旧式ライブラリ
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.13",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:useBundlePlugin",
      "-Ymacro-annotations"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.4" cross CrossVersion.full)
  )


  