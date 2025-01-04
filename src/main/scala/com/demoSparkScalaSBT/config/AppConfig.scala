package com.demoSparkScalaSBT.config
import com.typesafe.config.{Config, ConfigFactory}
object AppConfig {
  private val config: Config = ConfigFactory.load()

  object SparkConfig {
    val appName: String = config.getString("spark.appName")
    val master: String = config.getString("spark.master")
  }

  object PGDatabaseConfig {
    val url: String = config.getString("pg_database.pg_url")
    val user: String = config.getString("pg_database.pg_user")
    val password: String = config.getString("pg_database.pg_password")
    val driver: String = config.getString("pg_database.pg_driver")
  }
}
