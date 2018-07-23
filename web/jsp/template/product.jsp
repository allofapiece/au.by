<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<div class="card product" style="max-width: 18rem;">
    <c:if test="${param.image ne null and not empty param.image}">
        <img class="card-img-top" src="${param.image}" alt="${param.name}">
    </c:if>
    <div class="card-body">
        <h5 class="card-title">${param.name}</h5>
        <p class="card-text"><fmt:message key="product.field.amount.label" />: ${param.amount}</p>
        <p class="card-text"><fmt:message key="product.field.price.label" />: ${param.price}</p>
        <p class="card-text">${param.description}</p>
        <a href="#" class="btn btn-primary">Create lot</a>
        <div class="product-tools">
            <button class="btn btn-danger btn-xs delete-product">
                &times;
                <input type="hidden" value="${param.id}" name="delete-product">
            </button>
        </div>
    </div>
</div>