package example

import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object ScalaJSExample extends JSApp with Bubbles {

  val canvas = dom.document.getElementById("canvas main").asInstanceOf[dom.html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  @JSExport
  def main() {
    dom.console.log("Main started with:", canvas.id)

    def run() = {
      canvas.height = dom.window.innerHeight.toInt
      canvas.width  = dom.window.innerWidth.toInt

      ctx.fillRect(0, 0, canvas.width, canvas.height)

      bubbles(canvas, ctx, "")
    }

    // Initialize the canvas and refresh continuously.
    run()
    dom.window.setInterval(() => run(), 40)
  }

}
