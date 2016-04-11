<section class="categories">
    <form action="" method="post">
     <c:forEach var="c" items="${categories}">
         <c:set var="contains" value="false"/>
         <c:forEach var="e" items="${categoriesSelected}">
             <c:if test="${c.name == e}">
                 <c:set var="contains" value="true"/>
             </c:if>
         </c:forEach>
         <input id="${c.name}" class="hidden" type="submit" name="category"  value="${c.name}" />
         <c:choose>
             <c:when test="${contains}">
                 <label class="category category_selected" for="${c.name}">${c.name}</label>
             </c:when>
             <c:when test="${not contains}">
                 <label class="category" for="${c.name}">${c.name}</label>
             </c:when>
         </c:choose>
     </c:forEach>
 </form>
</section>