/**
 *
 * User: blep
 * Date: 24/06/13
 * Time: 08:47
 */
import org.scalatest.FunSuite
import domain._


class GoLSuite extends FunSuite{


  def computeNext(origin:GameArea):GameArea= {
    origin.foldLeft(List[Cell]()){ (acc,c)=>
      val neighbours = findNeighbours(origin, c)
      val alives = neighbours.foldLeft(0)((acc, elem) =>
        if (elem.status == Alive) acc+1
        else acc
      )
//      println("c = " + c)
//      println("alives = " + alives)
      if (alives == 3)
        Cell(c.coordinates,Alive)::acc
      else if (c.status == Alive && alives==2)
        c::acc
      else Cell(c.coordinates,Dead)::acc
    }
  }

  def findNeighbours(area:GameArea, cell:Cell)={
    val (x,y)=cell.coordinates
    area.filter(c =>  c.coordinates match{
        case (i,j) if x==i => (y-j).abs == 1
        case (i,j) if y==j => (x-i).abs == 1
        case (i,j) => (x-i).abs ==1 && (y-j).abs==1
      }
    )
  }

  test("find neighbours"){
    val a = Cell((1, 1), Dead)
    val b = Cell((1, 2), Dead)
    val c = Cell((1, 3), Dead)
    val d = Cell((2, 1), Alive)
    val e = Cell((2, 2), Alive)
    val f = Cell((2, 3), Alive)
    val g = Cell((3, 1), Dead)
    val h = Cell((3, 2), Dead)
    val i = Cell((3, 3), Dead)


    val gameArea= a:: b :: c :: d :: e :: f :: g :: h :: i :: Nil

    val neighbours = findNeighbours(gameArea, a)
    println("neighbours = " + neighbours)
    assert(neighbours.length === 3)
    assert(neighbours.contains(b))
    assert(neighbours.contains(d))
    assert(neighbours.contains(e))

    val neighbours1 = findNeighbours(gameArea, e)
    assert(neighbours1.length === 8)
    assert(neighbours1.contains(a))
    assert(neighbours1.contains(b))
    assert(neighbours1.contains(c))
    assert(neighbours1.contains(c))
    assert(neighbours1.contains(f))
    assert(neighbours1.contains(g))
    assert(neighbours1.contains(h))
    assert(neighbours1.contains(i))

    val neighbours2 = findNeighbours(gameArea, b)
    println("neighbours2 = " + neighbours2)
    assert(neighbours2.length === 5)
    assert(neighbours2.contains(a))
    assert(neighbours2.contains(c))
    assert(neighbours2.contains(d))
    assert(neighbours2.contains(e))
    assert(neighbours2.contains(f))



  }

  test("Simple case one"){
    new Scenario1{
      testSteps(computeNext,steps)
    }
  }
  test("Simple case 2"){
    new Scenario2{
      val next = computeNext(gameArea).sortWith(sorter)
      println("shouldBe = " + shouldBeNext)
      println("next     = " + next)

      assert(compareAreas(shouldBeNext,next))

    }
  }

  test("Block Scenario"){new BlockScenario {
    val next = computeNext(gameArea).sortWith(sorter)
    println("shouldBe = " + shouldBeNext)
    println("next     = " + next)

    assert(compareAreas(shouldBeNext,next))
  }}

  test("Frog Scenario"){new FrogScenario {
    val next = computeNext(gameArea).sortWith(sorter)
    println("shouldBe = " + shouldBeNext)
    println("next     = " + next)

    assert(compareAreas(shouldBeNext,next))

    val next2 = computeNext(next).sortWith(sorter)
    assert(compareAreas(gameArea,next2))

  }}

  test("Vaisseau"){new GliderScenario {
    assert(testSteps(computeNext,steps))
  }}

  test("Wrong scenario"){new WrongScenario {
    assert(!testSteps(computeNext,steps))
  }}



}
