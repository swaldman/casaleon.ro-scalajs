package ro.casaleon.www;

//import scalatags.Text.all._
import scalatags.JsDom.all._
import scalatags.JsDom.TypedTag;
import org.scalajs.dom;

object Html {
  def mainFrame( model : Model ) : TypedTag[dom.Element] = {
    div( cls := "main" )( 
      langSelector( model ),
      div( cls := "topsect" )(
        div( id := "cl_champagne" )(
          img( src := "images/CasaLeonChampagne_800.png" )
        ),
        div( id := "main_menu" )(
          Page.all.map { page =>
            val cssClass = if ( page == model.page ) "black menu_item" else "menu_item";
            a( cls := cssClass, onclick := s"ro.casaleon.www.App().updatePageName( '${page.name}' )" )( I18n.menuItemName( page, model.lang ) )
          }
        ),
        div( id := "content" ) (
          content( model )
        )
      )
    )
  }

  def content( model : Model ) : TypedTag[dom.Element] = {
    def home = {
      div( id := "homeContent" )(
        img( id := "circleWindow", src := "images/cl_front_exterior_extra_sm_circular_window.png" ),
        p( raw("&nbsp;"), a( href := "http://en.wikipedia.org/wiki/Constanța", target := "_blank" )(raw("Constan&#x163;a, Romania")), "." ),
        p( 
          raw("&nbsp;"),
          "Commercial space, office space, and ", 
          a( href := "https://www.airbnb.com/rooms/6231211", target := "_blank" )(
            "a fine vacation studio"
          ),
          "."
        ),
        p( 
          raw("&nbsp;"),
          "An address with distinction, and a ",
          a( href := "http://www.nytimes.com/2001/02/15/garden/romanian-past-interrupted.html?pagewanted=all&src=pm", target := "_blank" )(
            "history "
          ),
          "."
        ),
        p( raw("&nbsp;"), "Stunning views of the sea, from your desk or from your balcony." ),
        p( raw("&nbsp;"), "Entertain guests on the building's panaoramic roof terraces." ),
        p(
          raw("&nbsp;"),
          "In the ",
          a( href := "https://www.google.com/maps/d/edit?mid=z2cJSCR_sDxc.kELqRSVJCGqo&usp=sharing", target := "_blank" )(
            "heart of the city "
          ),
          raw("next to Constan&#x163;a's "),
          a( href := "http://www.ibis.com/gb/hotel-5939-ibis-constanta/index.shtml", target := "_blank" )(
            "main business hotel"
          ),
          "."
        )
      )
    }

    def spaces = p( b( "Spaces: To come" ) )
    def gallery = p( b( "Gallery: To come" ) )
    def contact = p( b( "Contact: To come" ) )

    model.page match {
      case Page.Home    => home
      case Page.Spaces  => spaces
      case Page.Gallery => gallery
      case Page.Contact => contact
    }
  }

  def langSelector( model : Model ) : TypedTag[dom.Element] = {
    import I18n.Lang

    def langs = {
      val rawLangs = Lang.all.map { lang =>
        if ( lang == model.lang ) {
          a( cls := "activeLangCode" )(
            lang.code
          )
        } else {
          a( cls := "newLangCode", onclick := s"ro.casaleon.www.App().updateLangCode( '${lang.code}' )" )(
            lang.code
          )
        }
      }
      rawLangs.head +: rawLangs.tail.flatMap( ltt => Seq( span( " | " ), ltt ) )
    }

    div( id := "langSelector" )( langs )
  }

  def testPage : TypedTag[dom.Element] = {
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p("This is my first paragraph"),
          p("This is my second paragraph")
        )
      )
    ) 
  }
  def testBody : TypedTag[dom.Element] = {
    div(
      h1("This is my title"),
      div(
        p("This is my 1st paragraph."),
        p("This is my 2nd paragraph.")
      )
    )
  }
}
