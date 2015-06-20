package ro.casaleon.www.gallery;

import scalatags.JsDom.all._
import scalatags.JsDom.TypedTag;
import org.scalajs.dom;

object Html {
  val ImageHeight = 100;
  val NumRows     =   4;
  val RowLength = {
    val exactGrid = Image.count % NumRows == 0
    ( Image.count / NumRows ) + ( if ( exactGrid ) 0 else 1 )
  }

  val SsimManagedUrls = true;

  def galleryPane : TypedTag[dom.Element] = div( id := "galleryPane" ){
    galleryTable
  }

  def galleryTable : TypedTag[dom.Element] = {
    def row( urls : IndexedSeq[String] ) : TypedTag[dom.Element] = div( cls := "galleryRow" )( 
      urls.map( url => a( cls := "galleryLink", onclick := "later" )( createImgTag( url ) ) ) 
    )

    val urlRows = Image.Urls.grouped( RowLength );
    div( cls := "galleryTable" )( urlRows.map( row ).toSeq )
  }

  def createImgTag( imgUrlStr : String ) : TypedTag[dom.Element] = {
    if ( SsimManagedUrls ) {
      img( src := ( imgUrlStr + "?height=" + ImageHeight ) )
    } else {
      img( src := imgUrlStr, width := ImageHeight )
    }
  }
}
