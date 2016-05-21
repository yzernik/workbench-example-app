package example

import org.scalajs.dom._

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport

case class Point(x: Double, y: Double) {
  def +(p: Point) = Point(x + p.x, y + p.y)
}

case class Wave(pos: Point, var time: Int = 1)

@JSExport
object ScalaJSExample {

  val canvas = document.getElementById("canvas").asInstanceOf[html.Canvas]
  val (ctx, speed) = (canvas.getContext("2d"), 1)
  var waves = mutable.Buffer[Wave]()

  @JSExport
  def doDynContent() {
    console.log("doDynContent called")

    document.onclick = { (e: MouseEvent) =>
      waves.append(Wave(Point(e.clientX.toInt, e.clientY.toInt)))
    }
    window.setInterval(() => {
      run()
      draw()
    }, 50)
  }

  def run() {
    (canvas.height, canvas.width) = (window.innerHeight, window.innerWidth)

    // doing
    waves = waves.filter(w => {
      val dist = w.time * speed
      w.time += 1
      dist * 8 < canvas.width || dist * 5 < canvas.height
    })
  }

  def draw() {
    // drawing
    ctx.fillStyle = "black"
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    ctx.strokeStyle = "magenta"
    ctx.lineWidth = 5
    waves.foreach { w => {
      ctx.beginPath()
      ctx.arc(w.pos.x, w.pos.y, speed * w.time, 0, 2 * math.Pi)
    }
      ctx.stroke()
    }
  }
}