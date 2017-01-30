<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="title">
    <jsp:attribute name="bodyLayout">
         <header class="image-bg-fluid-height">
         </header>

         <section>
             <div class="container">
                 <div class="row">
                     <div class="col-lg-12">
                         <h1 class="section-heading">Time planner</h1>
                         <p class="lead section-lead">Это онлай-сервис, который позволяет экономить время.</p>
                         <p class="section-paragraph">Мы создали Time Planner для:
                             <ul>
                                <li>Клиента с возможностью контролировать сроки выполнения задач.</li>
                                <li>Администратора с возможностью моздавать проект и распределять роли.</li>
                                <li>Проджект менеджера с возможностью руководить проектом, подключать команду, оперировать задачами..</li>
                            </ul>
                          </p>
                     </div>
                 </div>
             </div>
         </section>

         <aside class="image-bg-fixed-height"></aside>

    <!-- Content Section -->
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="section-heading">Time planner</h1>
                        <p class="lead section-lead">Зачем нужен этот сервис</p>
                        <p class="section-paragraph">
                        <ul>
                            <li>Для делегирования.</li>
                            <li>Для навыков делегированя.</li>
                            <li>Для навыков планирования.</li>
                        </ul>
                        </p>
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container -->
        </section>
    </jsp:attribute>
</t:pageLayout>