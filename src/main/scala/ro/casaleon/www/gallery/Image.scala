package ro.casaleon.www.gallery;

import scala.collection._

object Image {

  val Names = immutable.IndexedSeq(
    "IMG_2433.JPG", "IMG_3587.JPG", "IMG_3952.JPG", "IMG_4569.JPG", "IMG_4581.JPG",
    "IMG_4707.JPG", "IMG_4763.JPG", "IMG_5097.JPG", "IMG_5103.JPG", "IMG_2434.JPG",
    "IMG_3618.JPG", "IMG_3982.JPG", "IMG_4576.JPG", "IMG_4587.JPG", "IMG_4711.JPG",
    "IMG_5094.JPG", "IMG_5099.JPG", "IMG_6421.JPG", "IMG_2446.JPG", "IMG_3794.JPG",
    "IMG_4566.JPG", "IMG_4579.JPG", "IMG_4589.JPG", "IMG_4712.JPG", "IMG_5095.JPG",
    "IMG_5102.JPG"
  )

  def Urls = Names.map( BaseUrl + _ )

  val BaseUrl = "http://tickle.mchange.com/ssim/casaleon/"

  val count = Names.length;
}
