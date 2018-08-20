<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key="${not empty title ? title : 'title.lot.add'}" /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=lot-add"/>" id="add-lot-form" method="post" enctype="multipart/form-data">
            <h4><fmt:message key="title.lot.add.general" /></h4>
            <c:import url="/jsp/template/form-errors.jsp"/>
            <div class="form-group">
                <label for="type-field"><fmt:message key="lot.field.type.label" /></label>
                <select name="type" class="form-control" id="type-field" aria-describedby="typeHelp">
                    <option value="blitz"><fmt:message key="lot.field.type.value.blitz" /></option>
                    <option value="english"><fmt:message key="lot.field.type.value.english" /></option>
                    <option value="internet"><fmt:message key="lot.field.type.value.internet" /></option>
                </select>
                <small id="typeHelp" class="form-text text-muted"><fmt:message key="lot.field.type.help" /></small>
            </div>
            <div class="form-group">
                <label for="product-id-field"><fmt:message key="lot.field.product-id.label" /></label>
                <div class="search">
                    <input type="text" class="form-control" id="product-id-field" aria-describedby="product-idHelp" placeholder="<fmt:message key="lot.field.product-id.placeholder" />">
                    <ul class="search-result" style="display: none;">
                    </ul>
                </div>
                <small id="product-idHelp" class="form-text text-muted"><fmt:message key="lot.field.product-id.help" /></small>
                <div class="lot-products" style="margin-top: 15px; display: none;">
                    <div class="card product" >
                        <div class="card-header bg-success product-name"></div>
                        <div class="card-body">
                            <p class="card-text product-amount" data-label="<fmt:message key="product.field.amount.label" />"></p>
                            <p class="card-text product-price"  data-label="<fmt:message key="product.field.price.label" />"></p>
                            <p class="card-text product-description"></p>
                            <div class="product-tools">
                                <button class="btn btn-danger btn-xs delete-product">
                                    &times;
                                </button>
                            </div>
                        </div>
                        <input type="hidden" name="product-id">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="name-field"><fmt:message key="lot.field.name.label" /></label>
                <input type="text" name="name" class="form-control" id="name-field" aria-describedby="nameHelp" placeholder="<fmt:message key="lot.field.name.placeholder" />">
                <small id="nameHelp" class="form-text text-muted"><fmt:message key="lot.field.name.help" /></small>
            </div>
            <div class="form-group">
                <label for="description-field"><fmt:message key="lot.field.description.label" /></label>
                <input type="text" name="description" class="form-control" id="description-field" aria-describedby="descriptionHelp" placeholder="<fmt:message key="lot.field.description.placeholder" />">
            </div>
            <div class="form-group">
                <label for="amount-field"><fmt:message key="lot.field.amount.label" /></label>
                <input type="number" name="amount" class="form-control" id="amount-field" aria-describedby="amount" placeholder="<fmt:message key="lot.field.amount.placeholder" />">
            </div>
            <div class="form-group">
                <label for="begin-price-field"><fmt:message key="lot.field.begin-price.label" /></label>
                <input type="number" name="begin-price" class="form-control" id="begin-price-field" aria-describedby="begin-price" placeholder="<fmt:message key="lot.field.begin-price.placeholder" />">
            </div>
            <div class="form-group">
                <label for="start-date-field"><fmt:message key="lot.field.start-date.label" /></label>
                <input type="datetime-local" name="start-date" class="form-control" id="start-date-field" aria-describedby="start-date" placeholder="<fmt:message key="lot.field.start-date.placeholder" />">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="lot.submit.add.label" /></button>
        </form>
    </div>
    <div class="col-6 additional">
        <h4 class="blitz"><fmt:message key="title.lot.add.additional.blitz" /></h4>
        <h4 class="english"><fmt:message key="title.lot.add.additional.english" /></h4>
        <h4 class="internet"><fmt:message key="title.lot.add.additional.internet" /></h4>
        <div class="form-group blitz">
            <label for="outgoing-field"><fmt:message key="lot.field.outgoing.label" /></label>
            <input type="number" form="add-lot-form" name="outgoing" class="form-control" id="outgoing-field" aria-describedby="outgoing" placeholder="<fmt:message key="lot.field.outgoing.placeholder" />">
        </div>
        <div class="form-group blitz">
            <label for="round-amount-field"><fmt:message key="lot.field.round.amount.label" /></label>
            <input type="number" form="add-lot-form" name="round-amount" class="form-control" id="round-amount-field" aria-describedby="round-amount" placeholder="<fmt:message key="lot.field.round.amount.placeholder" />">
        </div>
        <div class="form-group blitz">
            <label for="round-time-field"><fmt:message key="lot.field.round.time.label" /></label>
            <input type="number" form="add-lot-form" name="round-time" class="form-control" id="round-time-field" aria-describedby="round-time" placeholder="<fmt:message key="lot.field.round.amount.placeholder" />">
        </div>
        <div class="form-group blitz">
            <label for="min-people-field"><fmt:message key="lot.field.min-people.label" /></label>
            <input type="number" form="add-lot-form" name="min-people" class="form-control" id="min-people-field" aria-describedby="min-people" placeholder="<fmt:message key="lot.field.min-people.placeholder" />">
        </div>
        <div class="form-group blitz">
            <label for="max-people-field"><fmt:message key="lot.field.max-people.label" /></label>
            <input type="number" form="add-lot-form" name="max-people" class="form-control" id="max-people-field" aria-describedby="max-people" placeholder="<fmt:message key="lot.field.max-people.placeholder" />">
        </div>
        <div class="form-group blitz internet">
            <label for="blitz-price-field"><fmt:message key="lot.field.blitz-price.label" /></label>
            <input type="number" form="add-lot-form" name="blitz-price" class="form-control" id="blitz-price-field" aria-describedby="blitz-price" placeholder="<fmt:message key="lot.field.blitz-price.placeholder" />">
        </div>
        <div class="form-group english internet">
            <label for="bet-time-field"><fmt:message key="lot.field.bet-time.label" /></label>
            <input type="number" form="add-lot-form" name="bet-time" class="form-control" id="bet-time-field" aria-describedby="bet-time" placeholder="<fmt:message key="lot.field.bet-time.placeholder" />">
        </div>
        <div class="form-group english">
            <label for="step-price-field"><fmt:message key="lot.field.step-price.label" /></label>
            <input type="number" form="add-lot-form" name="step-price" class="form-control" id="step-price-field" aria-describedby="step-price" placeholder="<fmt:message key="lot.field.step-price.placeholder" />">
        </div>
    </div>
</div>

<script src="${context}/js/lots.js"></script>

<c:import url="/jsp/template/footer.jsp"/>