package ro.casaleon.www;

object Page {
  case object Home    extends Page;
  case object Spaces  extends Page;
  case object Gallery extends Page;
  case object Contact extends Page;

  val all = Seq( Home, Spaces, Gallery, Contact )

  def apply( name : String ) : Option[Page] = all.find( _.name == name )
}
sealed trait Page {
  val name : String = this.getClass.getSimpleName.filter( _ != '$' )
}

