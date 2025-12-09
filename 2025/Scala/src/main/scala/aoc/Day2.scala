import scala.util.Try
import scala.math.pow

class Day2 extends App {
  val inputFileName = os.pwd / "../inputs/day2"

  val (part1, part2) = os.read
    .lines(inputFileName)
    .head
    .split(",")
    .iterator
    .collect { case s"$start-$end" => (start, end) }
    .flatMap { case (start, end) => start.toLong to end.toLong }
    .duplicate

  val answerPart1 = part1.filter { id =>
    val idStr = id.toString
    val (a, b) = idStr.splitAt(idStr.length / 2)
    a == b
  }.sum

  val answerPart2 = part2.filter { id =>
    val idStr = id.toString
    (1 until idStr.length)
      .iterator
      .filter(idStr.length % _ == 0)
      .map(idStr.grouped(_).toSeq)
      .exists(parts => parts.forall(_ == parts.head))
  }.sum

  println(f"Answer: \n\tPart 1: $answerPart1\n\tPart 2: $answerPart2")
}
