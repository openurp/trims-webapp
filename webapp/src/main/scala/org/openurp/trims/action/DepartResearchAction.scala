package org.openurp.trims.action

import org.beangle.data.jpa.dao.SqlBuilder
import scala.collection.mutable.ListBuffer

class DepartResearchAction extends AbsEamsAction {

  def index(): String = {
    forward()
  }

  def research(): String = {
    val thesises = getThesis
    val literatures = getLiterature
//    val a = thesises(0).
//    val departmentIds = if(thesises(0).l)
    val departsIdSet = new collection.mutable.HashSet[Integer]
    addDepartId(departsIdSet, thesises)
    addDepartId(departsIdSet, literatures)
    val departsIds = departsIdSet.toList.sortBy(i => i)
    val names = new ListBuffer[String]
    val map = getDepartmentMap
    departsIds.foreach(id => names += map(id.toString()))
    val values = new ListBuffer[ListBuffer[Any]]
    values += getValues(departsIds, thesises)
    values += getValues(departsIds, literatures)
    put("names", names)
    put("values", values)
    forward()
  }
  
  private def getValues(ids:List[Integer], datas:Seq[Any]):ListBuffer[Any] = {
    val list = new ListBuffer[Any]
    ids.foreach(id => {
      val data = datas.find(d => {
        val data = d.asInstanceOf[Array[Any]]
        data(0).equals(id)
      })
      if(data.isDefined){
        list += data.get.asInstanceOf[Array[Any]](1)
      }else{
        list += 0
      }
    })
    list
  }
  
  private def addDepartId(ids:collection.mutable.HashSet[Integer], datas:Seq[Any]){
    datas.foreach(data => {
      ids += data.asInstanceOf[Array[Any]](0).asInstanceOf[Integer]
    })
  }

  /**论文*/
  private def getThesis():Seq[Any] = {
    val sql = """select department_id,count(*)
        from research.thesis_harvests
        group by department_id"""
    val query = SqlBuilder.sql(sql)
    entityDao.search(query)
  }

  /**专著*/
  private def getLiterature():Seq[Any] = {
    val sql = """select department_id,count(*)
        from research.literatures
        group by department_id"""
    val query = SqlBuilder.sql(sql)
    entityDao.search(query)
  }

}