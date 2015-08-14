package example

import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport

case class Point(x: Double, y: Double) {
  def +(p: Point) = Point(x + p.x, y + p.y)
  def -(p: Point) = Point(x - p.x, y - p.y)
  def *(d: Double) = Point(x * d, y * d)
  def /(d: Double) = Point(x / d, y / d)
  def length = Math.sqrt(x * x + y * y)
}

case class Wave(pos: Point, var time: Int = 1) {
  def wavefront(speed: Int): Seq[Point] = {
    val (edge, dist) = (5, speed * time)

    def inWavefront(p: Point): Boolean = {
      val d = p.length
      d < dist && d > (dist - edge)
    }

    for {
      x <- -dist until dist
      y <- -dist until dist
      p = Point(x, y)
      if inWavefront(p)
    } yield p + pos
  }
}

@JSExport
object ScalaJSExample {

  val canvas = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
  val (ctx ,speed)= (canvas.getContext("2d"), 1)
  var waves = mutable.Buffer[Wave]()

  @JSExport
  def doDynContent(): Unit = {
    dom.console.log("Main started")

    dom.document.onclick = { (e: dom.MouseEvent) =>
      waves += Wave(Point(e.clientX.toInt, e.clientY.toInt))
    }
    dom.setInterval(() => {
      run()
      draw()
    }, 30)
  }

  def run() {
    canvas.height = dom.innerHeight; canvas.width = dom.innerWidth

    // doing
    waves = waves.filter(w => {
      val dist = w.time * speed
      w.time += 1
      dist * 8 < canvas.width || dist * 5 < canvas.height
    })
  }

  def draw() = { // drawing
    ctx.fillStyle = "black"
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    def frontPoints = for {
      wave <- waves
       p <- wave.wavefront(speed)
    } yield p

    ctx.fillStyle = "magenta"
    for (p <- frontPoints) ctx.fillRect(p.x - 10, p.y - 10, 1, 1)
  }
}