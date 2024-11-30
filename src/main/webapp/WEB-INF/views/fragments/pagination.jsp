<!-- Pagination -->
<div>
	<c:if test="${endPage > startPage}">
		<nav aria-label="..." class="mt-5 float-end">
			<ul class="pagination">
				<!-- Previous button -->
				<li class="page-item ${currentPage == startPage ? 'disabled' : ''}">
					<a class="page-link"
					href="${homePath}<c:if test='${not empty bookId}'>/${bookId}</c:if>?
									<c:if test='${not empty search}'>search=${search}&</c:if>
									<c:if test='${not empty filter}'>filter=${filter}&</c:if>
									<c:if test='${not empty rating}'>rating=${rating}&</c:if>
									page=${currentPage}">Previous</a>
				</li>

				<!-- Page number button -->
				<c:forEach begin="${startPage}" end="${endPage}" var="page">
					<li class="page-item ${currentPage == page ? 'active' : '' }">
						<a class="page-link"
						href="${homePath}<c:if test='${not empty bookId}'>/${bookId}</c:if>?
											<c:if test='${not empty search}'>search=${search}&</c:if>
											<c:if test='${not empty filter}'>filter=${filter}&</c:if>
											<c:if test='${not empty rating}'>rating=${rating}&</c:if>
											page=${page+1}">${page+1}</a>
					</li>
				</c:forEach>

				<!-- Next button -->
				<li class="page-item ${currentPage == endPage ? 'disabled' : ''}">
					<a class="page-link"
					href="${homePath}<c:if test='${not empty bookId}'>/${bookId}</c:if>?
										<c:if test='${not empty search}'>search=${search}&</c:if>
										<c:if test='${not empty filter}'>filter=${filter}&</c:if>
										<c:if test='${not empty rating}'>rating=${rating}&</c:if>
										page=${currentPage+2}">Next</a>
				</li>

			</ul>
		</nav>
	</c:if>
</div>