<%@ include file="../includes/header.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="../fragments/navbar.jsp" %>

<div class="container">
	<div class="row justify-content-center">
		<div class="col-md-9 mt-5">
			<h1 class="mb-5 text-center">Welcome, ${userFullname}</h1>

			<nav class="navbar mb-5">
				<div class="container">
					<!-- Sort by rating form -->
					<form class="d-flex" role="search" action="books" method="GET">
						<!-- Hidden input for search if available -->
						<c:if test="${not empty search}">
							<input type="hidden" name="search" value="${search}" />
						</c:if>

						<!-- Hidden input for filter if available -->
						<c:if test="${not empty filter}">
							<input type="hidden" name="filter" value="${filter}" />
						</c:if>

						<!-- Order by Rating-->
						<select class="form-select me-3 w-auto" name="rating"
							onchange="this.form.submit()">
							<option value="none" ${rating == null ? 'selected' : ''}>Rating</option>
							<option value="desc" ${rating == 'desc' ? 'selected' : ''}>Most
								Rating</option>
							<option value="asc" ${rating == 'asc' ? 'selected' : ''}>Least
								Rating</option>
						</select>
					</form>

					<!-- Search form -->
					<form class="d-flex" role="search" action="books" method="GET">
						<!-- Search -->
						<input class="form-control me-3" type="search" name="search"
							value="${search}" placeholder="Search books">

						<button class="btn btn-outline-info" type="submit">Search</button>
					</form>
				</div>
			</nav>

			<!-- table -->

			<table class="table table-dark table-striped">
				<c:if test="${!isEmptyList}">
					<thead>
						<tr>
							<th scope="col">No.</th>
							<th scope="col">Title</th>
							<th scope="col">Author</th>
							<th scope="col">Genre</th>
							<th scope="col">Average Rating</th>
						</tr>
					</thead>

					<tbody class="table-group-divider">

						<c:forEach var="book" items="${bookDtos}" varStatus="status">
							<tr>
								<th scope="row">${status.index + 1 + (currentPage * pageSize)}</th>
								<td><a class="text-white"
									href="${homePath}/${book.getId()}">${book.getTitle()}</a></td>
								<td>${book.getAuthor()}</td>
								<td>${book.getGenre()}</td>
								<td>${book.getAverageRating()}</td>
							</tr>
						</c:forEach>
						
						<c:if test="${isEmptyList}">
							<p class="my-5 text-center text-warning">No books available at
							the moment. Please check back later!</p>
						</c:if>

					</tbody>
				</c:if>
			</table>


			<!-- Pagination -->
			<%@ include file="../fragments/pagination.jsp"%>


		</div>
	</div>



	<div class="row justify-content-center mt-5">
		<div class="col-md-7">
			<!-- Filter Book Button -->

			<c:forEach var="genre" items="${genres}">
				<a href="?filter=${genre}" role="button"
					class="btn btn-outline-info me-3 mb-3">${genre}</a>
			</c:forEach>

		</div>
	</div>

</div>


<%@ include file="../includes/footer.jsp"%>
