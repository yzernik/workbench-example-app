package example

import org.scalajs.dom._
import org.scalajs.dom.html.Canvas

import scala.collection.mutable.ListBuffer
import scala.scalajs.js.annotation.JSExport

class Point(val x: Double, val y: Double) {
  def +(p: Point) = new Point(x + p.x, y + p.y)
}

class Wave(val pos: Point, var time: Int = 1)

@JSExport
object ScalaJSExample {

  val canvas = document.getElementById("canvas").asInstanceOf[Canvas]
  val (ctx, speed) = (canvas.getContext("2d"), 1)
  var waves = ListBuffer.empty[Wave]

  @JSExport
  def doDynContent() {
    console.log("doDynContent called")

    document.onclick = { (e: MouseEvent) =>
      waves :+= new Wave(new Point(e.clientX.toInt, e.clientY.toInt))
    }
    window.setInterval(() => {
      run()
      draw()
    }, 50)
  }

  def run() {
    canvas.height = window.innerHeight.toInt
    canvas.width = window.innerWidth.toInt

    // doing
    waves = waves.filter(w => {
      val dist = w.time * speed
      w.time += 1
      dist * 8 < canvas.width || dist * 5 < canvas.height
    })
  }

  def draw() {
    // drawing
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    ctx.strokeStyle = "magenta"
    ctx.lineWidth = 5
    waves.foreach { w =>
      ctx.beginPath()
      ctx.arc(w.pos.x, w.pos.y, speed * w.time, 0, 2 * math.Pi)

      ctx.stroke()
    }
  }
}
