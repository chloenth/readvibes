<%@ include file="../includes/header.jsp"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>

<base href="${homePath}/">

<%@ include file="../fragments/navbar.jsp"%>

<div class="container">
	<!-- Book Details -->
	<div class="row justify-content-center">
		<div class="col-md-6 mt-5 mx-auto">
			<!-- Book Details -->
			<div class="card p-4 bg-success-subtle">
				<h2 class="text-center mb-4">${bookDto.getTitle()}</h2>

				<div class="mx-auto mt-3">
					<p>
						<strong>Author:</strong> ${bookDto.getAuthor()}
					</p>
					<p>
						<strong>Genre:</strong> ${bookDto.getGenre()}
					</p>
					<p>
						<strong>Average Rating:</strong> ${bookDto.getAverageRating()}
					</p>

					<!-- Book Details - Review Action Buttons -->
					<c:choose>
						<c:when test="${isReviewed}">
							<!-- update review button -->
							<a href="${bookDto.getId()}/${reviewId}/update-review"
								class="btn btn-primary mt-4 mb-3" role="button">Update your
								review</a>

							<!-- remove review button trigger confirm remove modal -->
							<button type="button" class="btn btn-danger my-3 d-block"
								data-bs-toggle="modal" data-bs-target="#exampleModal">
								Remove your review</button>

						</c:when>

						<c:otherwise>
							<!-- add new review button -->
							<a href="${bookDto.getId()}/add-review"
								class="btn btn-primary mt-4 mb-3" role="button">Add your
								review</a>
						</c:otherwise>

					</c:choose>

				</div>

				<!-- Alert of review action button (add/update/remove review) -->
				<c:if test="${param['addReview'] == 'duplicate'}">
					<p class="text-danger text-center mt-3">You have already added
						a review to this book.</p>
				</c:if>

				<c:if test="${param['addReview'] == 'successful'}">
					<p class="text-success text-center mt-3">You have successfully
						added a review for this book.</p>
				</c:if>

				<c:if test="${param['updateReview'] == 'successful'}">
					<p class="text-success text-center mt-3">Your review for this
						book has been successfully updated.</p>
				</c:if>

				<c:if test="${param['removeReview'] == 'successful'}">
					<p class="text-success text-center mt-3">You have successfully
						removed your review from this book.</p>
				</c:if>

			</div>

			<!-- confirm modal of review remove button -->
			<div class="modal fade" id="exampleModal" tabindex="-1"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content text-dark">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">Remove
								Your Review</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">Are you sure you want to remove your
							review for this book?</div>
						<div class="modal-footer">
							<a href="${bookDto.getId()}/${reviewId}/remove-review"
								role="button" class="btn btn-primary">Confirm</a>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- Reviews Section -->
	<div class="row justify-content-center mt-5">
		<div class="col-md-7 mx-auto">
			<h3 class="text-center mb-5">Reviews</h3>

			<c:choose>
				<c:when test="${!isEmptyList}">
					<c:forEach var="review" items="${reviewDtos}">

						<div class="mb-4 px-5">
							<div class="review-header d-flex justify-content-between mt-3">
								<span class="reviewer-name"><strong>${review.getUserId() == userId ? 'You' : review.getUserFullname()}</strong></span>
								<span class="review-rating"><strong>Rating: </strong>${review.getRating()}</span>
							</div>

							<p class="review-content mt-3">${review.getContent()}</p>

							<small class="mb-3 text-secondary">Reviewed on:
								${review.getCreatedAtString()}</small>

							<c:if test="${review.getEditedAtString() != null }">
								<small class="mb-3 text-secondary d-block">Edited on:
									${review.getEditedAtString()}</small>
							</c:if>

							<hr>
						</div>

					</c:forEach>

				</c:when>

				<c:otherwise>
					<p class="mt-5 text-center text-warning">There is no review yet
						on this book!</p>
				</c:otherwise>

			</c:choose>


			<!-- Pagination -->
			<%@ include file="../fragments/pagination.jsp"%>


		</div>
	</div>
</div>



<%@ include file="../includes/footer.jsp"%>

