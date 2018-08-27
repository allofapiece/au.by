<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div class="container">
    <a class="btn btn-primary" href="<c:url value="/fc?command=product-add"/>"><fmt:message key="product.submit.add.label" /></a>
    <ul class="nav nav-tabs" id="tab-products" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="all-products-tab" data-toggle="tab" href="#all" role="tab" aria-controls="all" aria-selected="true"><fmt:message key="product.show.tab.inscription.status.all" /></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="in-bag-tab" data-toggle="tab" href="#in-bag" role="tab" aria-controls="in-bag" aria-selected="false"><fmt:message key="product.show.tab.inscription.status.bag" /></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="in-lots-tab" data-toggle="tab" href="#in-lots" role="tab" aria-controls="in-lots" aria-selected="false"><fmt:message key="product.show.tab.inscription.status.lot" /></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="sold-tab" data-toggle="tab" href="#sold" role="tab" aria-controls="sold" aria-selected="false"><fmt:message key="product.show.tab.inscription.status.sold" /></a>
        </li>
    </ul>
    <div class="tab-content" id="myTabContent">
        <div class="tab-pane fade show active" id="all" role="tabpanel" aria-labelledby="all-products-tab">
            <c:if test="${products eq null or fn:length(products) == 0}">
                <p><fmt:message key="product.show.inscription.null" /></p>
            </c:if>
            <c:forEach items="${products}" var="product">
                <c:import url="/jsp/template/product.jsp">
                    <c:param name="name" value="${product.name}"/>
                    <c:param name="description" value="${product.description}"/>
                    <c:param name="amount" value="${product.amount}"/>
                    <c:param name="price" value="${product.price}"/>
                    <c:param name="image" value="${product.images[0]}"/>
                    <c:param name="id" value="${product.id}"/>
                    <c:param name="status" value="${product.status}"/>
                </c:import>
            </c:forEach>
        </div>
        <div class="tab-pane fade" id="in-bag" role="tabpanel" aria-labelledby="in-bag-tab">
            <c:forEach items="${products}" var="product">
                <c:if test="${product.status == 'AVAILABLE'}">
                    <c:import url="/jsp/template/product.jsp">
                        <c:param name="name" value="${product.name}"/>
                        <c:param name="description" value="${product.description}"/>
                        <c:param name="amount" value="${product.amount}"/>
                        <c:param name="price" value="${product.price}"/>
                        <c:param name="image" value="${product.images[0]}"/>
                        <c:param name="id" value="${product.id}"/>
                        <c:param name="status" value="${product.status}"/>
                    </c:import>
                    <c:set value="true" var="isAvailableFind"/>
                </c:if>
            </c:forEach>
            <c:if test="${isAvailableFind != true}">
                <p><fmt:message key="product.show.inscription.null" /></p>
            </c:if>
        </div>
        <div class="tab-pane fade" id="in-lots" role="tabpanel" aria-labelledby="in-lots-tab">
            <c:forEach items="${products}" var="product">
                <c:if test="${product.status == 'IN_LOT'}">
                    <c:import url="/jsp/template/product.jsp">
                        <c:param name="name" value="${product.name}"/>
                        <c:param name="description" value="${product.description}"/>
                        <c:param name="amount" value="${product.amount}"/>
                        <c:param name="price" value="${product.price}"/>
                        <c:param name="image" value="${product.images[0]}"/>
                        <c:param name="id" value="${product.id}"/>
                        <c:param name="status" value="${product.status}"/>
                    </c:import>
                    <c:set value="true" var="isInLotFind"/>
                </c:if>
            </c:forEach>
            <c:if test="${isInLotFind != true}">
                <p><fmt:message key="product.show.inscription.null" /></p>
            </c:if>
        </div>
        <div class="tab-pane fade" id="sold" role="tabpanel" aria-labelledby="sold-tab">
            <c:forEach items="${products}" var="product">
                <c:if test="${product.status == 'SOLD'}">
                    <c:import url="/jsp/template/product.jsp">
                        <c:param name="name" value="${product.name}"/>
                        <c:param name="description" value="${product.description}"/>
                        <c:param name="amount" value="${product.amount}"/>
                        <c:param name="price" value="${product.price}"/>
                        <c:param name="image" value="${product.images[0]}"/>
                        <c:param name="id" value="${product.id}"/>
                        <c:param name="status" value="${product.status}"/>
                    </c:import>
                    <c:set value="true" var="isSoldFind"/>
                </c:if>
            </c:forEach>
            <c:if test="${isSoldFind != true}">
                <p><fmt:message key="product.show.inscription.null" /></p>
            </c:if>
        </div>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>