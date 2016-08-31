package example

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.Canvas

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object ScalaJSExample extends JSApp with Bubbles {

  val canvas = document.getElementById("canvas main").asInstanceOf[Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  @JSExport
  def main() {
    console.log("Main started with", canvas.id)

    def run() = {
      canvas.height = window.innerHeight.toInt
      canvas.width = window.innerWidth.toInt

      ctx.fillRect(0, 0, canvas.width, canvas.height)

      bubbles(canvas, ctx, "")
    }

    // Initialize the canvas and refresh continuously.
    run()
    dom.window.setInterval(() => run(), 40)
  }

}
