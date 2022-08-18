package com.beloblotskiy.controlm.job.parser.view

import scala.xml.NodeSeq
import com.beloblotskiy.controlm.job.parser.model.Job
import com.beloblotskiy.controlm.job.parser.model.Control
import com.beloblotskiy.controlm.job.parser.model.InCond
import com.beloblotskiy.controlm.job.parser.model.OutCond

/**
 * @author v-abelablotski
 */
object ReportGenerator {

  /**
   * Report about loading process.
   */
  def generateLoadingReport(loadingResults: List[(String, String, List[Job])]): NodeSeq = {
    
    def genReportRow(path: String, errorMsg: String, jobs: List[Job]): NodeSeq = {
      <td>{path}</td><td>{if (errorMsg.length == 0) "Ok" else errorMsg }</td><td>{jobs.length}</td>
    }
    
    def genReportRows(loadingResults: List[(String, String, List[Job])]): NodeSeq = {
      for {r <- loadingResults } 
        yield <tr>{genReportRow(r._1, r._2, r._3)}</tr>
    }
    
    <table border="1">
			<tr>
				<th>File</th><th>Status</th><th>Number of jobs</th>
			</tr>
			{genReportRows(loadingResults)}
		</table>
  }
  
  /**
   * Generate report about Control-M jobs
   */
  def generateJobsReport(jobs: List[Job]): NodeSeq = {
    
    def genReportRow(job: Job): NodeSeq = {
      
      def genControlsTable(controls: List[Control]): NodeSeq = {
        <table border="1">
					{for {c <- controls} yield <tr><td>{c.name}</td><td>{c.controlType}</td></tr>}
				</table>
      }
      
      def genInCondTable(conditions: List[InCond]): NodeSeq = {
        <table border="1">
          {for {c <- conditions} yield <tr><td>{c.name}</td><td>{c.andOr}</td><td>{c.oDate}</td></tr>}
        </table>
      }
      
      def genOutCondTable(conditions: List[OutCond]): NodeSeq = {
        <table border="1">
          {for {c <- conditions} yield <tr><td>{c.name}</td><td>{c.sign}</td><td>{c.oDate}</td></tr>}
        </table>
      }
      
      <td>{job.table.dataCenter}</td><td>{job.table.name}</td><td>{job.application}</td><td>{job.jobName}</td><td>{job.cyclic}</td><td>{job.cyclicType}</td>
<td>{job.cyclicIntervalSequence}</td><td>{"(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),".r.replaceAllIn(job.cyclicTimesSequence.getOrElse("-"), "$1,$2,$3,$4,$5,$6,$7,$8,$9,$10, ")}</td><td>{job.cyclicTolerance}</td><td>{job.cyclicInd}</td><td>{job.cyclicInterval}</td>
      <td>{job.description}</td><td>{job.taskType}</td><td>{genControlsTable(job.controls)}</td><td>{genInCondTable(job.inConds)}</td><td>{genOutCondTable(job.outConds)}</td>
    }
    
    def genReportRows(jobs: List[Job]): NodeSeq = {
      for { j <- jobs }
        yield <tr>{genReportRow(j)}</tr>
    }
    
    <table border="1">
      <tr>
        <th>Data center</th><th>Table</th><th>Application</th><th>Job name</th><th>Cyclic</th><th>Cyclic type</th>
<th>Cyclic Int Seq</th><th>Cyclic Times Seq</th><th>Cyclic Tlr</th><th>Cyclic Ind</th><th>Cyclic Int</th>
<th>Description</th><th>Task type</th><th>Controls (Resources)</th><th>In conditions</th><th>Out conditions</th>
      </tr>
			{genReportRows(jobs)}
    </table>
  }
  
}