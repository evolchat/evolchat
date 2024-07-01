$(document).ready(function(){
    $("#header").load("/header");
    $("#nav").load("/nav");
    $("#chat").load("/chat");
});

function logout() {
    window.location.href = '/logout';
    alert('정상적으로 로그아웃 하였습니다.');
}