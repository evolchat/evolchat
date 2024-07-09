//$(document).ready(function () {
//    // 비밀번호 확인 필드와 포커스 관련 처리 및 유효성 검사
//    $('#password, #confirmPassword').on('keyup', function () {
//        var password = $('#password').val();
//        var confirmPassword = $('#confirmPassword').val();
//
//        if (password === confirmPassword) {
//            $('#confirmPassword').removeClass('error');
//            $('#password').removeClass('error');
//            $('#password-error').text(''); // 일치할 경우 에러 메시지 초기화
//        } else {
//            $('#confirmPassword').addClass('error');
//            $('#password').addClass('error');
//            $('#password-error').text('비밀번호가 일치하지 않습니다.'); // 불일치할 경우 에러 메시지 표시
//        }
//    });
//
//    // 비밀번호 입력 필드에서 Enter 키 누를 때 비밀번호 확인으로 포커스 이동
//    $('#password').on('keydown', function (e) {
//        if (e.keyCode === 13) {
//            e.preventDefault();
//            $('#confirmPassword').focus();
//        }
//    });
//
//    // 회원가입 버튼 클릭 시 전체 유효성 검사
//    $('form').on('submit', function (e) {
//        var nickname = $('#nickname').val().trim();
//        var username = $('#username').val().trim();
//        var password = $('#password').val();
//        var confirmPassword = $('#confirmPassword').val();
//        var accept1 = $('#accept1').prop('checked');
//        var accept2 = $('#accept2').prop('checked');
//
//        var isValid = true;
//
//        // 닉네임 입력 여부 검사
//        //        if (nickname === '') {
//        //            showError($('#nickname'), '닉네임을 입력하세요.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#nickname'));
//        //        }
//
//        // 아이디 입력 여부 검사
//        //        if (username === '') {
//        //            showError($('#username'), '아이디를 입력하세요.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#username'));
//        //        }
//
//        // 비밀번호 입력 여부 검사
//        //        if (password === '') {
//        //            showError($('#password'), '비밀번호를 입력하세요.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#password'));
//        //        }
//
//        // 비밀번호 확인 입력 여부 및 일치 여부 검사
//        //        if (confirmPassword === '') {
//        //            showError($('#confirmPassword'), '비밀번호 확인을 입력하세요.');
//        //            isValid = false;
//        //        } else if (password !== confirmPassword) {
//        //            showError($('#confirmPassword'), '비밀번호가 일치하지 않습니다.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#confirmPassword'));
//        //        }
//
//        // 이용약관 동의 체크 여부 검사
//        //        if (!accept1) {
//        //            showError($('#accept1').closest('.field'), '이용약관에 동의해야 합니다.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#accept1').closest('.field'));
//        //        }
//
//        // 개인정보 수집 및 이용 동의 체크 여부 검사
//        //        if (!accept2) {
//        //            showError($('#accept2').closest('.field'), '개인정보 수집 및 이용에 동의해야 합니다.');
//        //            isValid = false;
//        //        } else {
//        //            hideError($('#accept2').closest('.field'));
//        //        }
//
//        // 유효성 검사 통과 여부에 따라 폼 제출 결정
//        if (!isValid) {
//            e.preventDefault(); // 유효성 검사를 통과하지 못한 경우 폼 제출 막기
//            $('#nickname').focus(); // 첫 번째 오류가 있는 필드로 포커스 이동
//        }
//    });
//
//    // 오류 메시지 표시 함수
//    function showError($element, message) {
//        $element.addClass('error');
//        $element.closest('.field').find('.error-msg').text(message).show();
//    }
//
//    // 오류 메시지 숨기는 함수
//    function hideError($element) {
//        $element.removeClass('error');
//        $element.closest('.field').find('.error-msg').hide();
//    }
//
//    // 회원가입 버튼 클릭 시 유효성 검사 (비밀번호 불일치 시에도 추가)
//    $('button[type="submit"]').on('click', function () {
//        var password = $('#password').val();
//        var confirmPassword = $('#confirmPassword').val();
//
//        if (password !== confirmPassword) {
//            $('#password-error').text('비밀번호가 일치하지 않습니다.'); // 에러 메시지 표시
//            $('#password').addClass('error');
//            $('#confirmPassword').addClass('error');
//            $('#password').focus(); // 비밀번호 입력 필드로 포커스 이동
//        }
//    });
//});

function openPass() {
    window.open('', 'popupChk',
        'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, ' +
        'status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
    document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
    document.form_chk.target = "popupChk";
    document.form_chk.submit();
}

window.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('.phone').addEventListener('click', openPass);
});