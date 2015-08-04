package ro.casaleon.www.gallery;

import scala.collection._
import scala.util.Random;

object Image {

  /*
  val BaseUrl = "http://tickle.mchange.com/ssim/casaleon/"

  val Names = immutable.IndexedSeq(
    "IMG_2433.JPG", "IMG_3587.JPG", "IMG_3952.JPG", "IMG_4569.JPG", "IMG_4581.JPG",
    "IMG_4707.JPG", "IMG_4763.JPG", "IMG_5097.JPG", "IMG_5103.JPG", "IMG_2434.JPG",
    "IMG_3618.JPG", "IMG_3982.JPG", "IMG_4576.JPG", "IMG_4587.JPG", "IMG_4711.JPG",
    "IMG_5094.JPG", "IMG_5099.JPG", "IMG_6421.JPG", "IMG_2446.JPG", "IMG_3794.JPG",
    "IMG_4566.JPG", "IMG_4579.JPG", "IMG_4589.JPG", "IMG_4712.JPG", "IMG_5095.JPG",
    "IMG_5102.JPG"
  )
  */

  private val ZeroPads = Map( 0 -> "", 1 -> "0", 2 -> "00" )

  val BaseUrl = "http://tickle.mchange.com/ssim/site_cl/"

  lazy val Names = Random.shuffle( (1 to 269).map( num => ("site_cl_" + threeDigitNum( num ) + ".jpg") ) )

  lazy val Urls = Names.map( BaseUrl + _ )

  lazy val count = Names.length;

  private def threeDigitNum( num : Int ) = {
    require (num > 0 && num < 1000)
    val numStr = num.toString;
    val padLen = 3 - num.toString.length;
    ZeroPads( padLen ) + numStr;
  }
}
