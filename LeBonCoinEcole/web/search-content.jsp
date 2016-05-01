<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<article class="search-container">
    <aside>
        <header>
            <h2>Catégories</h2>
        </header>
        <%@include file="form-categories-selector.jsp" %>
    </aside>

    <section class="items">
        <header>
            <form id="search-form" method="GET">
                <div>
                    <select name="school" value="">
                        <c:choose>
                            <c:when test="${pv.school == '' }">
                                <option  selected="selected" value="">Aucune école</option>
                            </c:when>
                            <c:otherwise>
                                <option value="">Aucune école</option>
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="s" items="${schools}">
                            <c:choose>
                                <c:when test="${pv.school == s.name}">
                                    <option selected="selected" >${s.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option>${s.name}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
                    <input name="areaCode" type="text" maxlength="5" value="${ pv.areaCode }" placeholder="Departement - Ex 06600" />
                </div>
                
                <div>
                    <label for="minPrice" >Prix min :</label>
                    <input name="minPrice" id="minPrice" type="numeric" min="0" value="${ pv.minPrice }" />

                    <label for="maxPrice" >Prix max :</label>
                    <input name="maxPrice" id="maxPrice" type="numeric" min="0" value="${ pv.maxPrice }" />
                    
                    <input name ="key" type="text" placeholder="Mots clés" value="${ pv.key }" /> 
                </div>

                <button class="button" type="submit">Envoyer</button>
            </form>
        </header>

        
        <c:choose>
            <c:when test="${nbPages >= 1}">
                <section>
                    
                    <!-- Pagination top -->
                    <nav class="paginations">
                        <c:if test="${param.page > 1}">
                            <input id="start" type="submit" class="hidden" name="page" form="search-form" value="1">
                            <label class="page" for="start" ><<</label>
                                    
                            <input id="previous" type="submit" class="hidden" name="page" form="search-form" value="${param.page - 1}">
                            <label class="page" for="previous" ><</label>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${nbPages}">
                            <c:choose>
                                <c:when test="${i == param.page}">
                                    <label class="page selected">${i}</label>
                                </c:when>
                                <c:otherwise>
                                    <input id="page-${i}" type="submit" class="hidden" name="page" form="search-form" value="${i}">
                                    <label class="page" for="page-${i}" >${i}</label>
                                </c:otherwise>

                            </c:choose>
                        </c:forEach>
                        <c:if test="${param.page < nbPages}">                            
                            <input id="next" type="submit" class="hidden" name="page" form="search-form" value="${param.page + 1}">
                            <label class="page" for="next" >></label>
                                    
                            <input id="end" type="submit" class="hidden" name="page" form="search-form" value="${nbPages}">
                            <label class="page" for="end" >>></label>
                        </c:if>
                    </nav>
                    <!-- END Pagination top -->
                    
                    <!-- Announcements  -->
                    <c:forEach var="a" items="${announcements}">
                        <a href="announcement?id=${a.id}" class="item clickable" >
                            <img src="${a.url}" /> 
                            <div class="description">
                                <h2 class="title"> ${a.title} </h2>
                                <p class="desc">${a.description}</p>
                                <p class="price">${a.price} &euro;</p>
                                <p class="date">${a.startDate}</p>
                            </div>
                        </a>
                    </c:forEach>
                    <!-- END Announcements  -->

                    <!-- Pagination bottom -->
                    <nav class="paginations">
                        <c:if test="${param.page > 1}">
                            <input id="start" type="submit" class="hidden" name="page" form="search-form" value="1">
                            <label class="page" for="start" ><<</label>
                                    
                            <input id="previous" type="submit" class="hidden" name="page" form="search-form" value="${param.page - 1}">
                            <label class="page" for="previous" ><</label>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${nbPages}">
                            <c:choose>
                                <c:when test="${i == param.page}">
                                    <label class="page selected">${i}</label>
                                </c:when>
                                <c:otherwise>
                                    <input id="page-${i}" type="submit" class="hidden" name="page" form="search-form" value="${i}">
                                    <label class="page" for="page-${i}" >${i}</label>
                                </c:otherwise>

                            </c:choose>
                        </c:forEach>
                        <c:if test="${param.page < nbPages}">                            
                            <input id="next" type="submit" class="hidden" name="page" form="search-form" value="${param.page + 1}">
                            <label class="page" for="next" >></label>
                                    
                            <input id="end" type="submit" class="hidden" name="page" form="search-form" value="${nbPages}">
                            <label class="page" for="end" >>></label>
                        </c:if>
                    </nav>
                    <!-- END Pagination bottom -->
                </section>
            </c:when>

            <c:otherwise>
                <section class="no-result">
                    <p>
                        Aucun résultat n'a été trouvé pour cette recherche...
                    </p>
                </section>
            </c:otherwise>
        </c:choose>
    </section>
</article>