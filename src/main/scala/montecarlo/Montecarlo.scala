package montecarlo

/**
  * Calculating pi using Monte Carlo Simulation
  * Assuming Center at (0,0) and point in 1st Quadrant as (1,1)
  */

import scala.util.Random

class Montecarlo {
  def calc(): Unit = {
    val n = 100000000
    var m = 0

    val rand = new Random()
    for (i <- 0 to n if({
      val x = rand.nextFloat()
      val y = rand.nextFloat()
      ((x*x) + (y*y)) <= 1
    })) m += 1

    val rho = m.toFloat/n.toFloat
    val pi = rho * 4

    println("Pi = " +pi)
  }
}

object Run extends Montecarlo {
  def main(args:Array[String]): Unit = {
    calc()
  }
}
