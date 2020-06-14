object StructuredStreamingApp extends App {
  
  import org.apache.spark.sql.SparkSession
  import spark.implicits._

  val spark = SparkSession.builder.master("local").appName("StructuredStreamingDemo").getOrCreate

  val staticDataFrame = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("/data/retail-data/by-day/*.csv")

  staticDataFrame.createorReplaceTempView()
  val staticSchema = staticDataFrame.schema

  import org.apache.spark.sql.functions.{window, column, desc, col}

  staticDataFrame.selectExpr("CustomerId", "(UnitPrice * Quantity) as total_cost", "InvoiceDate")
    .groupBy(col("CustomerId"), window(col("InvoiceDate"), "1 day"))
    .sum("total_cost")
    .show(5)


  val streamingDataFrame = spark.readStream.schema(staticSchema)
    .option("maxFilesPerTrigger", 1)
    .format("csv")
    .option("header", "true")
    .load("/data/retail-data/by-day/*.csv")


  val purchaseByCustomerPerHour = streamingDataFrame
    .selectExpr(
      "CustomerId",
      "(UnitPrice * Quantity) as total_cost",
      "InvoiceDate"
    ).groupBy($"CustomerId", window($"InvoiceDate", "1 day"))
    .sum

  purchaseByCustomerPerHour.writeStream.format("memory")
    .queryName("customer_purchases")
    .outputMode("complete")
    .start()


  purchaseByCustomerPerHour.writeStream.format("console")
    .queryName("customer_purchases_2")
    .outputMode("complete")
    .start()
}
