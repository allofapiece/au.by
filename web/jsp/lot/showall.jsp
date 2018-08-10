<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2></h2>
    <hr>
</div>
<div class="row">
    <div class="container-fluid">
        <form class="form-inline lot-search">
            <input type="search" class="form-control mr-lg-2" placeholder="Search for..">
            <button type="submit" class="btn btn-primary">GO</button>
            <div class="lot-filter">
                <a href="#" role="button" class="btn btn-outline-cyan" aria-pressed="true"><fmt:message key="lot.show.inscription.status.open" /></a>
                <a href="#" role="button" aria-pressed="true" class="btn btn-outline-primary"><fmt:message key="lot.show.inscription.status.started" /></a>
                <a href="#" role="button" aria-pressed="true" class="btn btn-outline-teal"><fmt:message key="lot.show.inscription.status.completed" /></a>
                <a href="#" role="button" aria-pressed="true" class="btn btn-outline-secondary"><fmt:message key="lot.show.inscription.status.proposed" /></a>
                <a href="#" role="button" aria-pressed="true" class="btn btn-outline-danger"><fmt:message key="lot.show.inscription.status.closed" /></a>
            </div>
        </form>

    </div>
</div>
<div class="row" style="margin-top: 15px;">
    <div class="container-fluid">
        <div class="lots card-columns">
            <c:import url="/jsp/template/lot.jsp"/>
        </div>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>