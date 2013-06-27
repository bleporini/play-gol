import domain._

/**
 * This is the oscillator case:
 *
 *   |o|o|o|      |o|x|o|
 *   |x|x|x|  ==> |o|x|o|
 *   |o|o|o|      |o|x|o|
 */
trait Scenario1{
  val gameArea= Cell((1, 1), Dead)::
    Cell((1,2),Alive)::
    Cell((1,3),Dead)::
    Cell((2,1),Dead)::
    Cell((2,2),Alive)::
    Cell((2,3),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Alive)::
    Cell((3,3),Dead)::Nil

  val shouldBeNext= Cell((1, 1), Dead)::
    Cell((1,2),Dead)::
    Cell((1,3),Dead)::
    Cell((2,1),Alive)::
    Cell((2,2),Alive)::
    Cell((2,3),Alive)::
    Cell((3,1),Dead)::
    Cell((3,2),Dead)::
    Cell((3,3),Dead)::Nil
}

/**
 *
 *   |x|o|o|      |o|x|o|
 *   |x|o|x|  ==> |o|x|o|
 *   |o|o|o|      |o|o|o|
 */

trait Scenario2{
  val gameArea=
    Cell((1,1),Alive)::
    Cell((1,2),Alive)::
    Cell((1,3),Dead)::
    Cell((2,1),Dead)::
    Cell((2,2),Dead)::
    Cell((2,3),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Alive)::
    Cell((3,3),Dead)::Nil

  val shouldBeNext=
    Cell((1,1),Dead)::
    Cell((1,2),Dead)::
    Cell((1,3),Dead)::
    Cell((2,1),Alive)::
    Cell((2,2),Alive)::
    Cell((2,3),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Dead)::
    Cell((3,3),Dead)::Nil
}

/**
 *
 *   |x|x|o|      |x|x|o|
 *   |x|x|o|  ==> |x|x|o|
 *   |o|o|o|      |o|o|o|
 */

trait BlockScenario{
  val gameArea=
    Cell((1,1),Alive)::
    Cell((1,2),Alive)::
    Cell((1,3),Dead)::
    Cell((2,1),Alive)::
    Cell((2,2),Alive)::
    Cell((2,3),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Dead)::
    Cell((3,3),Dead)::Nil

  val shouldBeNext=
    Cell((1,1),Alive)::
    Cell((1,2),Alive)::
    Cell((1,3),Dead)::
    Cell((2,1),Alive)::
    Cell((2,2),Alive)::
    Cell((2,3),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Dead)::
    Cell((3,3),Dead)::Nil
}

/**
 *
 *   |o|o|x|o|      |o|o|o|o|
 *   |x|o|o|x|  ==> |o|x|x|x|
 *   |x|o|o|x|      |x|x|x|o|
 *   |o|x|o|o|      |o|o|o|o|
 */

trait FrogScenario{
  val gameArea=
    Cell((1,1),Dead)::
    Cell((1,2),Alive)::
    Cell((1,3),Alive)::
    Cell((1,4),Dead)::
    Cell((2,1),Dead)::
    Cell((2,2),Dead)::
    Cell((2,3),Dead)::
    Cell((2,4),Alive)::
    Cell((3,1),Alive)::
    Cell((3,2),Dead)::
    Cell((3,3),Dead)::
    Cell((3,4),Dead)::
    Cell((4,1),Dead)::
    Cell((4,2),Alive)::
    Cell((4,3),Alive)::
    Cell((4,4),Dead)::Nil


  val shouldBeNext=
    Cell((1,1),Dead)::
    Cell((1,2),Dead)::
    Cell((1,3),Alive)::
    Cell((1,4),Dead)::
    Cell((2,1),Dead)::
    Cell((2,2),Alive)::
    Cell((2,3),Alive)::
    Cell((2,4),Dead)::
    Cell((3,1),Dead)::
    Cell((3,2),Alive)::
    Cell((3,3),Alive)::
    Cell((3,4),Dead)::
    Cell((4,1),Dead)::
    Cell((4,2),Alive)::
    Cell((4,3),Dead)::
    Cell((4,4),Dead)::Nil

}