document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");
    const loadingOverlay = document.querySelector(".loading-overlay");

    if (loginForm) {
        loginForm.addEventListener("submit", function() {
            loadingOverlay.classList.add("show");
        });
    }
});
