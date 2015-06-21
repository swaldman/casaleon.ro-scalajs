package ro.casaleon.www

import org.scalajs.dom
import dom.raw.Node

object DomUtils {
  def clearChildren( node : Node ) : Unit = {
    val kids = node.childNodes
    (0 until kids.length).foreach( i => node.removeChild( kids(i) ) )
  }
}
