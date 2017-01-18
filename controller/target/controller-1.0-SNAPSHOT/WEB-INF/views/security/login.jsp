<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="login">
    <jsp:attribute name="bodyLayout">
        <c:set var="logUrl" value="/j_spring_security_check"/>
        <div class="container" style="padding-top:20px">
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Time planner</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 ">
                                    <form name="loginForm" action="${logUrl}" method="POST" id="login">
                                        <div class="col-xs-12 col-sm-12 col-md-12 ">
                                            <br>
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-user fa-2x" aria-hidden="true"></i>
                                                </span>
                                                <input type="text" name="j_username" class="form-control input-lg"
                                                       placeholder="username" required autofocus/>
                                            </div>
                                            <br/>
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-lock fa-2x" aria-hidden="true"></i>
                                                </span>
                                                <input type="password" name="j_password" class="form-control input-lg"
                                                       placeholder="password" required/>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12">
                                            <a href="/registration">Re</a>
                                            <%--<c:if test="${error == true}">--%>

                                                <%--<c:out value="by"/>--%>
                                            <%--</c:if>--%>
                                            <hr class="colorgraph">
                                            <div class="row">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                                <div class="col-xs-offset-3 col-sm-offset-3  col-md-offset-3 col-xs-6 col-sm-6 col-md-6">
                                                    <%--<spring:message code="login.button" var="button"/>--%>
                                                    <input type="submit" value="butt"
                                                           class="btn btn-primary btn-block btn-lg btn-success">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:pageLayout>