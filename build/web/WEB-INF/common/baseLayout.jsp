<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><tiles:insertAttribute name="title"/></title>
        <link href="default.css" rel="stylesheet" type="text/css" />
        <%--<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css" />--%>
    </head>
    <body>
        <table width="100%" >
            <tr>
                <td><tiles:insertAttribute name="header"/></td>
            </tr>
            <tr>
                <td><tiles:insertAttribute name="menu"/></td>
            </tr>
            <tr>
                <td>
                    <tiles:insertAttribute name="body"/>
                </td>
            </tr>
            <tr>
                <td>
                    <tiles:insertAttribute name="footer"/>
                </td>
            </tr>
        </table>
    </body>
</html>
