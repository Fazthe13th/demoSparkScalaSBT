package com.demoSparkScalaSBT.transformations
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
object DataTransformations {
  def changeStringTOdate(df: DataFrame, colName: String): DataFrame = {
    df.withColumn(colName, to_date(col(colName),"yyyy-MM-dd"))
  }
}
