<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="title">
    <jsp:attribute name="bodyLayout">
        <div class="col-xs-10 col-lg-offset-1">
            <h2>Time planner
                <small><spring:message code="dashboard.tasks"/></small>
            </h2>
            <hr class="colorgraph">
            <div class="col-xs-6 col-lg-offset-4" >
                <p>
                    <span class="font-bold"><spring:message code="ceateTask.taskName"/></span>:${task.getName()}
                    <c:if test="${warningFlag==true}">
                        <spring:message code="deadlineWarn" var="deadlineWarn"/>
                        <i class="fa fa-exclamation-circle fa-2x" style="float: right" title="${deadlineWarn}" aria-hidden="true"></i>
                    </c:if>
                    <c:if test="${errorFlag==true}">
                        <spring:message code="deadlineError" var="deadlineError"/>
                        <i class="fa fa-exclamation-triangle fa-2x" style="float: right" title="${deadlineError}" aria-hidden="true"></i>
                    </c:if>
                </p>
                <p><span class="font-bold"><spring:message code="createSprint.planFinishDate"/>: </span>${task.getPlanFinishDate()}</p>
                <p>
                    <c:choose>
                    <c:when test="${task.getTaskStatus().ordinal()==0}">
                    </c:when>
                    <c:when test="${task.getTaskStatus().ordinal()==1}">
                        <span class="font-bold"><spring:message code="dashboard.startDate"/>: </span>${task.getStartDate()}
                    </c:when>
                    <c:when test="${task.getTaskStatus().ordinal()==2}">
                        <span class="font-bold"><spring:message code="dashboard.startDate"/>: </span>${task.getStartDate()}
                        <br/>
                        <span class="font-bold"><spring:message code="dashboard.finishDate"/>: </span>${task.getFinishDate()}
                    </c:when>
                    </c:choose>
                </p>
                <p>
                    <span class="font-bold"><spring:message code="createTask.estimate"/> </span>:${task.getEstimate()}
                </p>
                <p>
                    <span class="font-bold"><spring:message code="dashboard.priority"/>: </span>${task.getPriority()}
                </p>
                <p>
                    <span class="font-bold"><spring:message code="createSprint.sprintName"/>: </span>${task.getSprint().getName()}
                </p>
                <p>
                    <span class="font-bold"><spring:message code="assignedUser"/></span>:
                    <c:forEach items="${task.getUsers()}" var="user">
                        ${user.getFullName()}
                    </c:forEach>
                </p>
                <p>
                    <span class="font-bold"><spring:message code="taskDepended"/>: </span>
                    <c:forEach items="${task.getTasks()}" var="task">
                        ${task.getName()}
                    </c:forEach>
                </p>
                <a class="btn btn-primary btn-block btn-lg" href="/dashboard-emp"><spring:message code="backToDashboard"/></a>
        </div>
    </jsp:attribute>
</t:pageLayout>