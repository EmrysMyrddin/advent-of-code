package dev.cocaud.aoc.day7.iter

class Day7Iter extends App {
  val inputFileName = os.pwd / "../inputs/day7"

  val lines = os.read.lines.stream(inputFileName)
    .map(_.toArray.map[Cell] {
      case '.' => Empty
      case 'S' => Beam(1)
      case '^' => Spliter
    })

  val start = lines.head

  var splitersUsed = 0

  val finalState = lines.foldLeft(start) { (acc, line) =>
    line.zipWithIndex.map {
      case (Empty, i) => {
        val fromSpliters = Seq(i - 1, i + 1)
          .filter(i => (0 until line.length).contains(i) && line(i) == Spliter)
          .map(acc(_))
        val fromUpward = acc(i)
        val n = (fromUpward +: fromSpliters).collect { case Beam(n) => n }.sum
        if (n > 0) Beam(n) else Empty
      }
      case (other, i) => {
        if (other == Spliter && acc(i).isInstanceOf[Beam]) splitersUsed += 1
        other
      }
    }
  }

  val timeLines = finalState.collect { case Beam(n) => n }.sum

  println(s"Answer:\n\tPart 1: $splitersUsed\n\tPart 2: $timeLines")
}

sealed trait Cell
case object Empty extends Cell {
  override def toString = "."
}
case class Beam(n: Long) extends Cell {
  override def toString = n.toString()
}
case object Spliter extends Cell {
  override def toString = "^"
}
