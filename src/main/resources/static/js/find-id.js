document.addEventListener('DOMContentLoaded', function () {
    const sections = document.querySelectorAll('.section');
    let currentSectionIndex = -1;

    document.querySelectorAll('input[name="auth"]').forEach((input, index) => {
        input.addEventListener('change', function() {
            showSection(index);
        });
    });
});

function selectOption(id) {
    document.getElementById(id).checked = true;
    const index = Array.from(document.querySelectorAll('input[name="auth"]')).findIndex(input => input.id === id);
    showSection(index);
}

function showSection(index) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.classList.remove('active'));
    sections[index].classList.add('active');

    const options = document.querySelectorAll('input[name="auth"]');
    currentSectionIndex = index;
    updateNavButtons();
}
document.addEventListener('DOMContentLoaded', function () {
    const options = document.querySelectorAll('input[name="auth"]');
    const nextBtn = document.getElementById('nextBtn');

    options.forEach(option => {
        option.addEventListener('change', function () {
            nextBtn.classList.add('active');
        });
    });
});
