package ro.casaleon.www;

//import scalatags.Text.all._
import scalatags.JsDom.all._
import scalatags.JsDom.TypedTag;
import org.scalajs.dom;

object Html {
  def mainFrame( model : Model ) : TypedTag[dom.Element] = {
    div( id := "main" )(
      div( id := "stretcher" )(
        div( id := "bottomsect" )(
          i( raw("Str. Mircea cel Batr&#226;n nr. 43 &rightsquigarrow; Constan&#x163;a 900658 &rightsquigarrow; Romania") )
        )
      ),
      maybeVacationRentalsStar( model ),
      langSelector( model ),
      div( id := "topsect" )(
        div( id := "cl_champagne" )(
          img( src := "images/CasaLeonChampagne_800.png" )
        ),
        div( id := "main_menu" )(
          Page.all.map { page =>
            val cssClass = if ( page == model.page ) "black menu_item" else "menu_item";
            a( cls := cssClass, onclick := s"ro.casaleon.www.App().updatePageName( '${page.name}' )" )( I18n.menuItemName( page, model.lang ) )
          }
        ),
        div( id := "content" )(
          content( model )
        )
      )
    )
  }

  def maybeVacationRentalsStar( model : Model ) : TypedTag[dom.Element] = {
    if ( model.page == Page.Home ) {
      div( id := "vacationRentalsStar" )(
        a( href := "https://www.airbnb.com/rooms/6231211", target := "_blank" )(
          img( src := "images/vacation-rentals.png", width := "75%" )
        )
      )
    } else {
      script( `type` := "text/plain" )(
        "Omitted vacationRentalsStar"
      )
    }
  }

  def content( model : Model ) : TypedTag[dom.Element] = {
    def home = {
      div( id := "home" ) (
        div( id := "circleWindowFrame" )(
          img( id := "circleWindow", src := "images/cl_front_exterior_extra_sm_circular_window.png" )
        ),
        div( id := "homeContent" )(
          a( href := "http://en.wikipedia.org/wiki/Constanța", target := "_blank" )(raw("Constan&#x163;a, Romania")), ". ",
          "Commercial space, office space, and ",
          a( href := "https://www.airbnb.com/rooms/6231211", target := "_blank" )(
            "a fine vacation studio"
          ),
          ". ",
          "An address with distinction, and a ",
          a( href := "http://www.nytimes.com/2001/02/15/garden/romanian-past-interrupted.html?pagewanted=all&src=pm", target := "_blank" )(
            "history"
          ),
          ". ",
          "Stunning views of the sea, from your desk or from your balcony. ",
          "Entertain guests on the building's panaoramic roof terraces. ",
          "In the ",
          a( href := "https://plus.google.com/101225362883468431775/about?gl=us&hl=en", target := "_blank" )(
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

    def spaces = div( id := "placeholder" )( p( b( "Spaces: To come" ) ) )
    def gallery = div( id := "placeholder" )( p( b( "Gallery: To come" ) ) )
    def contact = div( id := "placeholder" )( p( b( "Contact: To come" ) ) )

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
}
