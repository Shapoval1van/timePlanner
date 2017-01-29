<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:dashboardPageLayout title="title">
    <jsp:attribute name="bodyDashboardLayout">
      <div id="page-wrapper">

          <div class="container-fluid">

              <!-- Page Heading -->
              <div class="row">
                  <h1 class="page-header">
                          ${user.getCompany().getName()}
                      <small>Dashboard</small>
                  </h1>
                  <ol class="breadcrumb">
                      <li class="active">
                          <i class="fa fa-user" aria-hidden="true"></i> ${user.getFirstName()} ${user.getLastName()}
                      </li>
                  </ol>
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
                                      <div class="huge">${projectCount}</div>
                                      <div><spring:message code="dashboard.createdProj"/></div>
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
                                      <div class="huge">${currentWorkersCount}</div>
                                      <div><spring:message code="dashboard.currentWorkers"/></div>
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
                                      <div class="huge">${currentCustomersCount}</div>
                                      <div><spring:message code="dashboard.curretCustomers"/></div>
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
                                      <div class="huge">${finishProjectsCount}</div>
                                      <div><spring:message code="dashboard.finishedProject"/></div>
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
                              <spring:message code="dashboard.companyDateCreation"/> : ${company.getDateCreation()}
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i><spring:message code="dashboard.project"/></h3>
                      </div>
                      <div class="panel-body">
                              <c:choose>
                                <c:when test="${projects != null}">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="dashboard.name"/></th>
                                             <th><spring:message code="dashboard.description"/></th>
                                             <th><spring:message code="dashboard.startDate"/></th>
                                             <th><spring:message code="dashboard.finishDate"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${projects}" var="project">
                                                <tr>
                                                    <td>${project.getName()}</td>
                                                    <td>${project.getDescription()}</td>
                                                    <td>${project.getStartDate()}</td>
                                                    <td>${project.getFinishDate()}</td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                                </c:when>
                                <c:when test="${projects == null}">
                                    <spring:message code="dashboard.projectEmpty"/>
                                </c:when>
                             </c:choose>
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
                          <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i><spring:message code="dashboard.workers"/></h3>
                      </div>
                      <div class="panel-body">
                          <div class="list-group">
                              <c:choose>
                              <c:when test="${currentWorkers != null}">
                              <c:forEach items="${currentWorkers}" var="workers">
                                               <a href="#" class="list-group-item">
                                                   <span class="badge">${workers.getRole()}</span>
                                                   <i class="fa fa-fw fa-calendar"></i>
                                                   ${workers.getFirstName()} ${workers.getLastName()}
                                               </a>
                                        </c:forEach>
                          </div>
                          </c:when>
                          <c:when test="${currentWorkers == null}">
                                    <spring:message code="dashboard.workersEmpty"/>
                                </c:when>
                          </c:choose>
                      </div>
                      <div class="text-right">
                          <a href="#"><spring:message code="dashboard.viewDetails"/><i class="fa fa-arrow-circle-right"></i></a>
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i><spring:message code="dashboard.customers"/></h3>
                      </div>
                      <div class="panel-body">
                              <c:choose>
                                <c:when test="${currentCustomers != null}">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped">
                                         <thead>
                                         <tr class = "font-bold">
                                             <th><spring:message code="dashboard.name"/></th>
                                             <th><spring:message code="dashboard.company"/></th>
                                             <th><spring:message code="dashboard.email"/></th>
                                             <th><spring:message code="dashboard.phone"/></th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${currentCustomers}" var="customers">
                                                <tr>
                                                    <td>${customers.getUser().getFirstName()} ${customers.getUser().getLastName()}</td>
                                                    <td>${customers.getCompanyName()}</td>
                                                    <td>${customers.getUser().getEmail()}</td>
                                                    <td>${customers.getUser().getPhone()}</td>
                                                </tr>
                                            </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                                </c:when>
                                <c:when test="${currentCustomers == null}">
                                    <spring:message code="dashboard.customersEmpty"/>
                                </c:when>
                             </c:choose>
                          <div class="text-right">
                              <a href="#"><spring:message code="dashboard.viewDetails"/><i class="fa fa-arrow-circle-right"></i></a>
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

    </jsp:attribute>
</t:dashboardPageLayout>
