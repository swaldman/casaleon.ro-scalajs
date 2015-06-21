package ro.casaleon.www.gallery;

case class ImageUrlInfo( selectedImageUrl : String, aspectRatio : Double );
case class Model( selectedImageUrlInfo : Option[ImageUrlInfo] );


