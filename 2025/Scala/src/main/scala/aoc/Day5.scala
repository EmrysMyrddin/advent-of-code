package dev.cocaud.aoc.day5

class Day5 extends App {
  val inputFileName = os.pwd / "../inputs/day5"

  val lines = os.read.lines(inputFileName)

  val (db, _ +: ids) = lines.splitAt(lines.indexOf("").toInt)

  val ranges = db.map { case s"$start-$end" => Range(start.toLong, end.toLong) }

  var distinctRanges = List[Range]()
  for (
    range <- db.map { case s"$start-$end" =>
      Range(start.toLong, end.toLong)
    }.sorted
  ) {
    distinctRanges.find(_.intersects(range)) match {
      case Some(existing) => existing.incorporate(range)
      case None           => distinctRanges = range +: distinctRanges
    }
  }

  val answer1 = ids
    .map(_.toLong)
    .count(id => distinctRanges.exists(_.contains(id)))

  val answer2 = distinctRanges.map(_.length).sum

  println(s"Answer:\n\tPart 1: $answer1\n\tPart 2: $answer2")
}

case class Range(var start: Long, var end: Long) extends Ordered[Range] {

  override def compare(that: Range): Int = {
    val result = start.compare(that.start)
    if (result == 0) end.compare(that.end) else result
  }

  def contains(n: Long) = start <= n && n <= end

  def intersects(that: Range) = contains(that.start) || contains(that.end)

  def incorporate(that: Range) = {
    start = Math.min(start, that.start)
    end = Math.max(end, that.end)
  }

  def length = end - start + 1
}
