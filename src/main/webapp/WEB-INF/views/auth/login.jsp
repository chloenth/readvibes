<%@ include file="../includes/header.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row justify-content-center">
		<div class="col-md-5 mt-5">

			<h1 class="mb-5 text-center">Sign In Your Account</h1>

			<c:if
				test="${param['register']!= null && param['register'].equals('successful')}">
				<div class="alert alert-success mb-4" role="alert">
					${successMessage}</div>
			</c:if>

			<form:form action="login" method="POST" modelAttribute="loginDto">
				<div class="mb-5">
					<form:label path="username" class="form-label">Username</form:label>
					<form:input path="username" id="username" class="form-control" />
					<p class="text-warning mt-3">
						<form:errors path="username" />
					</p>
				</div>

				<div class="mb-5">
					<form:label path="password" class="form-label">Password</form:label>
					<form:input path="password" id="password" class="form-control" />
					<p class="text-warning mt-3">
						<form:errors path="password" />
					</p>
				</div>

				<form:button name="register" id="register"
					class="btn btn-primary w-100 mt-2">Sign in</form:button>

			</form:form>

			<p class="text-center mt-5">
				Don't have an account? <a href="register"
					class="link-info link-opacity-75-hover">Register here</a>
			</p>

		</div>
	</div>
</div>


<%@ include file="../includes/footer.jsp"%>

