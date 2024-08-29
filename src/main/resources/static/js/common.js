$(document).ready(function(){
//    $("#header").load("/header");
//    $("#nav").load("/nav");
//    $("#chat").load("/chat");
});

function logout() {
    Swal.fire({
        title: '정상적으로 로그아웃 하시겠습니까?',
        text: "로그아웃 후에는 다시 로그인해야 합니다.",
        icon: 'question',
        showCancelButton: true,
        reverseButtons: true,
        iconColor: '#8744FF',
        color: '#FFFFFF',
        background: '#35373D',
        confirmButtonColor: '#8744FF',
        cancelButtonColor: '#535560',
        confirmButtonText: '로그아웃',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/logout';
        }
    });
}

function serviceOpen() {
    Swal.fire({
        title: '서비스 준비 중인 기능입니다 !',
        text: "더 나은 서비스를 제공하겠습니다 !",
        icon: 'info',
        reverseButtons: true,
        iconColor: '#8744FF',
        color: '#FFFFFF',
        background: '#35373D',
        confirmButtonColor: '#8744FF',
        confirmButtonText: '확인',
    });
}

$(document.body).on('click', function(e) {
    const target = e.target;

    // 물결 효과가 필요한 요소 필터링: 'ripple' 클래스를 가진 요소만
    if (target.classList.contains('ripple')) {
        // target이 이미 position: relative가 아닌 경우 추가
        if (getComputedStyle(target).position === 'static') {
            target.style.position = 'relative';
        }

        // overflow: hidden을 동적으로 추가
        target.style.overflow = 'hidden';

        // 기존의 물결 효과 제거
        const existingRipple = target.querySelector('.ripple-effect');
        if (existingRipple) {
            existingRipple.remove();
        }

        // 물결 효과 요소 생성
        const ripple = document.createElement('span');
        ripple.classList.add('ripple-effect');

        // 물결 크기 및 위치 계산
        const rect = target.getBoundingClientRect();
        const size = Math.max(rect.width, rect.height);
        ripple.style.width = ripple.style.height = `${size}px`;
        ripple.style.left = `${e.clientX - rect.left - size / 2}px`;
        ripple.style.top = `${e.clientY - rect.top - size / 2}px`;

        // 물결 효과 추가 및 애니메이션 후 제거
        target.appendChild(ripple);
        setTimeout(() => ripple.remove(), 600);
    }
});