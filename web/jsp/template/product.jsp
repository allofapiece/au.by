<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div class="card product" style="max-width: 18rem;">
    <c:if test="${param.image ne null and not empty param.image}">
        <img class="card-img-top" src="${param.image}" alt="${param.name}">
    </c:if>
    <c:choose>
        <c:when test="${param.status == 'AVAILABLE'}">
            <div class="card-header bg-success"><fmt:message key="product.show.inscription.status.bag" /></div>
        </c:when>
        <c:when test="${param.status == 'IN_LOT'}">
            <div class="card-header bg-warning"><fmt:message key="product.show.inscription.status.lot" /></div>
        </c:when>
    </c:choose>
    <div class="card-body">
        <h5 class="card-title">${param.name}</h5>
        <p class="card-text"><fmt:message key="product.field.amount.label" />: ${param.amount}</p>
        <p class="card-text"><fmt:message key="product.field.price.label" />: ${param.price}</p>
        <p class="card-text">${param.description}</p>
        <c:choose>
            <c:when test="${param.status == 'AVAILABLE'}">
                <a href="<c:url value="/fc?command=lot-add&productid=${param.id}"/> " class="btn btn-success"><fmt:message key="lot.inscription.action.create" /></a>
            </c:when>
            <%--<c:when test="${param.status == 'IN_LOT'}">
                <a href="#" class="btn btn-warning"><fmt:message key="product.inscription.action.delele-from-lot" /></a>
            </c:when>--%>
        </c:choose>
        <div class="product-tools">
            <c:if test="${param.status == 'AVAILABLE'}">
                <button class="btn btn-danger btn-xs delete-product">
                    &times;
                    <input type="hidden" value="${param.id}" name="delete-product">
                </button>
            </c:if>
        </div>
    </div>
</div>