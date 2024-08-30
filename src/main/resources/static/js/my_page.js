//$(document).ready(function () {
//    // 프로필 이미지 업로드 미리보기
//    $('#profile').change(function () {
//        const file = this.files[0];
//        const img = document.querySelector('.profileimg img');
//
//        if (file) {
//            const reader = new FileReader();
//            reader.onload = function (e) {
//                img.src = e.target.result;
//            }
//            reader.readAsDataURL(file);
//        } else {
//            img.src = '../../static/images/profile/default.png';
//        }
//    });
//
//    // 마이홈 배경 이미지 업로드 미리보기
//    $('#background').change(function () {
//        const file = this.files[0];
//        const img = document.querySelector('.backgroundimg img');
//
//        if (file) {
//            const reader = new FileReader();
//            reader.onload = function (e) {
//                img.src = e.target.result;
//            }
//            reader.readAsDataURL(file);
//        } else {
//            img.src = '../../static/images/profile/NoPath%20-%20복사본%20(58).png';
//        }
//    });
//
//    // 최대 5개 관심사 선택 기능
//    const checkboxes = document.querySelectorAll('.interest-checkbox');
//
//    checkboxes.forEach(checkbox => {
//        checkbox.addEventListener('change', function () {
//            const checkedCount = document.querySelectorAll('.interest-checkbox:checked').length;
//
//            if (checkedCount > 5) {
//                this.checked = false;
//                Swal.fire({
//                    icon: "error",
//                    title: "최대 5개까지만 선택할 수 있습니다.",
//                    color: '#FFFFFF',
//                    background: '#35373D'
//                });
//            }
//        });
//    });
//
//    // "닉네임 검색" 입력 필드에서 글자 수 업데이트
//    const nicknameSearchInput = document.getElementById('nicknameSearch');
//    const nicknameSearchCount = document.getElementById('nicknameSearch-count');
//
//    nicknameSearchInput.addEventListener('input', function () {
//        nicknameSearchCount.textContent = `${nicknameSearchInput.value.length} / 12`;
//    });
//
//    // "오늘의 한마디" 입력 필드에서 글자 수 업데이트
//    const todaysMessageInput = document.getElementById('todaysMessage');
//    const todaysMessageCount = document.getElementById('todaysMessage-count');
//
//    todaysMessageInput.addEventListener('input', function () {
//        todaysMessageCount.textContent = `${todaysMessageInput.value.length} / 60`;
//    });
//
//    // 버튼 클릭 시 등록권 확인 및 알림
//    $('#profileUploadButton').click(function () {
//        const uploadTicketCount = parseInt($('#uploadTicketCount').text(), 10);
//
//        if (uploadTicketCount <= 0) {
//            Swal.fire({
//                title: '프로필 사진 등록권이 없습니다!',
//                text: "프로필 사진을 등록하려면 등록권이 필요합니다.",
//                icon: 'info',
//                reverseButtons: true,
//                iconColor: '#8744FF',
//                color: '#FFFFFF',
//                background: '#35373D',
//                confirmButtonColor: '#8744FF',
//                confirmButtonText: '확인',
//            });
//        } else {
//            $('#profile').click(); // 등록권이 있으면 파일 선택 창을 열기
//        }
//    });
//
//    $('#backgroundUploadButton').click(function () {
//        const myHomeTicketCount = parseInt($('#myHomeTicketCount').text(), 10);
//
//        if (myHomeTicketCount <= 0) {
//            Swal.fire({
//                title: '마이홈 배경 등록권이 없습니다!',
//                text: "마이홈 배경을 등록하려면 등록권이 필요합니다.",
//                icon: 'info',
//                reverseButtons: true,
//                iconColor: '#8744FF',
//                color: '#FFFFFF',
//                background: '#35373D',
//                confirmButtonColor: '#8744FF',
//                confirmButtonText: '확인',
//            });
//        } else {
//            $('#background').click(); // 등록권이 있으면 파일 선택 창을 열기
//        }
//    });
//});
