document.getElementById('booking-form').addEventListener('submit', function(event) {
    event.preventDefault();
    // Handle form submission logic here
  });
  
  document.getElementById('booking-history-list').addEventListener('click', function(event) {
    if (event.target.classList.contains('cancel-booking')) {
      // Handle booking cancellation logic here
    } else if (event.target.classList.contains('view-details')) {
      // Handle view booking details logic here
    }
  });