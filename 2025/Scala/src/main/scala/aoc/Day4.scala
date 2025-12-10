class Day4 extends App {
  val inputFileName = os.pwd / "../inputs/day4"

  val lines = os.read.lines.stream(inputFileName).map(line => s".$line.")

  // Add empty borders to simplify calculations
  val width = lines.head.length
  val emptyLine = "." * width
  val rows = emptyLine +: lines.toSeq :+ emptyLine

  val data = rows.flatten.map {
    case '@' => 1
    case '.' => 0
  }.toArray

  def count3(i: Int) = data(i - 1) + data(i) + data(i + 1)

  def removeAcessibleRolls(): Int = data.iterator.zipWithIndex
    .collect { case (1, i) => i }
    .filter(i => count3(i - width) + count3(i) - 1 + count3(i + width) < 4)
    .toSeq
    .tapEach(data(_) = 0)
    .length

  var removed = removeAcessibleRolls()
  val answer1 = removed

  var answer2 = answer1
  while (removed != 0) {
    removed = removeAcessibleRolls()
    answer2 += removed
  }

  println(s"Answer:\n\tPart 1: ${answer1}\n\tPart 2: $answer2")
}
