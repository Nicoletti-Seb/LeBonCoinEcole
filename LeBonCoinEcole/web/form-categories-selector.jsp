<section class="categories">
    <form action="" method="post">
     <c:forEach var="c" items="${categories}">
         <c:set var="contains" value="false"/>
         <c:forEach var="e" items="${categoriesSelected}">
             <c:if test="${c.name == e.name}">
                 <c:set var="contains" value="true"/>
             </c:if>
         </c:forEach>
         <c:choose>
             <c:when test="${contains}">
                 <label class="category category_selected" for="${c.name}">${c.name}</label>
             </c:when>
             <c:when test="${not contains}">
                 <label class="category" for="${c.name}">${c.name}</label>
             </c:when>
         </c:choose>
         <input id="${c.name}"  type="submit" name="category"  value="${c.name}" />
     </c:forEach>
 </form>
</section>