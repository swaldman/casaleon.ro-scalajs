package ro.casaleon.www;

import I18n.Lang;

object Model {
  def fromUrlEncodedString( urlEncodedString : String ) : Model = {
    val map = App.parseUrlEncoded( urlEncodedString );
    val page : Page = {
      val pageName = map.get( "page" ).getOrElse( Array("Home") )(0);
      Page( pageName ).getOrElse( Page.Home )
    }
    val lang : Lang = {
      val langCode = map.get( "lang" ).getOrElse( Array("en") )(0);
      Lang( langCode.toLowerCase ).getOrElse( App.BrowserLang )
    }
    Model( page, lang )
  }
}
case class Model( page : Page, lang : Lang ) {
  def asUrlEncodedString = s"page=${page.name}&lang=${lang.code}"
}


