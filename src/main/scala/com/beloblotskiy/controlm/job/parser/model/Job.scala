package com.beloblotskiy.controlm.job.parser.model

/**
 * @author abelablotski
 */
case class Job (
    table: Table, 
    application: String, 
    jobName: String,
    cyclic: Boolean,
    cyclicType: String,
    cyclicIntervalSequence: Option[String],
    cyclicTimesSequence: Option[String],
    cyclicTolerance: Option[String],
    cyclicInd: Option[String],
    cyclicInterval: Option[String],
    description: Option[String],
    taskType: String,
    controls: List[Control],
    inConds: List[InCond],
    outConds: List[OutCond]) {
  
}