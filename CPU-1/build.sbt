// ビルド全体で使用するScalaバージョンを定義
ThisBuild / scalaVersion := "2.13.8"

// プロジェクトのバージョンを設定
ThisBuild / version := "0.1.0"

// 組織名を設定
ThisBuild / organization := "Shinrabansho.CPU"

// ルートプロジェクトの設定を定義
lazy val root = (project in file("."))
  .settings(
    // プロジェクトの名前を設定
    name := "shinrabansyo_cpu",
    
    // Chiselに関連するライブラリ依存関係を追加します
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.5.4", // ハードウェア構築言語Chisel3
      "edu.berkeley.cs" %% "chisel-iotesters" % "2.5.6", // Chisel用のI/Oテスター
      "edu.berkeley.cs" %% "chiseltest" % "0.5.4" % "test" // ユニットテスト用のChiselテストフレームワーク
    ),
    
    // 機能を有効にし、非推奨やその他の問題をチェックするためのScalaコンパイラオプションを設定
    scalacOptions ++= Seq(
      "-Xsource:2.13", // Scala 2.13の互換モード
      "-language:reflectiveCalls", // リフレクティブコールを許可
      "-deprecation", // 非推奨APIの使用箇所に警告と位置を出力
      "-feature", // 明示的にインポートする必要がある機能の使用箇所に警告と位置を出力
      "-Xcheckinit", // 初期化されていない値に対するランタイムチェックを追加
      "-P:chiselplugin:useBundlePlugin", // ワイヤ接続を生成するためのChisel BundlePluginを使用
      "-Ymacro-annotations" // マクロアノテーションを有効
    ),
    
    // ハードウェア設計を補助するChisel用コンパイラプラグインを追加
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.4" cross CrossVersion.full)
  )
