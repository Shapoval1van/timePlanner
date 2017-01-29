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

          <div class="container-fluid col-lg-offset-1 col-lg-10">
              <!-- Page Heading -->
              <div class="row">
                  <h1 class="page-header">
                          ${user.getCompany().getName()}
                      <small>Dashboard</small>
                  </h1>
              </div>
              <!-- /.row -->
              <div class="row">
                      <%--progect created--%>
                  <div class="col-lg-3 col-md-6">
                      <div class="panel panel-primary">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                      <i class="fa fa-comments fa-5x"></i>
                                  </div>
                                  <div class="col-xs-9 text-right">
                                      <div class="huge">${sprintCount}</div>
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
                      <%--current workers--%>
                  <div class="col-lg-3 col-md-6">
                      <div class="panel panel-green">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                      <i class="fa fa-address-card fa-5x"></i>
                                  </div>
                                  <div class="col-xs-9 text-right">
                                      <div class="huge">${taskCount}</div>
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
                      <%--current customers--%>
                  <div class="col-lg-3 col-md-6">
                      <div class="panel panel-yellow">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                      <i class="fa fa-shopping-cart fa-5x"></i>
                                  </div>
                                  <div class="col-xs-9 text-right">
                                      <div class="huge">${employeeCount}</div>
                                      <div><spring:message code="dashboard.employees"/></div>
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

                  <div class="col-lg-3 col-md-6">
                      <div class="panel panel-red">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                      <i class="fa fa-support fa-5x"></i>
                                  </div>
                                  <div class="col-xs-9 text-right">
                                      <div class="huge">${finishTaskCount}</div>
                                      <div><spring:message code="dashboard.finTastks"/></div>
                                  </div>
                              </div>
                          </div>
                          <a href="#">
                              <div class="panel-footer">
                                  <span class="pull-left">View Details</span>
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
                          <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i><spring:message code="dashboard.projectAbout"/></h3>
                      </div>
                      <div class="panel-body">
                          <spring:message code="dashboard.projectName"/>: ${project.getName()}
                          <br>
                          <spring:message code="dashboard.projectDescription"/>: ${project.getDescription()}
                          <br>
                          <spring:message code="dashboard.startDate"/>: ${project.getStartDate()}
                          <br>
                          <spring:message code="createProj.planFinishDate"/>: ${project.getPlanFinishDate()}
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i><spring:message code="dashboard.sprints"/></h3>
                      </div>
                      <div class="panel-body">
                              <c:choose>
                                <c:when test="${sprints != null}">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="dashboard.name"/></th>
                                             <th><spring:message code="dashboard.description"/></th>
                                             <th><spring:message code="dashboard.startDate"/></th>
                                             <th><spring:message code="dashboard.finishDate"/></th>
                                             <th><spring:message code="createSprint.planFinishDate"/></th>
                                             <th><spring:message code="dashboard.dependetSprints"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${sprints}" var="sprint">
                                                <tr>
                                                    <td>${sprint.getName()}</td>
                                                    <td>${sprint.getDescription()}</td>
                                                    <td>${sprint.getStartDate()}</td>
                                                    <td>${sprint.getFinishDate()}</td>
                                                    <td>${sprint.getPlanedFinishDate()}</td>
                                                    <td><c:if test="${sprint.getDependedOn() != null}">
                                                        <c:out value="${sprint.getDependedOn().getName()}"/><p>
                                                            </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                                </c:when>
                                <c:when test="${sprints == null}">
                                    Sorry you don't have any sprints
                                </c:when>
                             </c:choose>
                          <div class="text-right">
                              <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                          </div>
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i><spring:message code="dashboard.workers"/></h3>
                      </div>
                      <div class="panel-body">
                          <div class="list-group">
                              <c:choose>
                              <c:when test="${currentEmployees != null}">
                                      <div class="table-responsive">
                                          <table class="table table-bordered table-hover table-striped">
                                              <thead>
                                              <tr class = "font-bold">
                                                  <th><spring:message code="dashboard.name"/></th>
                                                  <th>Email</th>
                                                  <th><spring:message code="dashboard.phone"/></th>
                                              </tr>
                                              </thead>
                                              <tbody>
                                              <c:forEach items="${currentEmployees }" var="employee">
                                                    <tr>
                                                        <td>${employee.getFirstName()} ${employee.getLastName()}</td>
                                                        <td>${employee.getEmail()}</td>
                                                        <td>${employee.getPhone()}</td>
                                                    </tr>
                                                </c:forEach>
                                              </tbody>
                                          </table>
                                      </div>
                              </c:when>
                              <c:when test="${currentWorkers == null}">
                                        Sorry you don't have any workers
                              </c:when>
                              </c:choose>
                          </div>
                      </div>
                      <div class="text-right">
                          <a href="#">View All Activity <i class="fa fa-arrow-circle-right"></i></a>
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i><spring:message code="dashboard.tasks"/></h3>
                      </div>
                      <div class="panel-body">
                              <c:choose>
                                <c:when test="${tasks != null}">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="dashboard.name"/></th>
                                             <th><spring:message code="dashboard.estimate"/></th>
                                             <th><spring:message code="dashboard.startDate"/></th>
                                             <th><spring:message code="dashboard.finishDate"/></th>
                                             <th><spring:message code="createSprint.planFinishDate"/></th>
                                             <th><spring:message code="dashboard.priority"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${tasks}" var="task">
                                                <tr>
                                                    <td>${task.getName()}</td>
                                                    <td>${task.getEstimate()}</td>
                                                    <td>${task.getStartDate()}</td>
                                                    <td>${task.getFinishDate()}</td>
                                                    <td>${task.getPlanFinishDate()}</td>
                                                    <td>${task.getPriority()}</td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                                </c:when>
                                <c:when test="${tasks == null}">
                                    Sorry you don't have any task
                                </c:when>
                             </c:choose>
                          <div class="text-right">
                              <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                          </div>
                      </div>
                  </div>
              </div>
              <!-- /.row -->
          </div>
          <!-- /.row -->
      </div>
          <!-- /.row -->

      </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pm-dashboard.js"></script>
    </jsp:attribute>
</t:pageLayout>
