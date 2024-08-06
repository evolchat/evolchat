$(document).ready(function () {
    // 로그인 상태 유지 체크박스 기능
    $('#remember-me').on('change', function () {
        if ($(this).is(':checked')) {
            // 체크된 경우: 쿠키에 로그인 상태 유지 설정
            var username = $('#id').val();
            var password = $('#password').val();
            setCookie('remember_me_username', username, 7); // 7일 동안 유효한 쿠키 설정
            setCookie('remember_me_password', password, 7);
        } else {
            // 체크 해제된 경우: 쿠키에서 로그인 상태 유지 정보 제거
            deleteCookie('remember_me_username');
            deleteCookie('remember_me_password');
        }
    });

    // 페이지 로드 시 쿠키 확인하여 체크박스 상태 설정
    var rememberMeUsername = getCookie('remember_me_username');
    var rememberMePassword = getCookie('remember_me_password');
    if (rememberMeUsername && rememberMePassword) {
        $('#id').val(rememberMeUsername);
        $('#password').val(rememberMePassword);
        $('#remember-me').prop('checked', true);
    }

    // 쿠키 설정 함수
    function setCookie(name, value, days) {
        var expires = '';
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = '; expires=' + date.toUTCString();
        }
        document.cookie = name + '=' + (value || '') + expires + '; path=/';
    }

    // 쿠키 가져오기 함수
    function getCookie(name) {
        var nameEQ = name + '=';
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    // 쿠키 삭제 함수
    function deleteCookie(name) {
        document.cookie = name + '=; Max-Age=-99999999;';
    }

    // 입력 필드의 내용에 따라 버튼에 클래스 추가
    function toggleButtonClass() {
        var username = $('#id').val();
        var password = $('#password').val();
        if (username && password) {
            $('#login-button').addClass('login-bc-activate');
        } else {
            $('#login-button').removeClass('login-bc-activate');
        }
    }

    // 페이지 로드 시 초기 상태 설정
    toggleButtonClass();

    // 입력 필드의 내용 변경 시 클래스 토글
    $('#id, #password').on('input', function () {
        toggleButtonClass();
    });

    $('#id').focus()

    const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('sessionExpired')) {
            Swal.fire({
                title: '다른 환경에서 로그인하여 로그아웃 되었습니다.',
                text: "다시 로그인 해 주세요.",
                icon: 'error',
                reverseButtons: true,
                iconColor: '#FF4C4C',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인'
            });
        }
});
