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
      case Some( ImageUrlInfo( url, mbAspectRatio ) ) => {
        div(
          imageViewer( url, mbAspectRatio ),
          script( "setTimeout( function() {document.getElementById('imageViewer').focus()}, 100 )" ) // this feels like a terrible hack.
        )
      }
      case None => galleryTableFrame
    }
  }

  def imageViewer( url : String, mbImageAspectRatio : Option[Double] ) : TypedTag[dom.Element] = {

    val imageAspectRatio = mbImageAspectRatio.getOrElse(1d);

    val dim = Gallery.imageViewerDimension;

    val availableHeight = dim.height - ImageViewerHeaderImageHeight;
    val viewerPanelAspectRatio = dim.width.toDouble / availableHeight

    val fullWidth : Boolean = imageAspectRatio > viewerPanelAspectRatio;

    val urlIndex = Image.Urls.indexOf( url )

    val mbPrevUrl : Option[String] = if ( urlIndex > 0 ) Some( Image.Urls(urlIndex - 1) ) else None;
    val mbNextUrl : Option[String] = if ( urlIndex >= 0 && urlIndex < Image.Urls.size - 1 ) Some( Image.Urls( urlIndex + 1 ) ) else None;

    // preloads
    val preloads = div( display := "none" )(
      mbPrevUrl.map( u => img( src := u, display := "none" ) ).toSeq ++ mbNextUrl.map( u => img( src := u, display := "none" ) ).toSeq
    );

    val keypressHandler = (e : dom.KeyboardEvent) => {
      import dom.ext.KeyCode

      e.keyCode match {
        case KeyCode.Escape => Gallery.closeViewer
        case KeyCode.Left => mbPrevUrl.foreach( Gallery.showViewer( _ ) );
        case KeyCode.Right => mbNextUrl.foreach( Gallery.showViewer( _ ) );
      }
    }

    var down : Option[dom.TouchEvent] = None;
    var last : Option[dom.TouchEvent] = None;

    val handleTouchStart = (e : dom.TouchEvent ) => {
      //println("handleTouchStart");
      //scala.scalajs.js.Dynamic.global.console.log( e );
      down = Some( e );
    }
    val handleTouchMove = (e : dom.TouchEvent) => {
      //println("handleTouchMove");
      //scala.scalajs.js.Dynamic.global.console.log( e );
      last = Some( e );
    }
    val handleTouchEnd = (e : dom.TouchEvent ) => {
      //println("handleTouchEnd");
      //scala.scalajs.js.Dynamic.global.console.log( e );

      val _down = down;
      val _last = last;

      down = None;
      last = None;

      _down.foreach { d =>
        _last.foreach { l =>
          var xDiff = l.touches(0).pageX - d.touches(0).pageX;
          var yDiff = l.touches(0).pageY - d.touches(0).pageY;
          if (xDiff == 0 && yDiff == 0) { // workaround what I can't help but think is an iphone / ios bug, see package.scala
            val forced = forceExtractTopLevelPageXY( e );
            xDiff = forced._1 - d.touches(0).pageX;
            yDiff = forced._2 - d.touches(0).pageY;
          }
          //println( s"xDiff: ${xDiff}; yDiff: ${yDiff}" );
          if ( math.abs( xDiff ) > math.abs( yDiff ) ) { // horizontal-ish
            if ( xDiff > 0 ) mbPrevUrl.foreach( Gallery.showViewer( _ ) ) else mbNextUrl.foreach( Gallery.showViewer( _ ) )
          }
        }
      }
    }

    div (
      id := "imageViewer",
      width := dim.width,
      height := dim.height,
      onkeydown := keypressHandler,
      tabindex := 1
    ) {
      val sizing = if (fullWidth) (width := dim.width) else (height := availableHeight);
      Seq(
        div( id := "imageViewerHeader" )(
          a( id := "imageViewerPanelClose", onclick := ((_ : dom.Event) => Gallery.closeViewer) )(
            img( src := ImageViewerPanelClose, border := 0 )
          ),
          img( src := ImageViewerHeaderImage )
        ),
        div( id := "imageViewerPanel", "ontouchstart".attr := handleTouchStart, "ontouchmove".attr := handleTouchMove, "ontouchend".attr := handleTouchEnd )(
          div( id := "previousImageLink" )(
            mbPrevUrl.fold( a() )( prevUrl => a( onclick := ((_ : dom.Event) => Gallery.showViewer( prevUrl )) )( raw("&#8604;") ) )
          ),
          div( id := "nextImageLink" )(
            mbNextUrl.fold( a() )( nextUrl => a( onclick := ((_ : dom.Event) => Gallery.showViewer( nextUrl )) )( raw("&rightsquigarrow;") ) )
          ),
          if ( mbImageAspectRatio != None ) { // this is the final aspect ratio
            img( id := "galleryImage", sizing, src := url )
          } else {
            val onLoadFcn = (evt : dom.Event) => { // check to see whether our aspect raio guess led to okay sizing
              val imgTag = evt.target.asInstanceOf[dom.raw.HTMLImageElement]
              val realAspectRatio = imgTag.naturalWidth.toDouble / imgTag.naturalHeight;
              if ( (realAspectRatio > viewerPanelAspectRatio) != fullWidth ) { // we guessed a bad relative aspect ratio
                Gallery.showViewer( realAspectRatio, url )
              }
            }
            val onLoadAttr = onload := onLoadFcn
            img( sizing, src := url, onLoadAttr )
          },
          preloads // does not display
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
      img( src := imgUrlStr, height := ImageHeight )
    }
  }
}
