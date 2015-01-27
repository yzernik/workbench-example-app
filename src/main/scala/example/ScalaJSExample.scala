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

class Wave(var pos: Point, var time: Int) {
  def wavefront(speed: Int): Seq[Point] = {
    val edge = 5
    val dist = speed * time

    def inWavefront(p: Point): Boolean = {
      val d = p.length
      d < dist && d > (dist - edge)
    }

    val front = for {
      x <- -dist until dist
      y <- -dist until dist
      p = Point(x, y)
      if inWavefront(p)
    } yield p + pos

    front
  }
}

@JSExport
object ScalaJSExample {

  var startTime = js.Date.now()

  val canvas = dom.document
    .getElementById("canvas")
    .asInstanceOf[dom.HTMLCanvasElement]
  val ctx = canvas.getContext("2d")
    .asInstanceOf[dom.CanvasRenderingContext2D]

  var waves = Seq.empty[Wave]
  val speed = 1

  def run() = {

    canvas.height = dom.innerHeight
    canvas.width = dom.innerWidth

    // doing

    waves = waves.filter(w =>
      {
        val dist = w.time * speed
        dist * 8 < canvas.width || dist * 5 < canvas.height
      })

    for (wave <- waves) {
      wave.time = wave.time + 1
    }

  }

  def draw() = {
    // drawing
    ctx.fillStyle = "black"

    ctx.fillRect(0, 0, canvas.width, canvas.height)

    val frontPoints = for {
      wave <- waves
      p <- wave.wavefront(speed)
    } yield p

    ctx.fillStyle = "blue"
    for (p <- frontPoints) {
      ctx.fillRect(p.x - 10, p.y - 10, 1, 1)
    }

  }
  @JSExport
  def main(): Unit = {
    dom.console.log("main")

    dom.document.onclick = { (e: dom.MouseEvent) =>
      waves ++= Seq(new Wave(
        Point(e.clientX.toInt, e.clientY.toInt), 0))
      (): js.Any
    }
    dom.setInterval(() => { run(); draw() }, 20)
  }
}