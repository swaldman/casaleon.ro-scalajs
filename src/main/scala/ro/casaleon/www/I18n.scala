package ro.casaleon.www;

object I18n {

  object Lang {
    case object EN extends Lang( "en", "English" )
    case object RO extends Lang( "ro", "Romanian" )

    val all = Seq( RO, EN );

    def apply( code : String ) : Option[Lang] = all.find( _.code == code )
  }
  sealed abstract class Lang( val code : String, val name : String );

  def menuItemName( page : Page, lang : Lang ) : String = {
    import Lang._;
    import Page._;

    ( page, lang ) match {
      case ( Home,    EN ) => "home"
      case ( Home,    RO ) => "acasa"
      case ( Spaces,  EN ) => "spaces"
      case ( Spaces,  RO ) => "spa&tcedil;ii"
      case ( Gallery, EN ) => "gallery"
      case ( Gallery, RO ) => "gallerie"
      case ( Contact, EN ) => "contact"
      case ( Contact, RO ) => "contact"
    }
  }

  def menuItemNames( lang : Lang ) : Seq[String] = Page.all.map( page => menuItemName( page, lang ) )

  def menuItemNames( model : Model ) : Seq[String] = menuItemNames( model.lang )

  def activeMenuItemName( model : Model ) = menuItemName( model.page, model.lang )

}
