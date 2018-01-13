//#full-example
package montecarlo

/**
  * Author: LMK
  * Monte Carlo Simulation for Calculating Pi, using the Actor Framework
  */

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import org.ddahl.rscala.RClient

import scala.collection.mutable.ArrayBuffer
import breeze.linalg._

//#pimontecarlosimulator-companion
object PiMonteCarlo {
  //#pimontecarlosimulator-messages
  def calc(message: String, printerActor: ActorRef): Props = Props(new PiMonteCarlo(message, printerActor))
  //#greeter-messages
  final case class calculatePi()
  case object SendPi
}

//#pimontecarlosimulator-actor
class PiMonteCarlo(message: String, printerActor: ActorRef) extends Actor {
  import PiMonteCarlo._
  import Printer._

  var currentPi: Float = 0

  def receive = {
    case calculatePi() =>
      currentPi = calc()
    case SendPi           =>
      //#pimontecarlosimulator-send-message
      printerActor ! PrintingPi(currentPi)
  }

  def calc(): Float = {
    val pInSq = 1000
    val R = RClient()
    val points = new ArrayBuffer[Vector[Double]]
    for (a <- 1 to pInSq) {
      points += Vector[Double](R.evalD1("m <- runif(2)"))
    }
    val pInCircle = points.map(x => pointWithinCircle(x)).filter(x => x == true).length
    val rho = pInCircle.toFloat / pInSq.toFloat
    val piValue = rho * 4
    return piValue
  }

  //method to check if point is within circle
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

//#printer-companion
object Printer {
  //#printer-messages
  def props: Props = Props[Printer]
  //#printer-messages
  final case class PrintingPi(pi: Float)
}

//#printer-actor
class Printer extends Actor with ActorLogging {
  import Printer._
  def receive = {
    case PrintingPi(pi) => {
      log.info(s"Pi Value recieved (from ${sender()}): $pi");
    }
  }
}

//#main-class
object Montecarlo extends App {
  import PiMonteCarlo._

  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("helloAkka")

  //#create-actors
  // Create the printer actor
  val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

  // Create the 'pimontecarlosimulator' actors
  /*val Actor1: ActorRef =
    system.actorOf(PiMonteCarlo.calc("Actor1", printer), "Actor1")
  val Actor2: ActorRef =
    system.actorOf(PiMonteCarlo.calc("Actor2", printer), "Actor2")
  val Actor3: ActorRef =
    system.actorOf(PiMonteCarlo.calc("Actor3", printer), "Actor3")*/

  val actors = new ArrayBuffer[ActorRef]
  for(i <- 1 to 100) {
    actors += system.actorOf(PiMonteCarlo.calc(s"Actor$i", printer), s"Actor$i")
  }

  val piAgregate = actors.map(x => calcAgregatePi(x))

  def calcAgregatePi(actor: ActorRef): Unit = {
    val agregatePi: Float = 0
    actor ! calculatePi()
    actor ! SendPi

  }

/*  //#main-send-messages
  Actor1 ! calculatePi()
  Actor1 ! SendPi

  Actor1 ! calculatePi()
  Actor1 ! SendPi

  Actor2 ! calculatePi()
  Actor2 ! SendPi

  Actor3 ! calculatePi()
  Actor3 ! SendPi*/

}
