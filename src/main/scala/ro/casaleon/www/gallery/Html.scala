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

  val ImageViewerHeaderImage       = "images/CasaLeonChampagne_400_inverted.png"
  val ImageViewerHeaderImageHeight = 95

  val ImageViewerPanelClose = "images/image_panel_close.png"

  def galleryPane : TypedTag[dom.Element] = div( id := "galleryPane" ){
      galleryContents( Gallery.model )
  }

  def galleryContents( model : Model ) : TypedTag[dom.Element] = {
    model.selectedImageUrlInfo match {
      case Some( ImageUrlInfo( url, aspectRatio ) ) => imageViewer( url, aspectRatio )
      case None                                     => galleryTableFrame
    }
  }

  def imageViewer( url : String, imageAspectRatio : Double ) : TypedTag[dom.Element] = {

    val dim = Gallery.imageViewerDimension;

    val availableHeight = dim.height - ImageViewerHeaderImageHeight;
    val viewerPanelAspectRatio = dim.width.toDouble / availableHeight

    val fullWidth : Boolean = imageAspectRatio > viewerPanelAspectRatio;

    val urlIndex = Image.Urls.indexOf( url )

    val mbPrevUrl : Option[String] = if ( urlIndex > 0 ) Some( Image.Urls(urlIndex - 1) ) else None;
    val mbNextUrl : Option[String] = if ( urlIndex >= 0 && urlIndex < Image.Urls.size - 1 ) Some( Image.Urls( urlIndex + 1 ) ) else None;

    div ( id := "imageViewer", width := dim.width, height := dim.height ){
      val sizing = if (fullWidth) (width := dim.width) else (height := availableHeight);
      Seq(
        div( id := "imageViewerHeader" )(
          a( id := "imageViewerPanelClose", onclick := ((_ : dom.Event) => Gallery.closeViewer) )(
            img( src := ImageViewerPanelClose, border := 0 )
          ),
          img( src := ImageViewerHeaderImage )
        ),
        div( id := "imageViewerPanel" ) (
          img( sizing, src := url )
        )
      )
    }
  }

  def galleryTableFrame : TypedTag[dom.Element] = div( id := "galleryTableFrame" )( galleryTable )

  def galleryTable : TypedTag[dom.Element] = {
    def row( urls : IndexedSeq[String] ) : TypedTag[dom.Element] = div( cls := "galleryRow" ){ 
      def handler( url : String ) = (evt : dom.MouseEvent) => Gallery.showViewer( evt.target.asInstanceOf[dom.Element], url );
      urls.map( url => a( cls := "galleryLink", onclick := handler( url ) )( createImgTag( url ) ) )
    }

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
