<% String rtype = (String) request.getAttribute("PDFPATH"); %>
<% System.out.println("pdf in showreport.htm : " + rtype); %>

<html>
    <head><title></title></head>
<body>
<frame>
	<% if(rtype != null) { %>
		<%--<embed src="<%=rtype %>" width="100%" height="100%"> </embed>--%>

                <object type="application/pdf" data="<%=rtype %>"></object>
	<% } else { %>
		No Records Found...
	<% } %>
</frame>
</body>
</html>

	