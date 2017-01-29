<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="login.registration" var="reg"/>
<t:dashboardPageLayout title="title">
    <jsp:attribute name="bodyDashboardLayout">
       <div class="container" style="width: 100%">
           <h2>Time planner
               <small><spring:message code="dashboard.createTask"/></small>
           </h2>
           <hr class="colorgraph">
           <form:form role="form" commandName="taskForm" action="/create-task" method="POST">
                <div class="col-lg-offset-2 col-md-offset-2 col-xs-offset-0 col-xs-12 col-lg-8 col-md-8">
                    <div class="row">
                        <div class="form-group">
                            <label for="name"><spring:message code="ceateTask.taskName"/>:</label>
                            <spring:message code="dashboard.name" var="sprintName"/>
                            <form:input id="name" path="name" cssClass="form-control input-lg" required="required"
                                        placeholder="${sprintName}" tabindex="1"/>
                        </div>
                        <div class="form-group">
                            <label for="description"><spring:message code="createTask.taskDescription"/>:</label>
                            <spring:message code="dashboard.description" var="description"/>
                            <form:textarea id="description" path="description" rows="5" cssClass="form-control input-lg"
                                           placeholder="${description}" tabindex="2"/>
                        </div>

                        <label for="planedFinishDate"><spring:message code="createSprint.planFinishDate"/>:</label>
                        <div class='form-group input-group date' id='datetimepicker'>
                                <form:input path="planFinishDate" type='date' pattern="yyyy/MM/dd" id="planedFinishDate"
                                            cssClass="form-control" required="required" tabindex="3"/>
                            <span class="input-group-addon">
                                    <i class="fa fa-calendar" aria-hidden="true"></i>
                                </span>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-6 col-lg-8 col-md-8">
                                <label for="estimate"><spring:message code="createTask.estimate"/>:</label>
                                <form:input path="estimate" id="estimate" type='text' cssClass="form-control"
                                            pattern= "[0-9]" required="required" tabindex="4"/>
                            </div>
                            <div class="col-xs-6 col-lg-4 col-md-4">
                                <label for="priority"><spring:message code="dashboard.priority"/>:</label>
                                <form:select path="priority" type="text" name="role" id="priority"
                                             cssClass="form-control input" tabindex="5">
                                        <form:option value="LOW">LOW</form:option>
                                        <form:option cssClass="btn-info" value="MEDIUM">MEDIUM</form:option>
                                        <form:option cssClass="btn-warning" value="HIGH">HIGH</form:option>
                                        <form:option cssClass="btn-danger" value="CRITICAL">CRITICAL</form:option>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for = "sprint-selector"><spring:message code="createTask.chooseSprint"/></label>
                            <form:select path="sprint.id" id="sprint-selector" cssClass="input-lg" cssStyle="width: 100%">
                                <c:forEach items="${sprintList}" var="sprint">
                                    <option data-date="${sprint.getPlanedFinishDate()}" value="${sprint.getId()}">${sprint.getName()}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for = "task-selector"><spring:message code="createTask.dependetTask"/></label>
                            <form:select path="tasks" cssClass="task-selector input-lg" id="task-selector"
                                         multiple="multiple" cssStyle="width: 100%">
                                <c:forEach items="${taskList}" var="task">
                                    <option value="${task.getId()}">${task.getName()}</option>
                                </c:forEach>
                            </form:select>
                        </div>

                        <div class="row">
                            <div class="alert alert-danger">
                                <spring:message code="createTask.error"/>
                            </div>
                        </div>
                        <spring:message code="createProj.create" var="create"/>
                        <input id="submit" type="submit" value="${create}" class="btn btn-primary btn-block btn-lg"
                               tabindex="5">
                    </div>
                </div>
           </form:form>
       </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/create-task.js"></script>
    </jsp:attribute>
</t:dashboardPageLayout>