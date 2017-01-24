<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@tag description="Page Template" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@attribute name="title" %>
<%@attribute name="bodyDashboardLayout" fragment="true" %>

<html>
<csrf disabled="true"/>
<title>title</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/full-width-pics.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/sb-admin.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/plugins/morris.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Time planner</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#"><spring:message code="nav.about"/></a>
                </li>
                <li>
                    <a href="#"><spring:message code="nav.contact"/></a>
                </li>
            </ul>
            <ul class="nav navbar-nav  navbar-right top-nav" >
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="nav.locate"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="?lang=en">ENG</a></li>
                        <li><a href="?lang=ru">RU</a></li>
                    </ul>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li>
                        <a href="/registration"><spring:message code="nav.singUp"/></a>
                    </li>
                </sec:authorize>
                <li>
                    <sec:authorize access="isAnonymous()">
                        <a href="/login"><spring:message code="nav.login"/></a>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <a href="/j_spring_security_logout"><spring:message code="nav.exit"/></a>
                    </sec:authorize>
                </li>
            </ul>
        </div>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div>
                <ul class="nav navbar-nav side-nav navbar-left">
                    <li>
                        <c:choose>
                            <c:when test="${userRole.ordinal() == 0}">
                                <a href="/dashboard-adm"><i class="fa fa-fw fa-dashboard"></i>
                                    Dashboard
                                </a>
                            </c:when>
                            <c:when test="${userRole.ordinal() == 1}">
                                <a href="/dashboard-pm"><i class="fa fa-fw fa-dashboard"></i>
                                    Dashboard
                                </a>
                            </c:when>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${userRole.ordinal() == 0}">
                                <a href="/create-project"><i class="fa fa-fw fa-product-hunt" aria-hidden="true"></i>
                                    <spring:message code="dashboard.createProj"/>
                                </a>
                            </c:when>
                            <c:when test="${userRole.ordinal() == 1}">
                                <a href="/create-sprint/for-${currentProjectId}id"><i class="fa fa-fw fa-clipboard" aria-hidden="true"></i>
                                    <spring:message code="dashboard.creteSprint"/>
                                </a>
                            </c:when>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${userRole.ordinal() == 0}">
                                <a href="/create-worker"><i class="fa fa-fw fa-male" aria-hidden="true"></i>
                                    <spring:message code = "createWorker.createWorker"/>
                                </a>
                            </c:when>
                            <c:when test="${userRole.ordinal() == 1}">
                                <a href="/create-task"><i class="fa fa-fw fa-tasks" aria-hidden="true"></i>
                                    <spring:message code="dashboard.createTask"/>
                                </a>
                            </c:when>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${userRole.ordinal() == 0}">
                                <a href="/create-customer"><i class="fa fa-fw fa-bar-chart-o"></i>
                                    <spring:message code = "dashboard.createCustomer"/>
                                </a>
                            </c:when>
                            <c:when test="${userRole.ordinal() == 1}">
                                <a href="charts.html"><i class="fa fa-fw fa-bar-chart-o"></i>
                                    <spring:message code="dashboard.creteSprint"/>
                                </a>
                            </c:when>
                        </c:choose>
                    </li>
                    <li>
                        <a href="bootstrap-elements.html"><i class="fa fa-fw fa-desktop"></i> Add customer</a>
                    </li>
                </ul>
            </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div id="page-wrapper">

        <div class="container-fluid">
            <jsp:invoke fragment="bodyDashboardLayout"/>
        </div>
    </div>
</div>
<!-- /#wrapper -->
</html>
