package ro.casaleon.www

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import dom.document
import dom.raw.Node

import I18n.Lang;

@JSExport
object App extends JSApp {

  private var model = new Model( Page.Home, Lang.EN );

  private def clearChildren( node : Node ) : Unit = {
    val kids = node.childNodes
    (0 until kids.length).foreach( i => node.removeChild( kids(i) ) )
  }
  def rerender = {
    val body = dom.document.body;
    clearChildren( body );
    body.appendChild( Html.mainFrame( model ).render )
  }

  def updateModel( model : Model ) : Unit = {
    this.model = model;
    rerender
  }

  @JSExport
  def main() : Unit = {
    rerender
  }

  def updateLang( newLang : Lang ) : Unit = updateModel( model.copy( lang = newLang ) )

  @JSExport
  def updateLangCode( code : String ) : Unit = Lang( code ).foreach( updateLang )

  def updatePage( newPage : Page ) : Unit = updateModel( model.copy( page = newPage ) )

  @JSExport
  def updatePageName( name : String ) : Unit = Page( name ).foreach( updatePage )

}
