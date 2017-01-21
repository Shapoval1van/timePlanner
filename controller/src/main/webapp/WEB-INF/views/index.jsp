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
                         <h1 class="section-heading">Section Heading</h1>
                         <p class="lead section-lead">Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                         <p class="section-paragraph">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid,
                             suscipit, rerum quos facilis repellat architecto commodi officia atque nemo facere eum non
                             illo voluptatem quae delectus odit vel itaque amet.</p>
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
                        <h1 class="section-heading">Section Heading</h1>
                        <p class="lead section-lead">Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                        <p class="section-paragraph">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid,
                            suscipit, rerum quos facilis repellat architecto commodi officia atque nemo facere eum non
                            illo
                            voluptatem quae delectus odit vel itaque amet.</p>
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container -->
        </section>
    </jsp:attribute>
</t:pageLayout>