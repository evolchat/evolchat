$(document).ready(function(){
//    $("#header").load("/header");
//    $("#nav").load("/nav");
//    $("#chat").load("/chat");
});

function logout() {
    window.location.href = '/logout';
    alert('정상적으로 로그아웃 되었습니다.');
}

function serviceOpen() {
    alert('서비스 준비 중인 기능입니다!');
}