<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key="${not empty title ? title : 'product.field.image.title'}" /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=product-add"/>" id="add-product-form" method="post" enctype="multipart/form-data">
            <c:import url="/jsp/template/form-errors.jsp"/>
            <div class="form-group<c:if test="${errors['user.field.name'] ne null}"> invalid</c:if>">
                <label for="name-field"><fmt:message key="product.field.name.label" /></label>
                <input value="<c:if test="${name ne null}">${name}</c:if>" type="text" name="name" class="form-control" id="name-field" aria-describedby="nameHelp" placeholder="<fmt:message key="product.field.name.placeholder" />">
                <small id="nameHelp" class="form-text text-muted"><fmt:message key="product.field.name.help" /></small>
                <c:if test="${errors['product.field.name'] ne null}">
                    <c:forEach items="${errors['product.field.name']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="product.field.name.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['product.field.description'] ne null}"> invalid</c:if>">
                <label for="description-field"><fmt:message key="product.field.description.label" /></label>
                <input value="<c:if test="${description ne null}">${description}</c:if>" type="text" name="description" class="form-control" id="description-field" aria-describedby="descriptionHelp" placeholder="<fmt:message key="product.field.description.placeholder" />">
                <c:if test="${errors['product.field.description'] ne null}">
                    <c:forEach items="${errors['product.field.description']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="product.field.description.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['product.field.amount'] ne null}"> invalid</c:if>">
                <label for="amount-field"><fmt:message key="product.field.amount.label" /></label>
                <input value="<c:if test="${amount ne null}">${amount}</c:if>" type="number" name="amount" class="form-control" id="amount-field" aria-describedby="amount" placeholder="<fmt:message key="product.field.amount.placeholder" />">
                <c:if test="${errors['product.field.amount'] ne null}">
                    <c:forEach items="${errors['product.field.amount']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="product.field.amount.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['product.field.price'] ne null}"> invalid</c:if>">
                <label for="price-field"><fmt:message key="product.field.price.label" /></label>
                <input value="<c:if test="${price ne null}">${price}</c:if>" type="number" name="price" class="form-control" id="price-field" aria-describedby="price" placeholder="<fmt:message key="product.field.price.placeholder" />">
                <c:if test="${errors['product.field.price'] ne null}">
                    <c:forEach items="${errors['product.field.price']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="product.field.price.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="product.submit.add.label" /></button>
        </form>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>