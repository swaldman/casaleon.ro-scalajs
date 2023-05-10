package ro.casaleon.www.gallery;

import scala.collection._
import scala.util.Random;

object Image {

  private val ZeroPads = Map( 0 -> "", 1 -> "0", 2 -> "00" )

  val BaseUrl = "https://tickle.mchange.com/ssim/site_cl/"

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
