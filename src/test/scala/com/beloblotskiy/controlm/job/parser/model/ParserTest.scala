package com.beloblotskiy.controlm.job.parser.model

import scala.io.Source
import org.junit.Test
import org.junit.Assert._

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.collection.JavaConversions

/**
 * @author abelablotski
 */
class ParserTest {

  @Test
  def loadFilesFromDir(): Unit = {
    val filePath ="D://All_Intellij_Projects//parse_control-m_jobs-master//src//test//resources//two_tables_with_two_jobs_each.xml"
    val f = new File(filePath)
    val strlst = JavaConversions.asScalaBuffer(Files.readAllLines(Paths.get(f.getPath), StandardCharsets.ISO_8859_1)).toList
    val content = strlst.map(_.replace("<!DOCTYPE DEFTABLE SYSTEM \"deftable.dtd\">", "")).reduce(_ + _)
    Parser.parse(content)




  }
  
  @Test
  def test1_single_job() = {
    val testJobXml = Source.fromURL(getClass.getResource("/single_job1.xml")).mkString
    
    val jobs = Parser.parse(testJobXml)
    assertTrue("Check number of elements in a job list", jobs.length == 1)
    
    val job = jobs.head
    assertTrue("Check job.table.dataCenter", job.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job.application == "CTM_APP1")
    assertTrue("Check job.name", job.jobName == "CTM_JOB_NAME1")
    assertTrue("Check job.cyclic", !job.cyclic)
    assertTrue("Check job.cyclicType", job.cyclicType == "Interval")
    assertTrue("Check job.description", job.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job.taskType == "Job")
    
    assertTrue("Check job.controls.length", job.controls.length == 1)
    assertTrue("Check job.controls.head.name", job.controls.head.name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls.head.controlType", job.controls.head.controlType == "E")
    
    assertTrue("Check job.inConds.length", job.inConds.length == 1)
    assertTrue("Check job.inConds.head.name", job.inConds.head.name == "INCOND1-ENDED")
    assertTrue("Check job.inConds.head.andOr", job.inConds.head.andOr == "AND")
    assertTrue("Check job.inConds.head.oDate", job.inConds.head.oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job.outConds.length == 1)
    assertTrue("Check job.outConds.head.name", job.outConds.head.name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds.head.andOr", job.outConds.head.sign == "DEL")
    assertTrue("Check job.outConds.head.oDate", job.outConds.head.oDate == "STAT")
  }
  
  @Test
  def test2_single_job() = {
    val testJobXml = Source.fromURL(getClass.getResource("/single_job2.xml")).mkString
    
    val jobs = Parser.parse(testJobXml)
    assertTrue("Check number of elements in a job list", jobs.length == 1)
    
    val job = jobs.head
    assertTrue("Check job.table.dataCenter", job.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job.application == "CTM_APP1")
    assertTrue("Check job.name", job.jobName == "CTM_JOB_NAME1")
    assertTrue("Check job.cyclic", !job.cyclic)
    assertTrue("Check job.cyclicType", job.cyclicType == "Interval")
    assertTrue("Check job.description", job.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job.taskType == "Job")
    
    assertTrue("Check job.controls.length", job.controls.length == 2)
    assertTrue("Check job.controls(0).name", job.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job.inConds(1).oDate == "STAT")
  }
  
  @Test
  def test3_two_jobs_in_one_table() = {
    val testJobXml = Source.fromURL(getClass.getResource("/two_jobs_in_one_table1.xml")).mkString
    
    val jobs = Parser.parse(testJobXml)
    assertTrue("Check number of elements in a job list", jobs.length == 2)
    
    val job1 = jobs(0)
    assertTrue("Check job.table.dataCenter", job1.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job1.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job1.application == "CTM_APP1")
    assertTrue("Check job.name", job1.jobName == "CTM_JOB_NAME1")
    assertTrue("Check job.cyclic", !job1.cyclic)
    assertTrue("Check job.cyclicType", job1.cyclicType == "Interval")
    assertTrue("Check job.description", job1.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job1.taskType == "Job")
    
    assertTrue("Check job.controls.length", job1.controls.length == 2)
    assertTrue("Check job.controls(0).name", job1.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job1.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job1.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job1.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job1.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job1.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job1.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job1.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job1.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job1.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job1.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job1.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    val job2 = jobs(1)
    assertTrue("Check job.table.dataCenter", job2.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job2.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job2.application == "CTM_APP2")
    assertTrue("Check job.name", job2.jobName == "CTM_JOB_NAME2")
    assertTrue("Check job.cyclic", !job2.cyclic)
    assertTrue("Check job.cyclicType", job2.cyclicType == "Interval")
    assertTrue("Check job.description", job2.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job2.taskType == "Job")
    
    assertTrue("Check job.controls.length", job2.controls.length == 2)
    assertTrue("Check job.controls(0).name", job2.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job2.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job2.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job2.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job2.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job2.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job2.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job2.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job2.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job2.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job2.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job2.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
  }

  @Test
  def test3_two_tables_with_two_jobs_each() = {
    val testJobXml = Source.fromURL(getClass.getResource("/two_tables_with_two_jobs_each.xml")).mkString
    
    val jobs = Parser.parse(testJobXml)
    assertTrue("Check number of elements in a job list", jobs.length == 4)
    
    val job1 = jobs(0)
    assertTrue("Check job.table.dataCenter", job1.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job1.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job1.application == "CTM_APP1")
    assertTrue("Check job.name", job1.jobName == "CTM_JOB_NAME1")
    assertTrue("Check job.cyclic", !job1.cyclic)
    assertTrue("Check job.cyclicType", job1.cyclicType == "Interval")
    assertTrue("Check job.description", job1.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job1.taskType == "Job")
    
    assertTrue("Check job.controls.length", job1.controls.length == 2)
    assertTrue("Check job.controls(0).name", job1.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job1.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job1.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job1.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job1.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job1.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job1.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job1.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job1.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job1.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job1.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job1.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    val job2 = jobs(1)
    assertTrue("Check job.table.dataCenter", job2.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job2.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job2.application == "CTM_APP2")
    assertTrue("Check job.name", job2.jobName == "CTM_JOB_NAME2")
    assertTrue("Check job.cyclic", !job2.cyclic)
    assertTrue("Check job.cyclicType", job2.cyclicType == "Interval")
    assertTrue("Check job.description", job2.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job2.taskType == "Job")
    
    assertTrue("Check job.controls.length", job2.controls.length == 2)
    assertTrue("Check job.controls(0).name", job2.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job2.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job2.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job2.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job2.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job2.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job2.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job2.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job2.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job2.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job2.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job2.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
    
    val job3 = jobs(2)
    assertTrue("Check job.table.dataCenter", job3.table.dataCenter == "CTM_DC2")
    assertTrue("Check job.table.name", job3.table.name == "CTM_TABLE2")
    assertTrue("Check application name", job3.application == "CTM_APP1")
    assertTrue("Check job.name", job3.jobName == "CTM_JOB_NAME3")
    assertTrue("Check job.cyclic", !job3.cyclic)
    assertTrue("Check job.cyclicType", job3.cyclicType == "Interval")
    assertTrue("Check job.description", job3.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job3.taskType == "Job")
    
    assertTrue("Check job.controls.length", job3.controls.length == 2)
    assertTrue("Check job.controls(0).name", job3.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job3.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job3.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job3.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job3.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job3.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job3.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job3.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job3.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job3.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job3.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job3.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job3.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job3.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job3.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job3.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job3.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job3.inConds(1).oDate == "STAT")
    
    val job4 = jobs(3)
    assertTrue("Check job.table.dataCenter", job4.table.dataCenter == "CTM_DC2")
    assertTrue("Check job.table.name", job4.table.name == "CTM_TABLE2")
    assertTrue("Check application name", job4.application == "CTM_APP4")
    assertTrue("Check job.name", job4.jobName == "CTM_JOB_NAME4")
    assertTrue("Check job.cyclic", !job4.cyclic)
    assertTrue("Check job.cyclicType", job4.cyclicType == "Interval")
    assertTrue("Check job.description", job4.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job4.taskType == "Job")
    
    assertTrue("Check job.controls.length", job4.controls.length == 2)
    assertTrue("Check job.controls(0).name", job4.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job4.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job4.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job4.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job4.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job4.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job4.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job4.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job4.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job4.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job4.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job4.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job4.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job4.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job4.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job4.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job4.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job4.inConds(1).oDate == "STAT")
  }
  
  @Test
  def test4_cyclic1() = {
    val testJobXml = Source.fromURL(getClass.getResource("/cyclic_jobs1.xml")).mkString
    
    val jobs = Parser.parse(testJobXml)
    assertTrue("Check number of elements in a job list", jobs.length == 8)
    
    val job1 = jobs(0)
    assertTrue("Check job.table.dataCenter", job1.table.dataCenter == "DataCenter1")
    assertTrue("Check job.table.name", job1.table.name == "CYCLIC1_EXP1")
    assertTrue("Check application name", job1.application == "APP1")
    assertTrue("Check job.name", job1.jobName == "CYCLIC1_EXP1_TRANSACTIONS_FLAT_LZ_Sqoop")
    assertTrue("Check job.cyclic", job1.cyclic)
    assertTrue("Check job.cyclicType", job1.cyclicType == "IntervalSequence")
    assertTrue("Check job.description", job1.description.get == "Flattened transactions table")
    assertTrue("Check job.taskType", job1.taskType == "Job")
    
    assertTrue("Check job.cyclicIntervalSequence", job1.cyclicIntervalSequence.getOrElse("none") == "+1M")
    assertTrue("Check job.cyclicTimesSequence", job1.cyclicTimesSequence.getOrElse("none") == "1000,1005,1010,1015,1020,1025,1030,1035,1040,1045,1050,1055,1100,1105,1110,1115,1120,1125,1130,1135,1140,1145,1150,1155,1200,1205,1210,1215,1220,1225,1230,1235,1240,1245,1250,1255,1300,1305,1310,1315,1320,1325,1330,1335,1340,1345,1350,1355,1400,1405,1410,1415,1420,1425,1430,1435,1440,1445,1450,1455,1500,1505,1510,1515,1520,1525,1530,1535,1540,1545,1550,1555,1600,1605,1610,1615,1620,1625,1630,1635,1640,1645,1650,1655,1700,1705,1710,1715,1720,1725,1730,1735,1740,1745,1750,1755")
    assertTrue("Check job.cyclicTolerance", job1.cyclicTolerance.getOrElse("none") == "5")
    assertTrue("Check job.cyclicInd", job1.cyclicInd.getOrElse("none") == "END")
    assertTrue("Check job.cyclicInterval", job1.cyclicInterval.getOrElse("none") == "00001M")
    
    /*
    assertTrue("Check job.controls.length", job1.controls.length == 2)
    assertTrue("Check job.controls(0).name", job1.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job1.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job1.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job1.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job1.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job1.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job1.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job1.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job1.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job1.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job1.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job1.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job1.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job1.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job1.inConds(1).oDate == "STAT")
    
    val job2 = jobs(1)
    assertTrue("Check job.table.dataCenter", job2.table.dataCenter == "CTM_DC1")
    assertTrue("Check job.table.name", job2.table.name == "CTM_TABLE1")
    assertTrue("Check application name", job2.application == "CTM_APP2")
    assertTrue("Check job.name", job2.jobName == "CTM_JOB_NAME2")
    assertTrue("Check job.cyclic", !job2.cyclic)
    assertTrue("Check job.cyclicType", job2.cyclicType == "Interval")
    assertTrue("Check job.description", job2.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job2.taskType == "Job")
    
    assertTrue("Check job.controls.length", job2.controls.length == 2)
    assertTrue("Check job.controls(0).name", job2.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job2.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job2.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job2.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job2.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job2.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job2.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job2.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job2.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job2.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job2.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job2.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job2.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job2.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job2.inConds(1).oDate == "STAT")
    
    val job3 = jobs(2)
    assertTrue("Check job.table.dataCenter", job3.table.dataCenter == "CTM_DC2")
    assertTrue("Check job.table.name", job3.table.name == "CTM_TABLE2")
    assertTrue("Check application name", job3.application == "CTM_APP1")
    assertTrue("Check job.name", job3.jobName == "CTM_JOB_NAME3")
    assertTrue("Check job.cyclic", !job3.cyclic)
    assertTrue("Check job.cyclicType", job3.cyclicType == "Interval")
    assertTrue("Check job.description", job3.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job3.taskType == "Job")
    
    assertTrue("Check job.controls.length", job3.controls.length == 2)
    assertTrue("Check job.controls(0).name", job3.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job3.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job3.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job3.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job3.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job3.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job3.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job3.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job3.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job3.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job3.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job3.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job3.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job3.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job3.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job3.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job3.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job3.inConds(1).oDate == "STAT")
    
    val job4 = jobs(3)
    assertTrue("Check job.table.dataCenter", job4.table.dataCenter == "CTM_DC2")
    assertTrue("Check job.table.name", job4.table.name == "CTM_TABLE2")
    assertTrue("Check application name", job4.application == "CTM_APP4")
    assertTrue("Check job.name", job4.jobName == "CTM_JOB_NAME4")
    assertTrue("Check job.cyclic", !job4.cyclic)
    assertTrue("Check job.cyclicType", job4.cyclicType == "Interval")
    assertTrue("Check job.description", job4.description.get == "Ctm Test Job 1")
    assertTrue("Check job.taskType", job4.taskType == "Job")
    
    assertTrue("Check job.controls.length", job4.controls.length == 2)
    assertTrue("Check job.controls(0).name", job4.controls(0).name == "TEST_CONTROL1-RES")
    assertTrue("Check job.controls(0).controlType", job4.controls(0).controlType == "E")
    assertTrue("Check job.controls(1).name", job4.controls(1).name == "TEST_CONTROL2-RES")
    assertTrue("Check job.controls(1).controlType", job4.controls(1).controlType == "E")
    
    assertTrue("Check job.inConds.length", job4.inConds.length == 2)
    assertTrue("Check job.inConds(0).name", job4.inConds(0).name == "INCOND1-ENDED")
    assertTrue("Check job.inConds(0).andOr", job4.inConds(0).andOr == "AND")
    assertTrue("Check job.inConds(0).oDate", job4.inConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job4.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job4.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job4.inConds(1).oDate == "STAT")
    
    assertTrue("Check job.outConds.length", job4.outConds.length == 2)
    assertTrue("Check job.outConds(0).name", job4.outConds(0).name == "OUTCOND1-ENDED")
    assertTrue("Check job.outConds(0).andOr", job4.outConds(0).sign == "DEL")
    assertTrue("Check job.outConds(0).oDate", job4.outConds(0).oDate == "STAT")
    assertTrue("Check job.inConds(1).name", job4.inConds(1).name == "INCOND2-ENDED")
    assertTrue("Check job.inConds(1).andOr", job4.inConds(1).andOr == "AND")
    assertTrue("Check job.inConds(1).oDate", job4.inConds(1).oDate == "STAT")
    */
  }
}