package dev.cocaud.aoc.day1

class Day1 extends App {
  val inputFileName: os.Path = os.pwd / "../inputs/day1"

  var currPos = 50
  var answerPart1 = 0
  var answerPart2 = 0

  for (
    movement <- os.read.lines.stream(inputFileName) collect {
      case s"L$steps" => -steps.toInt
      case s"R$steps" => steps.toInt
    }
  ) {
    val prevPos = currPos
    currPos += movement

    if (
      prevPos != 0 && (currPos == 0 || currPos / currPos.abs != prevPos / prevPos.abs)
    ) {
      answerPart2 += 1
    }

    answerPart2 += (currPos / 100).abs;

    currPos %= 100;

    if (currPos == 0) {
      answerPart1 += 1
    }
  }

  println(s"Answers:\n\tPart 1: $answerPart1\n\tPart 2: $answerPart2")
}
