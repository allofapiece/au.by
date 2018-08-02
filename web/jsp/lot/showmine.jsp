<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form class="form-inline">

            <input type="search" class="form-control mr-lg-2" placeholder="Search for..">
            <button type="submit" class="btn btn-primary">GO</button>
        </form>
    </div>
</div>
<div class="row">

    <div id="lots">

    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>