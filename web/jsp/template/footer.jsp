<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<c:set var="query" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:if test="${query eq null}">
    <c:set var="url" value="${uri}?language="/>
</c:if>
<c:if test="${query ne null}">
    <c:set var="url" value="${uri}?${query}"/>
</c:if>

        </div> <!--content div close hear-->
        <footer class="footer">
            <div class="container">
                <span class="text-muted"><fmt:message key="all.footer.createdby"/>  <a href="https://github.com/allofapiece">allofapiece</a></span>
                <span class="text-muted language" style="float: right;"><a data-lang="ru" href="${url}">Русский</a> | <a data-lang="en_EN" href="${url}">English</a></span>
            </div>
        </footer>
    </div> <!--wrapper div close hear-->





    <script>
            <c:if test="${user ne null}">var userId = parseInt("${sessionScope.user.id}");</c:if>
            <c:if test="${user ne null}">
                var userRolesString = "${sessionScope.user.roles}";
            var userRoles = getRolesByJSPString(userRolesString);
                    </c:if>;


        $('.language a').each(function () {
            var url = $(this).attr('href');
            url = url.replace(/[&?]language(=[^&]*)?|^language(=[^&]*)?&?/g, '');
            $(this).attr('href', url + (url.indexOf('?') !== -1 ? '&language=' : 'language=') + $(this).data('lang'));
        })
    </script>



    <c:if test="${param.dependency ne null}">
        <script src="${param.dependency}"></script>
    </c:if>

    <script src="${context}/js/product.js"></script>


    </body>
</html>
