<%@ page import="java.util.*" %>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
 
<html>
    <head>
    </head>
    <body> 
    <a href="index.html" style="margin-left: 90%;"><b>Back</b></a>
          <table width="100%" align="center"
               style="border:1px solid #58D68D;">
            <tr>
                <td colspan=7 align="center"
                    style="background-color:#D35400">
                    <b>Product Detail</b></td>
             </tr>
            <tr style="background-color:#58D68D;">
                <td><b>Product ID</b></td>
                <td><b>Product Name</b></td>
                <td><b>Product Brand</b></td>
                <td style="width: 800px;"><b>Product Description</b></td>
                <td><b>Product Cost</b></td>
                <td><b>Image</b></td>
                <td><b>Category</b></td>
             
             </tr>
            <%
                int count = 0;
                String color = "#F9EBB3";
                if (request.getAttribute("pidList") != null) {
                    ArrayList al = (ArrayList) request.getAttribute("pidList");
                    System.out.println(al);
                    Iterator itr = al.iterator();
                    while (itr.hasNext()) {
 
                        if ((count % 2) == 0) {
                            color = "#FAD7A0";
                        }
                        count++;
                        ArrayList pList = (ArrayList) itr.next();
            %>
            <tr style="background-color:<%=color%>;">
                <td><%=pList.get(6)%></td>
                <td><%=pList.get(5)%></td>
                <td><%=pList.get(0)%></td>
                
                <td style="width: 800px;"><%=pList.get(1)%></td>
                <td><%=pList.get(2)%></td>
                <td><b><img style="width: 500px; height: 500px" src=<%=pList.get(3)%>></b></td>
                <td><%=pList.get(4)%></td>
            </tr>
            <%
                    }
                }
                if (count == 0) {
            %>
            <tr>
                <td colspan=7 align="center"
                    style="background-color:#FAD7A0"><b>No Record Found..</b></td>
            </tr>
            <%            }
            %>
        </table>
    </body>
</html>