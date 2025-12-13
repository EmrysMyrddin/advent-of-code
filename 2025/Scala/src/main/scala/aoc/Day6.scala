package dev.cocaud.aoc.day6

class Day6 extends App {
  val inputFileName = os.pwd / "../inputs/day6"

  val lines = os.read.lines(inputFileName)
  val (rows, Seq(last)) = lines.splitAt(lines.length - 1)

  val operations = last
    .split(" +")
    .map[(Long, Long) => Long] {
      case "+" => _ + _;
      case "*" => _ * _
    }

  val answer1 = rows
    .map(_.split(" +").map(_.toLong))
    .reduce((acc, curr) => {
      acc.zip(curr).zipWithIndex.map { case ((a, b), i) =>
        operations(i)(a, b)
      }
    })
    .sum

  val answer2 = last
    .splitWithDelimiters(". +", 0)
    .filter(_.nonEmpty)
    .foldLeft(Seq[(Int, Int)]())((acc, operation) => {
      val start = acc.lastOption.getOrElse((0, 0))._2
      acc :+ (start, start + operation.length)
    })
    .map { case (start, end) =>
      rows
        .map(_.slice(start, end))
        .transpose
        .map(_.mkString.trim)
        .filter(_.nonEmpty)
        .map(_.toLong)
    }
    .zip(operations)
    .map { case (numbers, op) => numbers.reduce(op) }
    .sum

  println(f"Answer:\n\tPart 1: $answer1\n\tPart 2: ${answer2}")
}
