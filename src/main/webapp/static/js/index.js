document.addEventListener('DOMContentLoaded', function() {
	if (window.location.pathname.includes('/books') && window.location.pathname.includes('/update-review')) {
		const ratingValue = document.getElementById("rating").value;

		if (ratingValue > 0) {
			selectStars(ratingValue);
		}
	}
})

// for managing star rating
function selectStars(value) {
	const buttons = document.querySelectorAll('.star-button');
	buttons.forEach((button, index) => {
		button.classList.toggle('active', index < value);
	});
	document.getElementById('rating').value = value;
}

