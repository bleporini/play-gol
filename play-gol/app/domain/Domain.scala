package object domain{
  type Coordinates=(Int,Int)
  type GameArea=List[Cell]

  case class Cell(coordinates:Coordinates,status:CellStatus){

  }


  abstract class CellStatus

  object Alive extends CellStatus{
    override def toString= "alive"
  }
  object Dead extends CellStatus{
    override def toString= "dead"
  }

  val sorter = (c1: Cell, c2: Cell) =>
    c1.coordinates._1 < c2.coordinates._1 || c1.coordinates._2 < c2.coordinates._2

}



