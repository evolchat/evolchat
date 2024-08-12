document.addEventListener('DOMContentLoaded', (event) => {
        // Select all checkboxes with the class 'interest-checkbox'
        const checkboxes = document.querySelectorAll('.interest-checkbox');

        // Add event listener to each checkbox
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                // Count the number of checked checkboxes
                const checkedCount = document.querySelectorAll('.interest-checkbox:checked').length;

                // If more than 5 checkboxes are checked, uncheck the current one
                if (checkedCount > 5) {
                    this.checked = false;
                    Swal.fire({
                      icon: "error",
                      title: "최대 5개까지만 선택할 수 있습니다.",
                      color: '#FFFFFF',
                      background: '#35373D'
                    });
                }
            });
        });
    });