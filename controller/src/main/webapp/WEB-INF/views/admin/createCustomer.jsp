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
           <spring:url value="/create-customer" var="createCustomer"/>
           <div class="row">
               <div class="col-xs-10 col-sm-10 col-md-8 col-sm-offset-1 col-md-offset-2">
                   <form:form role="form" commandName="customerForm"  action="${createCustomer}" method="POST">
                       <h2>Time planner <small><spring:message code="dashboard.createCustomer"/></small></h2>
                       <hr class="colorgraph">
                       <div class="row">
                           <div class="col-xs-10 col-sm-8 col-md-8">
                               <div class="row">
                                   <h2 class="col-md-offset-2"><small>Create user</small></h2>
                                   <div class="col-xs-12 col-sm-6 col-md-6">
                                       <div class="form-group">
                                           <spring:message code="registration.firstName" var="FN"/>
                                           <form:input path="user.firstName" type="text" name="firstName" id="firstName"
                                                       cssClass="form-control input-lg"  required="required" placeholder="${FN}" tabindex="1" />
                                       </div>
                                   </div>
                                   <div class="col-xs-12 col-sm-6 col-md-6">
                                       <div class="form-group">
                                           <spring:message code="registration.lastName" var="LN"/>
                                           <form:input path="user.lastName" type="text" name="lastName" id="lastName"
                                                       cssClass="form-control input-lg"  required="required" placeholder="${LN}" tabindex="2" />
                                       </div>
                                   </div>
                               </div>
                               <div class="form-group">
                             <form:input path="user.email" type="email" name="email" id="email"
                                         cssClass="form-control input-lg"  required="required"  placeholder="Email" tabindex="3" />
                               </div>
                               <div class="form-group">
                                   <spring:message code="registration.phone" var="phone"/>
                                   <form:input path="user.phone" type="text" name="phone" id="phone"
                                               cssClass="form-control input-lg"  required="required" placeholder="${phone}" tabindex="4" />
                               </div>
                               <div class="form-group">
                                   <form:select id="project-selector" cssClass="form-control input-lg" path="project.id">
                                    <form:options items="${projects}" itemValue="id" itemLabel="name"/>
                                </form:select>
                               </div>
                               <div class="row">
                                   <div class='col-xs-8 col-sm-8 col-md-8'>
                                       <div class="form-group">
                                           <div class='input-group date' id='datetimepicker'>
                                           <form:input path="user.birthDate" type='date' pattern="yyyy/MM/dd"
                                                       max="2001-12-31" min="1950-01-02" cssClass="form-control" id="birthDate"/>
                                               <span class="input-group-addon">
                                            <i class="fa fa-calendar" aria-hidden="true"></i>
                                       </span>
                                           </div>
                                       </div>
                                   </div>
                                   <div class = "col-xs-4 col-sm-4 col-md-4">
                               <form:select cssClass="form-control input-lg" path="user.sex">
                                   <form:option  value="1"><spring:message code="registration.male"/></form:option>
                                   <form:option  value="2"><spring:message code="registration.female"/></form:option>
                                </form:select>
                                   </div>
                               </div>
                           </div>
                           <div class="col-xs-6 col-sm-4 col-md-4">
                               <h2><small>Customer company</small></h2>
                               <div class="form-group">
                                   <spring:message code="registration.companyName" var="companyName"/>
                                   <form:input path="companyName" type="text" name="companyName" id="companyName"
                                               cssClass="form-control input-lg"  required="required" placeholder="${companyName}" tabindex="4" />
                               </div>
                               <div class="form-group">
                                   <spring:message code="dashboard.description" var="descripntion"/>
                                   <form:textarea path="description" type="text" rows="5" name="companyDescription" id="companyDescription"
                                                  cssClass="form-control input-md"   placeholder="${descripntion}" tabindex="4" />
                               </div>
                           </div>
                       </div>
                       <div class="alert alert-info">
                            <spring:message code="createWorkers.info"/>
                        </div>
                       <hr class="colorgraph">
                       <div class="row">
                           <spring:message code="login.registration" var="registB"/>
                           <div class="col-md-offset-3 col-xs-12 col-md-6">
                               <div id="error-message"  class="row">
                                   <div class="alert alert-danger">
                                       <form:errors path="message"  cssClass="error-text"/>
                                   </div>
                               </div>
                               <div class="row">
                                   <input type="submit" value="${registB}" class="btn btn-primary btn-block btn-lg" tabindex="7">
                               </div>
                           </div>
                       </div>
                   </form:form>
               </div>
           </div>
           <!-- Modal -->
       </div>
        <script type="text/javascript">
            if($('.error-text').text()==''){
                $('#error-message').css({"display":"none"});
            }else {
                $('#error-message').css({"display":"block"});
            }

            if ($('#project-selector option').length != 0) {
                $('.alert').css({"display": "none"});
                $('#submit').css({"display": "block"});
            } else {
                $('.alert').css({"display": "block"});
                $('#submit').css({"display": "none"});
            }
        </script>
    </jsp:attribute>
</t:dashboardPageLayout>