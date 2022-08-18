package com.beloblotskiy.controlm.job.parser.model

import scala.xml._
import scala.collection.mutable.MutableList
import java.util.NoSuchElementException

/**
 * Parse Control-M job XML
 * @author abelablotski
 */
object Parser {
  val printer = new scala.xml.PrettyPrinter(80, 2)
  
  def formatXml(xml: scala.xml.Node): Unit =
    println(printer.format(xml))
  
  /**
   * Returns None if attribute's key isn't found or Some string value.
   */
  private def parseAttrStrOpt(node: Node, attrKey: String): Option[String] = {
    if (node.attribute(attrKey).isEmpty) None else Some(node.attribute(attrKey).get.text)
  }
  
  /**
   * Returns attribute's key value or throw an exception if isn's found.
   */
  private def parseAttrStr(node: Node, attrKey: String): String = {
    val v = parseAttrStrOpt(node, attrKey)
    if (v.isEmpty) throw new java.util.NoSuchElementException(s"There is no value for ${attrKey}") else v.get
  }
  
  /**
   * Returns None if attribute's key isn't found or Some boolean value.
   * Throws an exception if value can't be parsed as boolean.
   */
  private def parseAttrBoolOpt(node: Node, attrKey: String): Option[Boolean] = {
    parseAttrStrOpt(node, attrKey) match {
        case Some("0") => Some(false)
        case Some("1") => Some(true)
        case None => None
        case _ => throw new IllegalArgumentException(s"Can't convert '${node.attribute(attrKey).get.text}' to Boolean")
      }
  }
  
  /**
   * Returns attribute's key value or throw an exception if isn't found or parsing error.
   */
  private def parseAttrBool(node: Node, attrKey: String): Boolean = {
    val v = parseAttrBoolOpt(node, attrKey)
    if (v.isEmpty) throw new java.util.NoSuchElementException(s"There is no value for ${attrKey}") else v.get
  }
  
  private def parseControls(node: Node): List[Control] = {
    (for { c <- (node \ "CONTROL") } 
      yield Control(name = parseAttrStr(c, "NAME"), controlType = parseAttrStr(c, "TYPE"))).toList
  }
  
  private def parseInConds(node: Node): List[InCond] = {
    (for { c <- (node \ "INCOND") } 
      yield InCond(andOr = parseAttrStr(c, "AND_OR"), name = parseAttrStr(c, "NAME"), oDate = parseAttrStr(c, "ODATE"))).toList
  }
  
  private def parseOutConds(node: Node): List[OutCond] = {
    (for { c <- (node \ "OUTCOND") } 
      yield OutCond(name = parseAttrStr(c, "NAME"), oDate = parseAttrStr(c, "ODATE"), sign = parseAttrStr(c, "SIGN"))).toList
  }
    
  /**
   * Parse Job attributes.
   * @param node The <JOB> node.
   */
  private def parseJob(job: Node, parentTable: Table): Job = {
    new Job(
        parentTable, 
        parseAttrStr(job, "APPLICATION"), 
        jobName = parseAttrStr(job, "JOBNAME"),
        cyclic = parseAttrBool(job, "CYCLIC"),
        cyclicType = parseAttrStr(job, "CYCLIC_TYPE"),
        cyclicIntervalSequence = parseAttrStrOpt(job, "CYCLIC_INTERVAL_SEQUENCE"),
        cyclicTimesSequence = parseAttrStrOpt(job, "CYCLIC_TIMES_SEQUENCE"),
        cyclicTolerance = parseAttrStrOpt(job, "CYCLIC_TOLERANCE"),
        cyclicInd = parseAttrStrOpt(job, "IND_CYCLIC"),
        cyclicInterval = parseAttrStrOpt(job, "INTERVAL"),
        description = parseAttrStrOpt(job, "DESCRIPTION"),
        taskType = parseAttrStr(job, "TASKTYPE"),
        controls = parseControls(job),
        inConds = parseInConds(job),
        outConds = parseOutConds(job)
    )
  }
  
  /**
   * Parse Table attributes and set of table's nested jobs.
   * @param table The <TABLE> node.
   */
  private def parseTable(table: Node): List[Job] = {
    val parentTable = Table(parseAttrStr(table, "TABLE_NAME"), parseAttrStr(table, "DATACENTER"))
    (for { j <- (table \ "JOB") } yield parseJob(j, parentTable)).toList
  }
    
  def parse(xmlDoc: String): List[Job] = {
    val xml = XML.loadString(xmlDoc)
    (for { t <- (xml \\ "DEFTABLE" \ "TABLE") } yield parseTable(t)).flatten.toList 
  }
}