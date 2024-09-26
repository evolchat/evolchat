$(document).ready(function() {
    function loadMessengerRecentMessages() {
        $.get('/friend-chat/user-chats', function(data) {
            const mainLayerContainer = $('.user-list-layer');
            mainLayerContainer.empty();  // 기존 메시지 목록 초기화

            data.forEach(chatRoom => {
                $.get('/friend-chat/unread-count/' + chatRoom.id, function(unreadCount) {

                    let recentMessageItem = `<div onclick="openMessageItem(event)" class="user-list flex-row flex-c tr" data-chat-room-id="${chatRoom.id}">
                        <div class="profile position-r" onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}"><img onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}" class="profile-img-52" src="../../static/images/profile/default.png"
                                alt="#"></div>
                        <div class="user" onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}">
                            <p class="nickname opacity80 m-b-6" onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}">${chatRoom.roomName}`
                    if(unreadCount != 0) {
                        recentMessageItem += `<span class="bc-ivory text-center px14">${unreadCount}</span></p>`;
                    }

                    if (chatRoom.recentMessages && chatRoom.recentMessages.length > 0) {
                        recentMessageItem += `<p class="text opacity60 px14" onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}">${chatRoom.recentMessages[chatRoom.recentMessages.length - 1].message || ""}</p>`;
                    }

                  recentMessageItem += `
                        </div>
                        <div class="flex-row flex-c" onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}">`

                  if (chatRoom.recentMessages && chatRoom.recentMessages.length > 0) {
                    recentMessageItem += `<span class="opacity40 m-r-14">${formatTime(chatRoom.recentMessages[chatRoom.recentMessages.length - 1].timestamp)}</span>`
                  }
                      recentMessageItem +=`
                                <button class="btn flex-c-c hyphenation"><img onclick="openMessageItem(event)" data-chat-room-id="${chatRoom.id}"
                                        src="../../static/images/svg/hyphenation.svg" alt=""
                                        style="height: auto; width: auto;" /></button>
                            </div>
                      </div>`;

                    mainLayerContainer.append(recentMessageItem);
                });
            });
        });
    }

    loadMessengerRecentMessages();  // 메시지 목록을 로드

    function formatTime(timestamp) {

      const date = new Date(timestamp);
      const hours = date.getHours();
      const minutes = date.getMinutes();
      return `${hours > 12 ? hours - 12 : hours}:${minutes < 10 ? '0' + minutes : minutes} ${hours >= 12 ? '오후' : '오전'}`;
    }

    document.querySelectorAll('.mainLayer .btn.flex-c-c.hyphenation').forEach(function (btn) {
        tippy(btn, {
            content: '기타',
            placement: 'top',
            animation: 'fade',
            onShow(instance) {
                instance.popper.querySelector('.tippy-box').style.backgroundColor = '#000000'; // 배경색 설정
                instance.popper.querySelector('.tippy-box').style.color = 'white'; // 텍스트 색상 설정
                instance.popper.querySelector('.tippy-arrow').style.color = '#000000'; // 화살표 색상 설정
            }
        });
    });
});

function openMessageItem(event) {
    console.log(event)
    const currentChatRoomId = event.target.getAttribute('data-chat-room-id'); // chat-toggle에 chat-room-id를 저장했다고 가정
    console.log(currentChatRoomId)
    const chatElement = $(`.chat-toggle[data-chat-room-id="${currentChatRoomId}"]`);
    chatElement.click();
}
