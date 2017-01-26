<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:dashboardPageLayout title="title">
    <jsp:attribute name="bodyDashboardLayout">
        <div class="row">
            <div class="col-xs-12 col-sm-10 col-md-8 col-sm-offset-1 col-md-offset-2">
                <spring:url value="/create-project" var="createProject"/>
                <form:form role="form" commandName="projectForm" action="${createProject}" method="POST">
                    <h2>Time planner
                        <small><spring:message code="dashboard.createdProj"/></small>
                    </h2>
                    <hr class="colorgraph">
                    <div class="row">
                        <div class="col-xs-1 col-sm-4 col-md-4">
                            <p class="text-info"><spring:message code="createProj.projectName"/>*</p>
                        </div>
                        <div class="col-xs-12 col-sm-8 col-md-8">
                            <spring:message code="dashboard.project" var="project"/>
                            <form:input path="name" type="text" name="projectName" id="projectName"
                                        cssClass="form-control input-lg" required="required"
                                        placeholder="${project}" tabindex="1"/>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-1 col-sm-4 col-md-4">
                            <p class="text-info"><spring:message code="createProj.planFinishDate"/>*</p>
                        </div>
                        <div class="col-xs-12 col-sm-8 col-md-8">
                            <form:input path="planFinishDate" type='date' pattern="yyyy/MM/dd"
                                        min="2016-01-02" cssClass="form-control" id="planFinishDate" tabindex="2"/>
                        </div>
                    </div>
                    <br>
                     <div class="row">
                         <div class="col-xs-1 col-sm-4 col-md-4">
                             <p class="text-info"><spring:message code="dashboard.description"/></p>
                         </div>
                         <div class="col-xs-12 col-sm-8 col-md-8">
                             <form:textarea path="description" rows="6" type="text" name="decsription" id="decsription"
                                         cssClass="form-control input-lg" tabindex="3"/>
                         </div>
                     </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-1 col-sm-4 col-md-4">
                            <p class="text-info"><spring:message code="projectManager"/>*</p>
                        </div>
                        <div class="col-xs-12 col-sm-8 col-md-8">
                            <form:select path="projectManager.id" cssClass="input-lg" itemValue="id"
                                         itemLabel="fullName" tabindex="4" items="${currentPM}"/>
                        </div>
                    </div>
                     <div class="row">
                         <hr class="colorgraph">
                         <spring:message code="createProj.create" var="create"/>
                         <div class="col-md-offset-4 col-xs-10 col-sm-6 col-md-6">
                             <div class="row">
                                 <div class="alert alert-danger">
                                     <spring:message code="createProj.error"/>
                                 </div>
                             </div>
                             <input id="submit" type="submit" value="${create}" class="btn btn-primary btn-block btn-lg" tabindex="5">
                         </div>
                     </div>
                </form:form>
            </div>
        </div>
        <script type="text/javascript">
            if ($('option').length != 0) {
                $('.alert').css({"display": "none"});
                $('#submit').css({"display": "block"});
            } else {
                $('.alert').css({"display": "block"});
                $('#submit').css({"display": "none"});
            }
        </script>
    </jsp:attribute>
</t:dashboardPageLayout>
