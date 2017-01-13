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
                          ${company.getName()}
                      <small>Dashboard</small>
                  </h1>
                  <ol class="breadcrumb">
                      <li class="active">
                          <i class="fa fa-user" aria-hidden="true"></i> ${userName}
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
                                      <div>Created Project</div>
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
                                      <div>Current workers</div>
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
                                      <div>Current Customers</div>
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
                  <div class="col-lg-3 col-md-6">
                      <div class="panel panel-red">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                      <i class="fa fa-support fa-5x"></i>
                                  </div>
                                  <div class="col-xs-9 text-right">
                                      <div class="huge">${finishProjectsCount}</div>
                                      <div>Finished projects</div>
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
                          <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Company About</h3>
                      </div>
                      <div class="panel-body">
                          <div id="morris-area-chart">
                              Company description: ${company.getDescription()}
                              <br>
                              Company date creation: ${company.getDateCreation()}
                          </div>
                      </div>
                  </div>
              </div>
              <!-- /.row -->

              <div class="row">
                  <div class="panel panel-default">
                      <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-long-arrow-right fa-fw"></i> Project</h3>
                      </div>
                      <div class="panel-body">
                          <div id="morris-donut-chart">
                              <c:choose>
                                <c:when test="${projects != null}">
                                 <div class="table-responsive">
                                     <table class="table table-bordered table-hover table-striped">
                                         <thead>
                                         <tr>
                                             <th>Name</th>
                                             <th>Description</th>
                                             <th>Start</th>
                                             <th>Finish</th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="${projects}" var="project">
                                                <tr>
                                                    <td>}${project.getName()}</td>
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
                                    Sorry you don't have any projects
                                </c:when>
                             </c:choose>
                          </div>
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
                          <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i> Tasks Panel</h3>
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
                                    Sorry you don't have any workers
                                </c:when>
                          </c:choose>
                      </div>
                      <div class="text-right">
                          <a href="#">View All Activity <i class="fa fa-arrow-circle-right"></i></a>
                      </div>
                  </div>
              </div>
          </div>
          <!-- /.row -->

      </div>
          <!-- /.row -->

      </div>



    </jsp:attribute>
</t:dashboardPageLayout>
