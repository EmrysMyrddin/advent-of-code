package dev.cocaud.aoc.day8
import java.lang.Math.{sqrt, powExact => pow}
import scala.annotation.tailrec

class Day8 extends App {
  val inputFileName = os.pwd / "../inputs/day8"

  val points = os.read
    .lines(inputFileName)
    .map[Point](_.split(",").map(_.toLong))

  val connections = points
    .combinations(2)
    .map { case Seq(a, b) => (a, b) }
    .toSeq
    .sortBy { case (a, b) => a.distance(b) }

  val circuits = connections
    .take(1_000)
    .foldLeft(Seq[Set[Point]]()) { (circuits, connection) =>
      connection match {
        case (a, b) => {
          val (matching, others) =
            circuits.partition(c => c.contains(a) || c.contains(b))
          (Set(a, b) +: matching).reduce(_.union(_)) +: others
        }
      }
    }
    .sortBy(-_.size)

  val answer1 = circuits.take(3).map(_.size).product

  @tailrec
  final def findLastConnection(connections: Iterator[(Point, Point)], acc: Seq[Set[Point]] = Seq()): (Point, Point) = {
    val connection = connections.next()
    val newAcc = connection match {
      case (a, b) => {
        val (matching, others) = acc.partition(c => c.contains(a) || c.contains(b))
        (Set(a, b) +: matching).reduce(_.union(_)) +: others
      }
    }
    if (acc.length > 0 && newAcc.length == 1) connection 
    else findLastConnection(connections, newAcc)
  }

  val (a, b) = findLastConnection(connections.iterator)
  val answer2 = a.x * b.x

  println(s"Answer:\n\tPart 1: $answer1\n\tPart 2: $answer2")
}

case class Point(x: Long, y: Long, z: Long) {
  def distance(b: Point) =
    if (b == this) Double.MaxValue
    else sqrt(pow(x - b.x, 2) + pow(y - b.y, 2) + pow(z - b.z, 2))
}

object Point {
  implicit def fromArray(coord: Array[Long]): Point = coord match {
    case Array(x, y, z) => Point(x, y, z)
  }
}
