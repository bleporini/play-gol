import domain._


trait ScenarioUtil{
  /**
   * Computes the List of Cell from an area size and a String representation.
   * @param pattern
   * @return
   */
  def fromPattern(width:Int, height:Int,pattern:String):GameArea={
    def status(c:Char) = if(c=='x')Alive else Dead

    def computePattern(p:(Int, Int,String),area:GameArea):GameArea=p match{
      case (x,y,s) if x==width && y==height=>
        Cell((x,y),status(s.head))::area
      case (x,y,s) if x%width == 0 =>
        computePattern((1,y+1,s.tail),Cell((x,y),status(s.head))::area)
      case (x,y,s) =>
        computePattern((x+1,y,s.tail),Cell((x,y),status(s.head))::area)
      case _ =>
        throw new Exception("Error with " + p)
    }

    computePattern((1,1,pattern),Nil)
  }

  /**
   * Compares two GameArea no matter the order in the list.
   * @param ref
   * @param other
   * @return
   */
  def compareAreas(ref:GameArea, other:GameArea)={
    def compareElements(r:GameArea,o:GameArea):Boolean=o match{
      case Nil => true
      case h::tail => r.contains(h) && compareElements(r,tail)
    }

    ref.length == other.length && compareElements(ref,other)
  }

  def testSteps(computeNext:GameArea=>GameArea,steps:Seq[GameArea]):Boolean=
    steps match{
      case step::Nil => true
      case step1::step2::tail => compareAreas(computeNext(step1),step2) &&
          testSteps(computeNext,step2::tail)
  }

}

trait WrongScenario extends ScenarioUtil{
  val gameArea= fromPattern(3,3,
    "ooo" +
    "xxo" +
    "ooo"
  )
  val shouldBeNext= fromPattern(3,3,
      "oxo" +
      "oxo" +
      "oxo"
  )

  val steps=gameArea::shouldBeNext::Nil
}
/**
 * This is the oscillator case:
 *
 *   |o|o|o|      |o|x|o|
 *   |x|x|x|  ==> |o|x|o|
 *   |o|o|o|      |o|x|o|
 */
trait Scenario1 extends ScenarioUtil{
  val gameArea= fromPattern(3,3,
    "ooo" +
    "xxx" +
    "ooo"
  )
  val shouldBeNext= fromPattern(3,3,
      "oxo" +
      "oxo" +
      "oxo"
  )

  val steps=gameArea::shouldBeNext::Nil
}

/**
 *
 *   |x|o|o|      |o|x|o|
 *   |x|o|x|  ==> |o|x|o|
 *   |o|o|o|      |o|o|o|
 */

trait Scenario2 extends ScenarioUtil{
  val gameArea=fromPattern(3,3,
      "xoo" +
      "xox" +
      "ooo"
  )

  val shouldBeNext=fromPattern(3,3,
      "oxo" +
      "oxo" +
      "ooo"
  )

  val steps=gameArea::shouldBeNext::Nil
}

/**
 *
 *   |x|x|o|      |x|x|o|
 *   |x|x|o|  ==> |x|x|o|
 *   |o|o|o|      |o|o|o|
 */

trait BlockScenario extends ScenarioUtil{
  val gameArea=fromPattern(3,3,
    "xxo" +
    "xxo" +
    "ooo"
  )
  val shouldBeNext= gameArea

  val steps=gameArea::shouldBeNext::Nil
}

/**
 *
 *   |o|o|x|o|      |o|o|o|o|
 *   |x|o|o|x|  ==> |o|x|x|x|
 *   |x|o|o|x|      |x|x|x|o|
 *   |o|x|o|o|      |o|o|o|o|
 */

trait FrogScenario extends ScenarioUtil{
  val gameArea=fromPattern(4,4,
    "ooxo" +
    "xoox" +
    "xoox" +
    "oxoo"
  )

  val shouldBeNext=fromPattern(4,4,
    "oooo" +
    "oxxx" +
    "xxxo" +
    "oooo"
  )

  val steps=gameArea::shouldBeNext::Nil
}

trait GliderScenario extends ScenarioUtil{
  val steps=fromPattern(5,5,
  "ooooo"+
  "ooooo"+
  "xxxoo"+
  "ooxoo"+
  "oxooo")::
  fromPattern(5,5,
    "ooooo"+
    "oxooo"+
    "oxxoo"+
    "xoxoo"+
    "ooooo"
  )::
  fromPattern(5,5,
    "ooooo"+
    "oxxoo"+
    "xoxoo"+
    "ooxoo"+
    "ooooo"
  )::
  fromPattern(5,5,
    "OOOOO"+
    "OxxOO"+
    "OOxxO"+
    "OxOOO"+
    "OOOOO"
  )::
  fromPattern(5,5,
    "OOOOO"+
    "OxxxO"+
    "OOOxO"+
    "OOxOO"+
    "OOOOO"
  )::
  Nil

}