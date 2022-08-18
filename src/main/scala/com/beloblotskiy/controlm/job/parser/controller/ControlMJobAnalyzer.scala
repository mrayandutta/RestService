package com.beloblotskiy.controlm.job.parser.controller

import javax.servlet.http._
import scala.xml.NodeSeq
import com.beloblotskiy.controlm.job.parser.model.Parser
import com.beloblotskiy.controlm.job.parser.model.Job
import java.io.File
import java.nio.file.Files
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths
import scala.collection.JavaConversions
import com.beloblotskiy.controlm.job.parser.view.ReportGenerator

/**
 * @author abelablotski
 */
class ControlMJobAnalyzer extends HttpServlet {
  
  /**
   * @return List of tuples: (path to file, load/parsing error or empty string if successful, list of jobs) or empty list
   */
  private def loadFilesFromDir(path: String): List[(String, String, List[Job])] = {
    
    def readAndParseFile(f: File): List[Job] = {
      val strlst = JavaConversions.asScalaBuffer(Files.readAllLines(Paths.get(f.getPath), StandardCharsets.ISO_8859_1)).toList
      val content = strlst.map(_.replace("<!DOCTYPE DEFTABLE SYSTEM \"deftable.dtd\">", "")).reduce(_ + _)
      Parser.parse(content)
    }
    
    val fp = new File(path)

    
    if (fp.exists()) {
      val res = (for { f <- fp.listFiles.filter(_.getName.endsWith(".xml")) }
      //val res = (for { f <- fp.listFiles.filter(_.isFile) }
        yield {
          val parsingRes: (List[Job], String) = try { (readAndParseFile(f), "") } catch { case e: Throwable => (List(), "ERROR: " + e.getMessage) }
          (f.getPath, parsingRes._2, parsingRes._1)
        }
      )
      
      if (res != null) res.toList else List()
    }
    else {
      List()      // there are no any files to read
    }
  }
  
  override def doGet(request: HttpServletRequest, response:HttpServletResponse) = {
    response.setContentType("text/html")
    response.setCharacterEncoding("UTF-8")
    
    val jobs = List()
    //val result = loadFilesFromDir("D:\\Projects2\\Scala\\parse_control-m_jobs\\ctm_jobs\\test3")
    val filePath ="D:\\All_Intellij_Projects\\parse_control-m_jobs-master\\src\\test\\resources\\"
    //val filePath ="D://All_Intellij_Projects//parse_control-m_jobs-master//src//test//resources//two_tables_with_two_jobs_each.xml"
    //val result = loadFilesFromDir("D:\\Projects2\\Scala\\parse_control-m_jobs\\ctm_jobs\\test3")
    val result = loadFilesFromDir(filePath)
    
    val responseBody: NodeSeq =
      <html><body>
				{ReportGenerator.generateJobsReport(result map (x => x._3) flatten)}
				<p></p><hr/><p></p>
        {ReportGenerator.generateLoadingReport(result)}
    	</body></html>
    response.getWriter.write(responseBody.toString)
  }
}