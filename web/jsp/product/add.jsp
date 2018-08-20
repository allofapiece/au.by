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
        <form action="<c:url value="/fc?command=product-add"/>" id="add-product-form" method="post" enctype="multipart/form-data">
            <c:if test="${errors ne null and not empty errors}">
                <div class="alert alert-danger" role="alert">
                    <h5 class="alert-heading"><fmt:message key="signup.error.form.summary.title" /></h5>
                    <c:forEach items="${errors}" var="field">
                        <h6 class="alert-heading"><fmt:message key='${field.key}.label' /></h6>
                        <c:forEach items="${field.value}" var="error">
                            <p><fmt:message key='${field.key}.${error}.message' /></p>
                        </c:forEach>
                    </c:forEach>
                </div>
            </c:if>
            <div class="form-group">
                <label for="name-field"><fmt:message key="product.field.name.label" /></label>
                <input type="text" name="name" class="form-control" id="name-field" aria-describedby="nameHelp" placeholder="<fmt:message key="product.field.name.placeholder" />">
                <small id="nameHelp" class="form-text text-muted"><fmt:message key="product.field.name.help" /></small>
            </div>
            <div class="form-group">
                <label for="description-field"><fmt:message key="product.field.description.label" /></label>
                <input type="text" name="description" class="form-control" id="description-field" aria-describedby="descriptionHelp" placeholder="<fmt:message key="product.field.description.placeholder" />">
            </div>
            <div class="form-group">
                <label for="amount-field"><fmt:message key="product.field.amount.label" /></label>
                <input type="number" name="amount" class="form-control" id="amount-field" aria-describedby="amount" placeholder="<fmt:message key="product.field.amount.placeholder" />">
            </div>
            <div class="form-group">
                <label for="price-field"><fmt:message key="product.field.price.label" /></label>
                <input type="number" name="price" class="form-control" id="price-field" aria-describedby="price" placeholder="<fmt:message key="product.field.price.placeholder" />">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="product.submit.add.label" /></button>
        </form>
    </div>
    <%--<div class="col-6">
        <h4><fmt:message key="product.field.image.title"/></h4>
        <input type="file" form="add-product-form" name="image" accept="image/jpeg,image/png,image/gif">
    </div>--%>
</div>

<c:import url="/jsp/template/footer.jsp"/>