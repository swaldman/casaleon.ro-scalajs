package ro.casaleon.www.gallery;

case class ImageUrlInfo( selectedImageUrl : String, mbAspectRatio : Option[Double] );
case class Model( selectedImageUrlInfo : Option[ImageUrlInfo] );


