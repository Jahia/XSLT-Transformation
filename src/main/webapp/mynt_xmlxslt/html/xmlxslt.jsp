<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<c:catch var="XmlXsl_error">
    <c:catch var="socketExceptionVariable">
        <c:set var="xmlContent"/>
        <c:set var="xmlReference" value="${currentNode.properties.xmlReference.string}"/>
        <c:choose>
            <c:when test="${xmlReference == 'mymix:xmlUri'}">
                <c:import var="xmlContent" url="${currentNode.properties.xmlUri.string}"/>
            </c:when>
            <c:when test="${xmlReference == 'mymix:xmlFile'}">
                <jcr:nodeProperty node="${currentNode}" name="xmlFile" var="xmlFile"/>
                <jcr:node path="${xmlFile.node.path}/jcr:content" var="xmlFileContent"/>
                <jcr:nodeProperty node="${xmlFileContent}" name="jcr:data" var="xmlContentData"/>
                <c:set var="xmlContent" value="${xmlContentData.string}"/>
            </c:when>
            <c:otherwise>
                <c:set var="xmlContent" value="${currentNode.properties.xmlText.string}"/>
            </c:otherwise>
        </c:choose>
        <c:set var="xsltReference" value="${currentNode.properties.xsltReference.string}"/>
        <c:choose>
            <c:when test="${xsltReference == 'mymix:xsltUri'}">
                <c:import var="xsltContent" url="${currentNode.properties.xsltUri.string}"/>
            </c:when>
            <c:when test="${xsltReference == 'mymix:xsltFile'}">
                <jcr:nodeProperty node="${currentNode}" name="xsltFile" var="xsltFile"/>
                <jcr:node path="${xsltFile.node.path}/jcr:content" var="xsltFileContent"/>
                <jcr:nodeProperty node="${xsltFileContent}" name="jcr:data" var="xsltContentData"/>
                <c:set var="xsltContent" value="${xsltContentData.string}"/>
            </c:when>
            <c:otherwise>
                <c:set var="xsltContent" value="${currentNode.properties.xsltText.string}"/>
            </c:otherwise>
        </c:choose>
        <x:transform doc="${xmlContent}" xslt="${xsltContent}">
            <c:forEach var="xslParam" items="${xslParams}">
                <x:param name="${xslParam.key}" value="${xslParam.value[0]}"/>
            </c:forEach>
        </x:transform>

        <c:remove var="xmlContent"/>
        <c:remove var="xsltContent"/>

    </c:catch>
    <c:if test="${socketExceptionVariable != null}">
        <p>There was an error here</p>
        <c:out value="${socketExceptionVariable}"/>
    </c:if>
</c:catch>
<c:if test="${not empty XmlXsl_error}">
    Problem when transforming: ${XmlXsl_error}
</c:if>