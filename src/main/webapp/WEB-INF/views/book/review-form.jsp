<%@ include file="../includes/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="../fragments/navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col mt-4 text-white">
			<nav class="breadcrumb-white" aria-label="breadcrumb">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="${homePath}/${bookId}"
						class="text-white">${bookTitle}</a></li>
					<li class="breadcrumb-item active" aria-current="page"
						class="text-white">${isUpdate ? 'Update' : 'Add'} Review</li>
				</ol>
			</nav>

		</div>

	</div>

	<div class="row justify-content-center">
		<div class="col-md-6 mt-5 p-5 border border-white custom-shadow">
			<h2 class="mb-5 text-center">${isUpdate ? 'Update Your' : 'Add New'} Review</h2>

			<form:form action="${isUpdate ? 'update-review' : 'add-review'}" method="POST"
				modelAttribute="reviewFormDto">
				<div class="mb-5">
					<form:label path="content" class="form-label">Review Content</form:label>
					<form:textarea path="content" class="form-control" rows="5"></form:textarea>
					<p class="text-warning mt-3">
						<form:errors path="content" />
					</p>

					<form:label path="rating" class="form-label mt-5">Rating</form:label>
					<div class="stars">
						<button type="button" class="star-button" onclick="selectStars(1)">
							<i class="bi bi-star-fill"></i>
						</button>
						<button type="button" class="star-button" onclick="selectStars(2)">
							<i class="bi bi-star-fill"></i>
						</button>
						<button type="button" class="star-button" onclick="selectStars(3)">
							<i class="bi bi-star-fill"></i>
						</button>
						<button type="button" class="star-button" onclick="selectStars(4)">
							<i class="bi bi-star-fill"></i>
						</button>
						<button type="button" class="star-button" onclick="selectStars(5)">
							<i class="bi bi-star-fill"></i>
						</button>
					</div>
					<p class="text-warning mt-3">
						<form:errors path="rating" />
					</p>

					<!-- Hidden input to store the selected star rating -->
					<form:input type="hidden" id="rating" path="rating" value="${rating}" class="form-control" />

				</div>

				<form:button name="add-review" id="add-review"
					class="btn btn-primary w-75 mt-3 d-block mx-auto">${isUpdate ? 'Update' : 'Add'} Review</form:button>
			</form:form>
		</div>
	</div>
</div>



<%@ include file="../includes/footer.jsp"%>
