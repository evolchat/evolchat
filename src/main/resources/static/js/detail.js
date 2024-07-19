document.querySelectorAll('.options').forEach(option => {
    option.addEventListener('click', function(event) {
        event.stopPropagation();  // Prevent the event from bubbling up to document
        let dropdown = this.querySelector('.dropdown-menu');
        if (dropdown.style.display === 'block') {
            dropdown.style.display = 'none';
        } else {
            document.querySelectorAll('.dropdown-menu').forEach(menu => menu.style.display = 'none'); // Hide other menus
            dropdown.style.display = 'block';
        }
    });
});

// Close the dropdown menu if clicked outside
document.addEventListener('click', function() {
    document.querySelectorAll('.dropdown-menu').forEach(dropdown => {
        dropdown.style.display = 'none';
    });
});

document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.options').forEach(option => {
            option.addEventListener('click', function(event) {
                event.stopPropagation();  // Prevent the event from bubbling up to document
                let dropdown = this.querySelector('.dropdown-menu');
                if (dropdown.style.display === 'block') {
                    dropdown.style.display = 'none';
                } else {
                    document.querySelectorAll('.dropdown-menu').forEach(menu => menu.style.display = 'none'); // Hide other menus
                    dropdown.style.display = 'block';
                }
            });
        });

        // Close the dropdown menu if clicked outside
        document.addEventListener('click', function() {
            document.querySelectorAll('.dropdown-menu').forEach(dropdown => {
                dropdown.style.display = 'none';
            });
        });
    });