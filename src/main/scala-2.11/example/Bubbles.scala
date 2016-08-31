package example

import org.scalajs.dom

import scala.Numeric.Implicits._

trait Bubbles{
  private lazy val twoPi = 2.0 * math.Pi
  private var waves = Vector.empty[Wave[Double]]

  private def speed = 2

  class Point[P: Numeric](val x: P, val y: P) {
    def +(p: Point[P]) = new Point(x + p.x, y + p.y)
  }

  case class Wave[P](loc: Point[P], time: Int = 1)

  protected def bubbles(canvas: dom.html.Canvas, renderer: dom.CanvasRenderingContext2D, dummy: => String) = {

    def draw[P](w: Wave[P]) = {
      renderer.beginPath()
      renderer.arc(w.loc.x.asInstanceOf[Double], w.loc.y.asInstanceOf[Double], speed * w.time, 0, twoPi)
      renderer.stroke()
      w.copy(time = w.time + 1)
    }

    renderer.strokeStyle = "magenta"
    renderer.lineWidth = 5

    waves = waves.map(draw).takeWhile { w => w.time * 8 * speed < canvas.width || w.time * 5 * speed < canvas.height }

    lazy val runOnce = canvas.onmousedown = { (e: dom.MouseEvent) => waves +:= Wave(new Point(e.clientX, e.clientY)) }
    runOnce
  }

}
