object DataSetsDemo extends App {

  import org.apache.spark.sql.SparkSession
  import spark.implicits._

  val spark = SparkSession.builder.
  master("local").appName("Flight data Analysis").getOrCreate()

  val flightsDF = spark.read.parquet("/data/flight-data/parquet/2010-summary.parquet/")

  val flightsDataSet = flightsDF.as[Flights]

  flightsDataSet.filter(row => row.ORIGIN_COUNTRY_NAME != "CANADA")
    .take(5)

  val staticDataFrame = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("/data/retail-data/by-day/*.csv")

  staticDataFrame.createorReplaceTempView()
  val staticSchema = staticDataFrame.schema
}
