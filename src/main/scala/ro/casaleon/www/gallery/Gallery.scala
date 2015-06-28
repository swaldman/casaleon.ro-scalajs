package ro.casaleon.www.gallery;

import org.scalajs.dom
import dom.document
import dom.raw.{Element,Node}

import ro.casaleon.www.DomUtils;


object Gallery {
  val EmptyModel = Model( None );

  var model : Model = EmptyModel;

  def rerender : Unit = {
    val galleryPane = dom.document.querySelector("#galleryPane");
    DomUtils.clearChildren( galleryPane );
    galleryPane.appendChild( Html.galleryContents( model ).render );
  }
  def showViewer( elem : Element, url : String ) : Unit = {
    val clientRect = elem.getBoundingClientRect
    val aspectRatio = clientRect.width / clientRect.height
    showViewer( aspectRatio, url );
  }
  def showViewer( aspectRatio : Double, url : String ) : Unit = {
    this.model = Model( Some( ImageUrlInfo( url, Some(aspectRatio) ) ) )
    rerender
  }
  def showViewer( url : String ) : Unit = {
    this.model = Model( Some( ImageUrlInfo( url, None ) ) )
    rerender
  }
  def closeViewer : Unit = {
    model = EmptyModel
    rerender
  }

  // currently the image viewer should take over the space occupied
  // by the main pane, so we look for that.
  def imageViewerDimension : Dimension = {
    val mainPane = dom.document.querySelector("#main")
    val clientRect = mainPane.getBoundingClientRect

    Dimension( clientRect.width.toInt, clientRect.height.toInt, clientRect.width / clientRect.height  )
  }
}

