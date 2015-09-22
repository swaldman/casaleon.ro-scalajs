package ro.casaleon.www

import scala.scalajs.js;
import org.scalajs.dom;

package object gallery {
  case class Dimension( width : Int, height : Int, aspectRatio : Double )


  // this is really yucky, but the only visible motion in the sequence of 
  // touchstart, touchmove, and touchend my iphone is generating is between
  // a constant value (for clientX, clientY, pageX, pageY, etc.) in all the touches
  // and a non-standard pageX and pageY at the top-level of Safari's touchend event.
  //
  // in theory, I should be able to follow the (standard) touchmove events, but
  // unfortunately, only getting these nonstandard top-level pageX and pageY
  // values detects any kind of motion. :(
  //
  // this seems like too huge a bug to exist. but for now, i have to assume
  // that it does, or I am misunderstanding something very basic. probably
  // the latter.
  //
  // but the stuff below works, as a workaround. it's used in Html.scala in
  // this package.

  def forceExtractTopLevelPageXY( e : dom.TouchEvent ) : (Int, Int ) = {
    import IphonePageXYWorkaroundManager._

    ( topLevelPageX( e ), topLevelPageY( e ) )
  }

  private object IphonePageXYWorkaroundManager extends js.GlobalScope {
    def topLevelPageX( e : dom.TouchEvent ) : Int = js.native;
    def topLevelPageY( e : dom.TouchEvent ) : Int = js.native;
  }
}
