class Day7 extends App {
  val inputFileName = os.pwd / "../inputs/day7"

  val lines = os.read
    .lines(inputFileName)
    .map(_.toArray.map[Cell] {
      case '.' => Empty
      case 'S' => Beam(1)
      case '^' => Spliter(0)
    })

  for (y <- 1 until lines.length) {
    for (x <- 0 until lines(y).length) {
      lines(y - 1)(x) match {
        case (Beam(prev)) =>
          lines(y)(x) match {
            case Empty           => lines(y)(x) = Beam(prev);
            case Beam(curr)      => lines(y)(x) = Beam(curr + prev);
            case Spliter(splits) => {
              lines(y)(x) = Spliter(splits + 1)
              Seq(-1, 1).map(i =>
                lines(y)(x + i) = lines(y)(x + i) match {
                  case Empty      => Beam(prev)
                  case Beam(curr) => Beam(curr + prev)
                  case curr       => curr
                }
              );
            }
          }
        case _ => ()
      }
    }
  }

  val answer1 = lines.flatten.collect { case Spliter(n) => n }.sum
  val answer2 = lines.last.collect { case Beam(n) => n }.sum

  println(s"Answer:\n\tPart 1: $answer1\n\tPart 2: $answer2")
}

sealed trait Cell
case object Empty extends Cell {
  override def toString = "."
}
case class Beam(n: Long) extends Cell {
  override def toString = n.toString()
}
case class Spliter(n: Int) extends Cell {
  override def toString = "^"
}
