<%@ include file="../includes/header.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row justify-content-center">
		<div class="col-md-5 mt-5">

			<h1 class="mb-5 text-center">Register Your Account</h1>

			<c:if test="${param['error']}">
				<div class="alert alert-danger mb-4" role="alert">
					${errorMessage}
				</div>
			</c:if>

			<form:form action="register" method="POST"
				modelAttribute="registrationDto">
				<div class="mb-4">
					<form:label path="fullname" class="form-label">Full Name</form:label>
					<form:input path="fullname" id="fullname" class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="fullname" />
					</p>
				</div>

				<div class="mb-4">
					<form:label path="username" class="form-label">Username</form:label>
					<form:input path="username" id="username" class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="username" />
					</p>
				</div>

				<div class="mb-4">
					<form:label path="password" class="form-label">Password</form:label>
					<form:input type="password" path="password" id="password" class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="password" />
					</p>
				</div>

				<div class="mb-4">
					<form:label path="confirmPassword" class="form-label">Confirm Password</form:label>
					<form:input type="password" path="confirmPassword" id="confirmPassword"
						class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="confirmPassword" />
					</p>
				</div>

				<div class="mb-4">
					<form:label path="email" class="form-label">Email</form:label>
					<form:input path="email" id="email" class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="email" />
					</p>
				</div>

				<div class="mb-5">
					<form:label path="address" class="form-label">Address</form:label>
					<form:input path="address" id="address" class="form-control" />
					<p class="text-warning mt-2">
						<form:errors path="address" />
					</p>
				</div>

				<form:button name="register" id="register"
					class="btn btn-primary w-100 mt-3">Register</form:button>

			</form:form>

			<p class="text-center mt-5">
				Already have an account? <a href="login"
					class="link-info link-opacity-75-hover">Login here</a>
			</p>

		</div>
	</div>
</div>


<%@ include file="../includes/footer.jsp"%>

