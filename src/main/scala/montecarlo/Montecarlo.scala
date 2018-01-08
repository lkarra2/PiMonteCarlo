package montecarlo

/**
  * Calculating pi using Monte Carlo Simulation
  */

import org.ddahl.rscala.RClient
import scala.collection.mutable.ArrayBuffer
import breeze.linalg._


class Montecarlo {
  def calc(): Unit = {
    val pInSq = 10000

    val R = RClient()
    val points = new ArrayBuffer[Vector[Double]]

    for( a <- 1 to pInSq) {
      points += Vector[Double](R.evalD1("m <- runif(2)"))
    }

    val pInCircle = points.map(x=>pointWithinCircle(x)).filter(x => x == true).length
    println("Points within Circle = " + pInCircle)

    val rho = pInCircle.toFloat/pInSq.toFloat
    val pi = rho * 4

    println("Rho = "+rho)
    println("Pi = " +pi)
  }

  //Taken from GK
  def pointWithinCircle(p:Vector[Double]): Boolean = {
    val x0 = 0.5
    val y0 = 0.5
    val radius = 0.5
    val dist = scala.math.pow((x0 - p{0}), 2.0) + scala.math.pow((y0 - p{1}), 2)

    if (dist < scala.math.pow(radius, 2))
      return true
    return false
  }
}

object Run extends Montecarlo {
  def main(args:Array[String]): Unit = {
    calc()
  }
}
