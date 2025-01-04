package com.demoSparkScalaSBT.utils

import com.demoSparkScalaSBT.config.AppConfig
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

object sparkUtils {
  def getSparkSession(): SparkSession = {
    SparkSession.builder()
      .appName(AppConfig.SparkConfig.appName)
      .master(AppConfig.SparkConfig.master)
      .getOrCreate()
  }
  def readFile(spark: SparkSession, filePath:String,
               schema: StructType, format: String, multiline: String): DataFrame =
    {
      val mapOptions: Map[String, String] =
        Map(
          "multiline" -> multiline,
          "path" -> filePath
        )
      spark.read
        .format(format)
        .schema(schema)
        .options(mapOptions).load().toDF()
    }
  def writeDFToDB( df: DataFrame, tableName: String):Unit =
    {
      val mapWriteOptions = Map(
        "url" -> AppConfig.PGDatabaseConfig.url,
        "dbtable" -> tableName,
        "user" -> AppConfig.PGDatabaseConfig.user,
        "password" -> AppConfig.PGDatabaseConfig.password,
        "driver" -> AppConfig.PGDatabaseConfig.driver,
      )
      df.write
        .format("jdbc")
        .options(mapWriteOptions)
        .mode("overwrite")  // "overwrite", "append", "ignore", "error"
        .save()
    }
}
