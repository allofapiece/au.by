<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<li class="list-group-item bieter prototype bieter-prototype">
    <a class="bet-bieter-name" data-label="<fmt:message key="lot.show.inscription.info.owner" />"></a>
    <input type="hidden" name="entity-id">
</li>