<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="login.registration" var="reg"/>
<t:dashboardPageLayout title="title">
    <jsp:attribute name="bodyDashboardLayout">
        <div class="container" style="width: 100%">
            <div class="row">
                <h2>Time planner
                    <small><spring:message code="dashboard.creteSprint"/></small>
                </h2>
                <hr class="colorgraph">
                <div class="col-xs-12 col-lg-7 col-md-7">
                    <form:form role="form" commandName="sprintForm"  action="/create-sprint" method="POST">
                        <div class = "row">
                            <div class="form-group">
                                <label for="name"><spring:message code="createSprint.sprintName"/>:</label>
                                <spring:message code="dashboard.name" var="sprintName"/>
                                <form:input id="name" path="name" cssClass="form-control input-lg"  required="required" placeholder="${sprintName}" tabindex="1"/>
                            </div>
                            <div class="form-group">
                                <label for="description"><spring:message code="dashboard.description"/>:</label>
                                <spring:message code="dashboard.description" var="description"/>
                                <form:textarea id="description" path="description" rows="6"  cssClass="form-control input-lg" placeholder="${description}" tabindex="2"/>
                            </div>

                            <label for="planedFinishDate"><spring:message code="createSprint.planFinishDate"/>:</label>
                            <div class='form-group input-group date' id='datetimepicker'>
                                <form:input path="planedFinishDate" type='date' pattern="yyyy/MM/dd" id="planedFinishDate"
                                            min="${previousSpring.getPlanedFinishDate()}" cssClass="form-control" required="required" tabindex="3"/>
                                <span class="input-group-addon">
                                    <i class="fa fa-calendar" aria-hidden="true"></i>
                                </span>
                            </div>

                            <div class="form-group">
                                <label for="description"><spring:message code="dashboard.priority"/>:</label>
                                <spring:message code="dashboard.description" var="description"/>
                                <form:textarea id="description" path="description" rows="6"  cssClass="form-control input-lg" placeholder="${description}" tabindex="2"/>
                            </div>
                            <form:hidden path="project.id" value ="${currentProjectId}"/>

                            <c:if test="${previousSpring!=null}">
                                <form:hidden path="dependedOn.id" value="${previousSpring.getId()}"/>
                            </c:if>


                            <div class="form-group">
                                <spring:message code="createProj.create" var = "create"/>
                                <input type="submit" value="${create}" class="btn btn-primary btn-block btn-lg"
                                       tabindex="3">
                            </div>
                        </div>
                    </form:form>
                </div>
                <div class="col-xs-12 col-lg-5 col-md-5">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h2 class="panel-title"><i class="fa fa-sitemap" aria-hidden="true"></i> <spring:message code="createSprint.prevSprint"/></h2>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-lg-7 col-md-7">
                                <span class="font-bold"><spring:message code="createSprint.sprintName"/>:</span>
                            </div>
                            <div class="col-xs-12 col-lg-5 col-md-5">
                                <c:if test="${previousSpring!=null}">
                                    ${previousSpring.getName()}
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-lg-7 col-md-7">
                                <span class="font-bold"><spring:message code="createSprint.planFinishDate"/>:</span>
                            </div>
                            <div class="col-xs-12 col-lg-5 col-md-5">
                                <c:if test="${previousSpring!=null}">
                                    ${previousSpring.getPlanedFinishDate()}
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-lg-7 col-md-7">
                                <span class="font-bold"><spring:message code="createSprintFinished"/>:</span>
                            </div>
                            <div class="col-xs-12 col-lg-5 col-md-5">
                                <c:if test="${previousSpring!=null}">
                                    <c:if test="${previousSpring.isFinished()==true}">
                                        <i class="fa fa-check fa-2x" aria-hidden="true"></i>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>
                        <span class="font-bold"><spring:message code="dashboard.description"/>:</span>
                        <c:if test="${previousSpring!=null}">
                            <pre style="text-align: left">${previousSpring.getDescription()}</pre>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:dashboardPageLayout>