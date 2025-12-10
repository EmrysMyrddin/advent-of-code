import scala.annotation.tailrec
class Day3 extends App {

  def findJoltage(s: String, n: Int): Long = {
    @tailrec def aux(s: String, n: Int, acc: String): String = {
      if (n == 0) return acc
      val max = s.take(s.length - n + 1).max
      return aux(s.dropWhile(_ != max).tail, n - 1, acc + max)
    }
    aux(s, n, "").toLong
  }

  val inputFileName = os.pwd / "../inputs/day3"

  val (part1, part2) = os.read.lines
    .stream(inputFileName)
    .map(bank => (findJoltage(bank, 2), findJoltage(bank, 12)))
    .toSeq
    .unzip

  println(s"Answer:\n\tPart 1: ${part1.sum}\n\tPart 2: ${part2.sum}")
}
