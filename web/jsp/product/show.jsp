<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>


<div class="container">
    <a class="btn btn-primary" href="<c:url value="/fc?command=product-add"/>"><fmt:message key="product.submit.add.label" /></a>
    <ul class="nav nav-tabs" id="tab-products" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="all-products-tab" data-toggle="tab" href="#all" role="tab" aria-controls="all" aria-selected="true">All products</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="in-bag-tab" data-toggle="tab" href="#in-bag" role="tab" aria-controls="in-bag" aria-selected="false">Products in bag</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="in-lots-tab" data-toggle="tab" href="#in-lots" role="tab" aria-controls="in-lots" aria-selected="false">Products in lots</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="sold-tab" data-toggle="tab" href="#sold" role="tab" aria-controls="sold" aria-selected="false">Sold products</a>
        </li>
    </ul>
    <div class="tab-content" id="myTabContent">
        <div class="tab-pane fade show active" id="all" role="tabpanel" aria-labelledby="all-products-tab">
            <c:forEach items="${products}" var="product">
                <c:import url="/jsp/template/product.jsp">
                    <c:param name="name" value="${product.name}"/>
                    <c:param name="description" value="${product.description}"/>
                    <c:param name="amount" value="${product.amount}"/>
                    <c:param name="price" value="${product.price}"/>
                    <c:param name="image" value="${product.images[0]}"/>
                    <c:param name="id" value="${product.id}"/>
                </c:import>
            </c:forEach>
        </div>
        <div class="tab-pane fade" id="in-bag" role="tabpanel" aria-labelledby="in-bag-tab">In bag</div>
        <div class="tab-pane fade" id="in-lots" role="tabpanel" aria-labelledby="in-lots-tab">In lots</div>
        <div class="tab-pane fade" id="sold" role="tabpanel" aria-labelledby="sold-tab">Sold</div>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>