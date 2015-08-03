package ro.casaleon.www

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import dom.document
import dom.raw.Node

import I18n.Lang;

@JSExport
object App extends JSApp {

  val BrowserLang = findBrowserLang;

  private var model = recreateModel;

  def recreateModel : Model = {
    val hash = getHash();
    if (hash == null || hash.length == 0)
      Model( Page.Home, BrowserLang )
    else
      Model.fromUrlEncodedString( hash )
  }

  def getHash() : String = {
    def nullifyEmpty( str : String ) : String = if (str == null || str.length == 0) null else str

    val raw = nullifyEmpty( HashManager.getHash() );
    if (raw == null) {
      null 
    } else {
      val rest = if (raw(0) == '#') raw.substring(1) else raw;
      nullifyEmpty( rest )
    }
  }

  def setHash( hash : String ) : Unit = HashManager.setHash( hash )

  private def findBrowserLang : Lang = {
    var check = LanguageFinder.findLanguage()
    if ( check == null || check.length < 2 ) {
      "en"
    } else {
      check.substring(0,2)
    }
    I18n.Lang( check ).getOrElse( Lang.EN )
  }

  def rerender = {
    val body = dom.document.body;
    DomUtils.clearChildren( body );
    body.appendChild( Html.mainFrame( model ).render )
  }

  def updateModel( model : Model ) : Unit = {
    this.model = model;
    setHash( model.asUrlEncodedString );
    rerender;
  }

  @JSExport
  def main() : Unit = {
    rerender
  }


  @JSExport
  def restart() : Unit = {
    model = recreateModel;
    rerender;
  }

  def updateLang( newLang : Lang ) : Unit = updateModel( model.copy( lang = newLang ) )

  @JSExport
  def updateLangCode( code : String ) : Unit = Lang( code ).foreach( updateLang )

  def updatePage( newPage : Page ) : Unit = updateModel( model.copy( page = newPage ) )

  @JSExport
  def updatePageName( name : String ) : Unit = Page( name ).foreach( updatePage )

  // note that we had to define a findLanguage() function in Javascript,
  // in the global scope of our host HTML document
  private object LanguageFinder extends js.GlobalScope {
    def findLanguage() : String = js.native
  }
  def parseUrlEncoded( str : String ) : scala.collection.Map[String,Array[String]] = UrlEncodedParser.parseUrlEncoded( str ).mapValues( _.toArray );
  
  private object UrlEncodedParser extends js.GlobalScope {
    def parseUrlEncoded( str : String ) : js.Dictionary[ js.Array[String] ] = js.native;
  }

  private object HashManager extends js.GlobalScope {
    def getHash() : String = js.native;
    def setHash( hash : String, updateState : Boolean = true ) : Unit = js.native;
  }
}
