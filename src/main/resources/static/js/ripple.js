
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