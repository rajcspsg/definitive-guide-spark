import org.apache.spark.sql.SparkSession
import spark.implicits._

val sparkSession = SparkSession.builder.
      master("local")
      .appName("spark session example")
      .getOrCreate()

val flightdata2015 = spark.read
.option("inferSchema", "true")
.option("header", "true")
.csv("csv/2015-summary.csv")



