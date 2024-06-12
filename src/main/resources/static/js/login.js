document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('loginForm').addEventListener('submit', function (e) {
        var username = document.getElementById('id').value;
        var password = document.getElementById('password').value;

        if (username === '' || password === '') {
            alert('아이디와 비밀번호를 입력해주세요.');
            e.preventDefault();
        } else {
            document.querySelector('.loading-overlay').classList.add('show');
        }
    });
});
