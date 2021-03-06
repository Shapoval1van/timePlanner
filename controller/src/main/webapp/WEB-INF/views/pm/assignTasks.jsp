<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:dashboardPageLayout title="title">
    <jsp:attribute name="bodyDashboardLayout">
        <div class="col-lg-offset-1 col-lg-10">
            <h2>Time planner
                <small><spring:message code="dashbord.assignTasks"/></small>
            </h2>
            <hr class="colorgraph">
            <c:forEach items="${taskWithDetailsSet}" var="task">
                <div class="col-lg-6 col-md-6 col-xs-12 right-panel">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">${task.getName()}</h3>
                        </div>
                        <div class="panel-body">
                            <p>
                                <span class="font-bold"><spring:message code="createSprint.sprintName"/></span>:${task.getSprint().getName()}
                                <button class="btn btn-success"  data-project="${currentProjectId}" data-id="${task.getId()}" style="float: right"><i class="fa fa-check" aria-hidden="true"></i></button>
                            </p>
                            <p>
                                <span class="font-bold"><spring:message code="createSprint.planFinishDate"/></span>:${task.getPlanFinishDate()}
                            </p>
                            <span class="font-bold"><spring:message code="taskDepended"/>: </span>
                            <pre style="text-align: left"> <c:forEach items="${task.getTasks()}" var="dependedTask">${dependedTask.getName()} </c:forEach></pre>
                            <span class="font-bold"><spring:message code="dashboard.description"/>:</span>
                            <pre class="pre-describe" style="text-align: left">${task.getDescription()} </pre>
                            <label for = "employee-selector-id${task.getId()}"><spring:message code="assigneUsers"/></label>
                            <select  class="employee-selector input-lg" id="employee-selector-id${task.getId()}"
                                         multiple="multiple" style="width: 100%">
                                <c:forEach items="${currentEmployees}" var="employee">
                                    <option value="${employee.getId()}">${employee.getFullName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assignTask.js"></script>
    </jsp:attribute>
</t:dashboardPageLayout>
