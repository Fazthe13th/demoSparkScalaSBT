package com.demoSparkScalaSBT.transformations
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.funsuite.AnyFunSuite
class DataTransformationTest extends AnyFunSuite with DataFrameSuiteBase {
  import spark.implicits._

  test("Cast String col to Date - Check data type") {
    val inputData = Seq(("bmw", "1936-04-05"), ("toyota", "1946-05-03")).toDF("name", "year")
    val resultDF = DataTransformations.changeStringTOdate(inputData, "year")
    // Asserting the schema for the 'year' column to be DateType
    assert(resultDF.schema("year").dataType.typeName == "date")
  }
  test("Cast String col to Date - Check df same") {
    val inputData = Seq(("bmw", "1936-04-05"), ("toyota", "1946-05-03")).toDF("name", "year")
    val resultDF = DataTransformations.changeStringTOdate(inputData, "year")
    // Asserting the transformed data correctness
    val expectedData = Seq(("bmw", java.sql.Date.valueOf("1936-04-05")), ("toyota", java.sql.Date.valueOf("1946-05-03")))
    val expectedDF = expectedData.toDF("name", "year")

    assertDataFrameEquals(resultDF, expectedDF)
  }
}
