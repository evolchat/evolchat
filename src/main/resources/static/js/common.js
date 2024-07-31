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