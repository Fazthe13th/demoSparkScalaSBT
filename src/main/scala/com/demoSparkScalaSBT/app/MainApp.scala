package com.demoSparkScalaSBT.app
import com.demoSparkScalaSBT.models._
import com.demoSparkScalaSBT.transformations.DataTransformations.changeStringTOdate
import com.demoSparkScalaSBT.utils.sparkUtils.{getSparkSession, readFile, writeDFToDB}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Encoders}

object MainApp {
  def main(args: Array[String]): Unit = {
    val spark = getSparkSession()
    implicit val personEncoderSchema: StructType = Encoders.product[Persons].schema
    val personsDF = readFile(spark = spark,
      filePath =  "src/main/resources/data/persons_large.json",
      schema= personEncoderSchema, format = "json", multiline=  "true")
    personsDF.show()
    implicit val carsEncoderschema: StructType = Encoders.product[Cars].schema
    val carsDF = readFile(spark, "src/main/resources/data/cars.json",
      carsEncoderschema,"json", "false")
    val carsDFConverted:DataFrame = changeStringTOdate(carsDF,"year")
    carsDFConverted.printSchema()
    carsDFConverted.show( )
    // write df to db
    writeDFToDB(carsDFConverted, "cars")
    writeDFToDB(personsDF, "persons")
    spark.stop()



  }
}
