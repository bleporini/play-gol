import domain.{Alive, Dead, Cell}
import org.scalatest.FunSuite
import domain._

/**
 *
 * User: blep
 * Date: 27/06/13
 * Time: 08:57
 */

class GamePatternSuite extends FunSuite with ScenarioUtil{


  test("Scenario 1"){
    val shoulBe=(
      Cell((1,1), Dead)::
      Cell((1,2),Alive)::
      Cell((1,3),Dead)::
      Cell((2,1),Dead)::
      Cell((2,2),Alive)::
      Cell((2,3),Dead)::
      Cell((3,1),Dead)::
      Cell((3,2),Alive)::
      Cell((3,3),Dead)::Nil).sortWith(sorter)
    val pattern =
      "ooo" +
      "xxx" +
      "ooo"

    val gameArea = fromPattern(3, 3, pattern).sortWith(sorter)
    println("gameArea = " + gameArea)
    println("shoulBe = " + shoulBe)
    assert(shoulBe===gameArea)
  }

}
