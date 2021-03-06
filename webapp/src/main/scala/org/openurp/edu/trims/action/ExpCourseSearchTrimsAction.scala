package org.openurp.edu.trims.action

import org.beangle.data.dao.OqlBuilder
import com.sun.org.apache.bcel.internal.generic.ClassObserver
import org.openurp.base.model.Department
import org.openurp.edu.base.code.model.StdType
import org.openurp.edu.base.code.model.CourseHourType
import org.openurp.edu.base.code.model.CourseCategory
import org.openurp.edu.base.code.model.ExamMode
import org.openurp.edu.base.code.model.Education

class ExpCourseSearchTrimsAction extends CourseSearchTrimsAction {

  //	private LabService labService;

  def expCourses(): String = {
    val query = OqlBuilder.from(classOf[Department], "depart")
    query.where("depart.level=1")
    query.where("depart.teaching=true")
    query.where("depart.enabled=true")
    val departs = entityDao.search(query)
    put("departments", departs)
    val project = getProject()
    put("stdTypes", getCodes(project, classOf[StdType]))
    put("examModes", getCodes(project, classOf[ExamMode]))
    put("educationTypes", getCodes(project, classOf[Education]))
    put("categorys", getCodes(project, classOf[CourseCategory]))
    put("courseHourTypes", getCodes(project, classOf[CourseHourType]))
    forward()
  }

  //
  //	def  labs() :String={
  //		val labs = entityService.loadAll(classOf[Lab].class)
  //		put("labs", labs)
  //		return forward()
  //	}

  //	public LabService getLabService() {
  //		return labService
  //	}
  //
  //	public void setLabService(LabService labService) {
  //		this.labService = labService
  //	}

}