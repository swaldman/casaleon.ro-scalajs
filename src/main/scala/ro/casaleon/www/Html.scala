package ro.casaleon.www;

//import scalatags.Text.all._
import scalatags.JsDom.all._
import scalatags.JsDom.TypedTag;
import org.scalajs.dom;

object Html {
  val AirBnBUrl = "https://www.airbnb.com/rooms/6231211"

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
            a( cls := cssClass, onclick := s"ro.casaleon.www.App().updatePageName( '${page.name}' )" )( raw( I18n.menuItemName( page, model.lang ) ) )
          }
        ),
        div( id := "content" )(
          content( model )
        )
      )
    )
  }

  def maybeVacationRentalsStar( model : Model ) : TypedTag[dom.Element] = {
    import I18n.Lang._

    val imgUrl = model.lang match {
      case EN => "images/vacation-rentals.png"
      case RO => "images/apartament-de-vacanta.png"
      case _  => "images/vacation-rentals.png"
    }
    if ( model.page == Page.Home ) {
      div( id := "vacationRentalsStar" )(
        a( href := AirBnBUrl, target := "_blank" )(
          img( src := imgUrl, width := "75%" )
        )
      )
    } else {
      script( `type` := "text/plain" )(
        "Omitted vacationRentalsStar"
      )
    }
  }

  def content( model : Model ) : TypedTag[dom.Element] = {
    def enContent = Seq[ scalatags.JsDom.Modifier ](
      a( href := "http://en.wikipedia.org/wiki/Constanța", target := "_blank" )(raw("Constan&#x163;a, Romania")), ". ",
      "Commercial space, office space, and ",
      a( href := AirBnBUrl, target := "_blank" )(
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
    );
    def roContent = Seq[ scalatags.JsDom.Modifier ](
      a( href := "http://ro.wikipedia.org/wiki/Constanța", target := "_blank" )(raw("Constan&#x163;a, Romania")), ". ",
      raw("Spa&tcedil;ii comerciale, spa&tcedil;ii pentru birouri, &scedil;i " ),
      a( href := AirBnBUrl, target := "_blank" )(
        raw( "un calderos Studio pentru vacan&tcedil;a" )
      ),
      ". ",
      raw( "O loca&tcedil;ie cu distinc&tcedil;ie, incarcata de "),
      a( href := "http://www.nytimes.com/2001/02/15/garden/romanian-past-interrupted.html?pagewanted=all&src=pm", target := "_blank" )(
        "istorie "
      ),
      a( href := "https://plus.google.com/101225362883468431775/about?gl=us&hl=en", target := "_blank" )(
        raw( "in inima ora&scedil;ului" )
      ),
      raw( ", langa " ),
      a( href := "http://www.ibis.com/gb/hotel-5939-ibis-constanta/index.shtml", target := "_blank" )(
        "cel mai important business hotel din zona. "
      ),
      raw(
        "Cu o privile&scedil;te uimitoare la Marea Neagra din orice birou sau balcon &scedil;i unde va pute&tcedil;i primi " +
        "oaspe&tcedil;ii pe imensa terasa de la ultimul nivel de unde se poate admira cea mai cunoscuta plaja a ora&scedil;ului " +
        "dar &scedil;i portul turistic Tomis."
      )
    );
    def home = {
      import I18n.Lang.RO

      div( id := "home" ) (
        div( id := "circleWindowFrame" )(
          img( id := "circleWindow", src := "images/cl_front_exterior_extra_sm_circular_window.png" )
        ),
        div( id := "homeContent" )( 
          model.lang match {
            case RO => roContent
            case _  => enContent
          }
        )
      )
    }

    def spaces = { 
      import I18n.Lang.RO

      def enContent = Seq[ scalatags.JsDom.Modifier ](
        p(
          "Our third floor vacation studio, adjacent to a stunning rooftop terrace ",
          "is now available via AirBnB."
        ),
        p( cls := "center" )(
          a( href := AirBnBUrl, cls := "booknow", target := "_blank" )(
            "Book now!"
          )
        ),
        p( 
          "Unfortunately, our commercial and office spaces are fully booked at the moment. ",
          "Thank you for your interest, and keep us in mind in the future!" 
        )
      )
      def roContent = Seq[ scalatags.JsDom.Modifier ](
        p(
          raw(
            "Studioul destinat vacan&tcedil;elor, situat langa terasa de 96 mp de la etajul " +
            "al treilea al imobilului, se poate rezerva prin AirBnB."
          )
        ),
        p( cls := "center" )(
          a( href := AirBnBUrl, cls := "booknow", target := "_blank" )(
            "Rezerva acum!"
          )
        ),
        p( 
          raw(
            "Din pacate spa&tcedil;iile nostre de cazare sau pentru birouri sunt rezervate integral " +
            "in acest moment."  
            //"Daca dori&tcedil;i sa fi&tcedil;i informat imediat ce se elibereaza " +
            //"un spa&tcedil;iu, lasa&tcedil;i-ne datele dumneavoastra de contact in formularul de mai jos."
          )
        )
      )
      div( cls := "textcard" )(
        model.lang match {
          case RO => roContent
          case _  => enContent
        }
      )
    }

    def gallery = ro.casaleon.www.gallery.Html.galleryPane 
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
