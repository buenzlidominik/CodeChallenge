import com.workday.insights.timeseries.arima.struct.{ArimaParams, ForecastResult}
import com.workday.insights.timeseries.arima.Arima

import java.text.SimpleDateFormat
import org.joda.time.DateTime
import scala.io.Source

object Main {

  def main(args: Array[String]) = {

    var timestamps = Array.empty[Double]
    val filename = "C:\\temp\\newsTimes.txt"
    val formatter = new SimpleDateFormat("HH'h' mm'm' ss's'")

    //Read and clean the data in the file, convert to timestamp
    for (line <- Source.fromFile(filename).getLines) {
      val time = formatter.parse(line)
      timestamps = timestamps :+ time.getTime.toDouble
    }

    // Set ARIMA model parameters
    // Non-Seasonal parameters
    val p = 1
    val d = 0
    val q = 0

    // Seasonal parameters
    val P = 0
    val D = 0
    val Q = 0
    val m = 0
    val forecastSize = 1

    val params: ArimaParams = new ArimaParams(p, d, q, P, D, Q, m);

    val forecastResult: ForecastResult = Arima.forecast_arima(timestamps, forecastSize, params)

    forecastResult.getForecast.foreach(forecast => {
      val dateTime = new DateTime(forecast.toLong)
      println(dateTime.toString("HH:mm:ss"))
    });
  }
}
