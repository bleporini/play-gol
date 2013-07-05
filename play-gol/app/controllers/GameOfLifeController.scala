package controllers

import play.api.mvc.{Action, Controller}
import domain._
import domain.Cell

/**
 *
 * User: blep
 * Date: 01/07/13
 * Time: 09:03
 */

object GameOfLifeController extends Controller{

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

  import play.api.libs.json._


  implicit val cellMapper= new Reads[Cell] {
    def reads(js: JsValue) = JsSuccess(
      Cell(((js \ "x").as[Int], (js \ "y").as[Int]),
        if ((js \ "status").as[String] == Alive.toString) Alive else Dead)
    )
  }

  implicit val rds = __.read[List[Cell]]

  val js = Json.arr(Json.obj("x" -> 1, "y" -> 2, "status" -> "alive"),
    Json.obj("x" -> 1, "y" -> 2, "status" -> "dead"))

  implicit val cellWriter = new Writes[GameArea] {
    def writes(area: GameArea) = JsArray(area.map(o=>Json.obj("x" -> o.coordinates._1,
      "y" -> o.coordinates._2, "status" -> o.status.toString)))
  }

  def computeNextAction=Action(parse.json){ request =>
    request.body.validate[GameArea].map {
      case area => Ok(Json.toJson(computeNext(area)))
    }.recoverTotal(e => BadRequest)

  }

}
