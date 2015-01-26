package example

import org.scalajs.dom
import scala.util.Random
import scala.scalajs.js
import js.annotation.JSExport
case class Point(x: Double, y: Double) {
  def +(p: Point) = Point(x + p.x, y + p.y)
  def -(p: Point) = Point(x - p.x, y - p.y)
  def *(d: Double) = Point(x * d, y * d)
  def /(d: Double) = Point(x / d, y / d)
  def length = Math.sqrt(x * x + y * y)
}

class Wave(var pos: Point, var time: Int)
@JSExport
object ScalaJSExample {

  var startTime = js.Date.now()

  val canvas = dom.document
    .getElementById("canvas")
    .asInstanceOf[dom.HTMLCanvasElement]
  val ctx = canvas.getContext("2d")
    .asInstanceOf[dom.CanvasRenderingContext2D]

  var waves = Seq.empty[Wave]
  val vel = 1

  def run() = {

    canvas.height = dom.innerHeight
    canvas.width = dom.innerWidth

    // doing

    waves = waves.filter(w =>
      {
        val dist = w.time * vel
        dist < canvas.width || dist < canvas.height
      })

    for (wave <- waves) {
      wave.time = wave.time + 1
    }

  }

  def draw() = {
    // drawing
    ctx.fillStyle = "black"

    ctx.fillRect(0, 0, canvas.width, canvas.height)

    ctx.fillStyle = "red"
    for (wave <- waves) {
      val dist = vel * wave.time
      println(wave.time)
      ctx.fillRect(wave.pos.x - 10, wave.pos.y - 10, dist, dist)
    }

  }
  @JSExport
  def main(): Unit = {
    dom.console.log("main")

    dom.document.onclick = { (e: dom.MouseEvent) =>
      println("clicked...")
      waves ++= Seq(new Wave(
        Point(e.clientX.toInt, e.clientY.toInt), 0))
      (): js.Any
    }
    dom.setInterval(() => { run(); draw() }, 20)
  }
}