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
      <div id="page-wrapper">

          <div class="container-fluid">
              <div class="col-lg-offset-1 col-lg-11">
                  <div class="row">
                      <h1 class="page-header">
                              ${user.getCompany().getName()}
                          <small>Dashboard</small>
                      </h1>
                  </div>
                  <!-- /.row -->
                  <ol class="breadcrumb">
                      <li class="active">
                          <i class="fa fa-user" aria-hidden="true"></i> ${user.getFullName()}
                      </li>
                  </ol>
                  <div class="row">
                          <%--new task--%>
                      <div class="col-lg-4 col-md-6">
                          <div class="panel panel-primary">
                              <div class="panel-heading">
                                  <div class="row">
                                      <div class="col-xs-3">
                                          <i class="fa fa-comments fa-5x"></i>
                                      </div>
                                      <div class="col-xs-9 text-right">
                                          <div class="huge" id="tasksNewCount">${tasksNewCount}</div>
                                          <div><spring:message code="dashboard.createdSprints"/></div>
                                      </div>
                                  </div>
                              </div>
                              <a href="#">
                                  <div class="panel-footer">
                                      <span class="pull-left"><spring:message code="dashboard.viewDetails"/></span>
                                      <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                      <div class="clearfix"></div>
                                  </div>
                              </a>
                          </div>
                      </div>
                          <%--task in work--%>
                      <div class="col-lg-4 col-md-6">
                          <div class="panel panel-green">
                              <div class="panel-heading">
                                  <div class="row">
                                      <div class="col-xs-3">
                                          <i class="fa fa-address-card fa-5x"></i>
                                      </div>
                                      <div class="col-xs-9 text-right">
                                          <div class="huge" id="tasksInWorksCount">${tasksInWorksCount}</div>
                                          <div><spring:message code="dashboard.tasks"/></div>
                                      </div>
                                  </div>
                              </div>
                              <a href="#">
                                  <div class="panel-footer">
                                      <span class="pull-left"><spring:message code="dashboard.viewDetails"/></span>
                                      <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                      <div class="clearfix"></div>
                                  </div>
                              </a>
                          </div>
                      </div>
                          <%--finished--%>
                      <div class="col-lg-4 col-md-6">
                          <div class="panel panel-red">
                              <div class="panel-heading">
                                  <div class="row">
                                      <div class="col-xs-3">
                                          <i class="fa fa-support fa-5x"></i>
                                      </div>
                                      <div class="col-xs-9 text-right">
                                          <div class="huge" id="tasksFinishedCount">${tasksFinishedCount}</div>
                                          <div><spring:message code="dashboard.finTastks"/></div>
                                      </div>
                                  </div>
                              </div>
                              <a href="#">
                                  <div class="panel-footer">
                                      <span class="pull-left"><spring:message code="dashboard.viewDetails"/></span>
                                      <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                      <div class="clearfix"></div>
                                  </div>
                              </a>
                          </div>
                      </div>
                  </div>
                  <!-- /.row -->
                  <div class="row">
                      <div class="panel panel-default">
                          <div class="panel-heading">
                              <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i><spring:message code="dashboard.companyAbout"/></h3>
                          </div>
                          <div class="panel-body">
                              <spring:message code="dashboard.companyDescription"/>: ${company.getDescription()}
                              <br>
                              <spring:message code="dashboard.companyDateCreation"/>: ${company.getDateCreation()}
                          </div>
                      </div>
                  </div>
                  <!-- /.row -->
                  <div class="row">
                      <div class="panel panel-default">
                          <div class="panel-heading">
                              <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i>New tasks</h3>
                          </div>
                          <div class="panel-body">
                              <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped" id = "table-created">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="ceateTask.taskName"/></th>
                                             <th><spring:message code="dashboard.description"/></th>
                                             <th><spring:message code="createSprint.planFinishDate"/></th>
                                             <th><spring:message code="dashboard.priority"/></th>
                                             <th><spring:message code="dashboard.estimate"/></th>
                                             <th><spring:message code="confirmAppointment"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${tasksNew}" var="task">
                                                <tr>
                                                    <td><a href="/show-task/${task.getId()}id">${task.getName()}</a></td>
                                                    <td>${task.getDescription()}</td>
                                                    <td>${task.getPlanFinishDate()}</td>
                                                    <td>${task.getPriority()}</td>
                                                    <td>${task.getEstimate()}</td>
                                                    <td style="text-align: center ">
                                                        <button class="btn btn-success status-button"  data-id="${task.getId()}">
                                                            <i class="fa fa-check-circle " aria-hidden="true"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                              <div class="text-right">
                                  <a href="#"><spring:message code="dashboard.viewDetails"/><i class="fa fa-arrow-circle-right"></i></a>
                              </div>
                          </div>
                      </div>
                  </div>
                  <!-- /.row -->
                  <div class="row">
                      <div class="panel panel-default">
                          <div class="panel-heading">
                              <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i>Task in work</h3>
                          </div>
                          <div class="panel-body">
                              <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped" id = "table-started">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="ceateTask.taskName"/></th>
                                             <th><spring:message code="dashboard.description"/></th>
                                             <th><spring:message code="createSprint.planFinishDate"/></th>
                                             <th><spring:message code="dashboard.priority"/></th>
                                             <th><spring:message code="dashboard.estimate"/></th>
                                             <th><spring:message code="closeTask"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${tasksInWork}" var="task1">
                                                <tr>
                                                    <td><a href="/show-task/${task1.getId()}id">${task1.getName()}</a></td>
                                                    <td>${task1.getDescription()}</td>
                                                    <td>${task1.getPlanFinishDate()}</td>
                                                    <td>${task1.getPriority()}</td>
                                                    <td>${task1.getEstimate()}</td>
                                                    <td style="text-align: center">
                                                        <button class="btn btn-danger status-button"  data-id="${task1.getId()}">
                                                            <i class="fa fa-times " aria-hidden="true"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                         </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                              <div class="text-right">
                                  <a href="#"><spring:message code="dashboard.viewDetails"/><i class="fa fa-arrow-circle-right"></i></a>
                              </div>
                          </div>
                      </div>
                  </div>
                  <!-- /.row -->
                  <div class="row">
                      <div class="panel panel-default">
                          <div class="panel-heading">
                              <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i>Task Finished</h3>
                          </div>
                          <div class="panel-body">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped" id = "table-finished">
                                         <thead >
                                         <tr class = "font-bold">
                                             <th><spring:message code="ceateTask.taskName"/></th>
                                             <th><spring:message code="dashboard.description"/></th>
                                             <th><spring:message code="createSprint.planFinishDate"/></th>
                                             <th><spring:message code="dashboard.priority"/></th>
                                             <th><spring:message code="dashboard.estimate"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${tasksFinished}" var="task2">
                                                <tr>
                                                    <td><a href="/show-task/${task2.getId()}id">${task2.getName()}</a></td>
                                                    <td>${task2.getDescription()}</td>
                                                    <td>${task2.getPlanFinishDate()}</td>
                                                    <td>${task2.getPriority()}</td>
                                                    <td>${task2.getEstimate()}</td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                              <div class="text-right">
                                  <a href="#"><spring:message code="dashboard.viewDetails"/><i class="fa fa-arrow-circle-right"></i></a>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <!-- /.row -->
      </div>
          <!-- /.row -->
      <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/emp-dashboard.js"></script>
      </div>
    </jsp:attribute>
</t:pageLayout>