package ro.casaleon.example;

import ro.casaleon.www.Html;

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import dom.document

@JSExport
object Hello extends JSApp {
  @JSExport
  def main() : Unit = {
    //println("Hello world!")
    //println( Html.testPage );
    dom.document.body.appendChild( Html.testBody.render )
  }
}



