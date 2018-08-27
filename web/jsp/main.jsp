<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local" />

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
  <h2><fmt:message key="${not empty title ? title : 'product.field.image.title'}" /></h2>
  <hr>
</div>
<div class="row">
  <div class="col-6">

  </div>
  <div class="col-6">
    <h4><fmt:message key="title.main.field.last"/></h4>
    <a class="btn btn-primary" href="<c:url value="/fc?command=lot-show-all"/>" role="button">Show All</a>
  </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>