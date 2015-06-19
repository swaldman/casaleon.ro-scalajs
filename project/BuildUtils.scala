import java.io._
import java.nio.file._

import scala.annotation.tailrec

import scala.io.Codec
import scala.io.Source

object BuildUtils {
  implicit val codec = Codec.UTF8

  def copyTextFile( src : File, dst : File, replacePairs : Seq[Tuple2[String,String]] = Nil )( implicit codec : Codec ) : Unit = {
    def replaced( line : String ) : String = {
      @tailrec
      def replace( lastline : String, remainingPairs : Seq[Tuple2[String,String]] ) : String = {
        remainingPairs match {
          case Seq( head, tail @ _* ) => replace( lastline.replaceAll( head._1, head._2 ), tail )
          case _                      => lastline
        }
      }
      replace( line, replacePairs )
    }
    val lines = Source.fromFile( src )( codec ).getLines
    val filtered = lines.map( replaced )
    val pw = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( dst ), codec.name ) ) )
    try filtered.foreach( line => pw.println( line ) ) finally pw.close
  }
  def copyHtmlFiles( srcDir : File, destDir : File, replacePairs : Seq[Tuple2[String,String]] = Nil )( implicit codec : Codec ) : Unit = {
    if ( srcDir.exists ) {
      val ff = new FilenameFilter {
        def accept( dir : File, name : String ) : Boolean = name.toLowerCase.endsWith(".html")
      }
      srcDir.listFiles(ff).foreach { srcFile =>
        copyTextFile( srcFile, new File( destDir, srcFile.getName ), replacePairs )
      }
    }
  }
  def copyDirectoryContents( srcDir : File, destDir : File ) : Unit = {
    if ( srcDir.exists && srcDir.isDirectory ) {
      val srcDirPath = srcDir.toPath
      val destDirPath = destDir.toPath
      val fileVisitor = new SimpleFileVisitor[Path] {
        override def visitFile( file : Path, attrs : attribute.BasicFileAttributes ) : FileVisitResult = {
          val relPath = srcDirPath.relativize( file );
          val destPath = destDirPath.resolve( relPath )
          Files.copy( file, destPath, StandardCopyOption.REPLACE_EXISTING )
          FileVisitResult.CONTINUE
        }
        override def preVisitDirectory( dir : Path, attrs : attribute.BasicFileAttributes ) : FileVisitResult = {
          val relPath = srcDirPath.relativize( dir );
          val destPath = destDirPath.resolve( relPath )
          Files.createDirectories( destPath )
          FileVisitResult.CONTINUE
        }
      }
      Files.walkFileTree( srcDirPath, fileVisitor )
    }
  }
  def createPublicHtmlDirectory( 
    parentDir       : File, 
    dirName         : String,
    jsFileNameToken : String,
    htmlSrcDir      : File,
    srcJsFile       : File, 
    jsDirName       : String, 
    assetsSrcDir    : File
  ) : File = {
    val out = new File( parentDir, dirName )
    out.mkdirs

    val replacePairs = Seq( ( jsFileNameToken, jsDirName + "/" + srcJsFile.getName ) )
    copyHtmlFiles( htmlSrcDir, out, replacePairs )

    val jsDir = new File( out, jsDirName )
    jsDir.mkdirs

    Files.copy( srcJsFile.toPath, new File( jsDir, srcJsFile.getName ).toPath, StandardCopyOption.REPLACE_EXISTING )

    val srcMapFile = new File( srcJsFile.getParent, srcJsFile.getName + ".map" )
    Files.copy( srcMapFile.toPath, new File( jsDir, srcMapFile.getName ).toPath, StandardCopyOption.REPLACE_EXISTING )

    copyDirectoryContents( assetsSrcDir, out )

    out
  }
}

