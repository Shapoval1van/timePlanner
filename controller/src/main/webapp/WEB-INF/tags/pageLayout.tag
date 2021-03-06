<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@tag description="Page Template" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@attribute name="title" %>
<%@attribute name="bodyLayout" fragment="true" %>

<html>
<csrf disabled="true"/>
<title>Time planner</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/full-width-pics.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/font-awesome/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/success">Time planner</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/contacts"><spring:message code="nav.contact"/></a>
                </li>
            </ul>
            <ul class="nav navbar-nav  navbar-right top-nav" >
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="nav.locate"/>
                        <span class="caret"></span></a>
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
</nav>
<jsp:invoke fragment="bodyLayout"/>
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <p>&copy; Time Planner 2017</p>
            </div>
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container -->
</footer>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app.js"></script>--%>
</body>
</html>
