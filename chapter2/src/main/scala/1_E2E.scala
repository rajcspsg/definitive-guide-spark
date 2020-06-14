object E2E extends App {

  import org.apache.spark.sql.SparkSession
  import spark.implicits._

  val sparkSession = SparkSession.builder.
    master("local").appName("Flight data Analysis").getOrCreate()

  val flightData2015 = spark.read.option("header", "true").option("inferSchema", "true").csv

  val first3FlightData2015 = flightData2015.take(3)

  import org.apache.spark.sql.functions.max

  flightData2015.select(max("count")).take(1)

  flightData2015.groupBy("DEST_COUNTRY_NAME").sum("count")
    .withColumnRenamed("sum(count)", destination_total)
    .sort(desc("destination_total"))
    .limit(5).show()
}
