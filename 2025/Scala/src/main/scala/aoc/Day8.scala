package dev.cocaud.aoc.day8
import java.lang.Math.{sqrt, powExact => pow}

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
    .take(1_000)

  val circuits = connections
    .foldLeft(Seq[Set[Point]]()) { (circuits, connection) =>
      connection match {
        case (a, b) => {
          val (matching, others) = circuits.partition(c => c.contains(a) || c.contains(b))
          (Set(a, b) +: matching).reduce(_.union(_)) +: others
        }
      }
    }
    .sortBy(-_.size)

  val answer1 = circuits.take(3).map(_.size).product

  println(s"Answer:\n\tPart 1: $answer1")
}

case class Point(x: Long, y: Long, z: Long) extends Ordered[Point] {

  override def compare(that: Point): Int = {
    val compX = x.compare(that.x)
    if (x != 0) compX
    else {
      val compY = y.compare(that.y)
      if (compY != 0) compY
      else z.compare(that.z)
    }
  }

  def distance(b: Point) =
    if (b == this) Double.MaxValue
    else sqrt(pow(x - b.x, 2) + pow(y - b.y, 2) + pow(z - b.z, 2))

  override def toString() =
    f"$x%3d"

  def toCompleteString() =
    s"${x.toString.padTo(3, ' ')} ${y.toString.padTo(3, ' ')} ${z.toString.padTo(3, ' ')}"
}

object Point {
  implicit def fromArray(coord: Array[Long]): Point = coord match {
    case Array(x, y, z) => Point(x, y, z)
  }
}
