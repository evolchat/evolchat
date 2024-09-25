$(document).ready(function() {
    // Apply Tippy.js to all icons in the user list
    function initializeTooltips() {
        document.querySelectorAll('.user-list .btn-wrap .icon').forEach(function (icon) {
            tippy(icon, {
                content: icon.querySelector('.hidden').textContent,
                placement: 'top',
                animation: 'fade',
                onShow(instance) {
                    // 툴팁의 루트 요소에 접근하여 스타일을 직접 변경
                    instance.popper.querySelector('.tippy-box').style.backgroundColor = '#000000'; // 배경색 설정
                    instance.popper.querySelector('.tippy-box').style.color = 'white'; // 텍스트 색상 설정
                    instance.popper.querySelector('.tippy-arrow').style.color = '#000000'; // 화살표 색상 설정
                }
            });
        });
    }

    function loadFriends(query = '') {
        fetch(`/messenger/friends?query=${encodeURIComponent(query)}`) // 서버에서 친구 리스트를 가져오는 API 엔드포인트
            .then(response => response.json())
            .then(data => {
                const userListLayer = document.getElementById('user-list-layer');
                const noFriendsMessage = document.getElementById('no-friends-message');
                userListLayer.innerHTML = ''; // 기존 목록 비우기

                if (data.success && Array.isArray(data.friends)) {
                    noFriendsMessage.style.display = 'none'; // 친구가 있으면 메시지 숨기기
                    data.friends.forEach(friend => {
                        const userItem = document.createElement('div');
                        userItem.className = 'user-list flex-row flex-c tr';
                        userItem.innerHTML = `
                            <div class="profile"><img class="profile-img-52" src="${friend.profileImg || '../../static/images/profile/default.png'}" alt="#"></div>
                            <div class="user">
                                <p class="nickname opacity80">${friend.nickname}</p>`;
                        if(friend.status) {
                            userItem.innerHTML += `<p class="text opacity60 px14">${friend.status}</p>`;
                        }
                        userItem.innerHTML += `
                            </div>
                            <div class="btn-wrap flex-row flex-c">
                                <div onclick="setFriendFin(event)" class="icon flex-c-c cursor-p"><img src="../../static/images/svg/pins.svg" alt="" style="height: auto; width: auto;" />
                                    <div class="hidden">고정</div>
                                </div>
                                <div class="icon flex-c-c cursor-p"><img src="../../static/images/svg/home.svg" alt="" style="height: auto; width: auto;" />
                                    <div class="hidden">홈피</div>
                                </div>
                                <div onclick="messengerFriendMsgSend(event)" class="icon flex-c-c cursor-p chat-button" data-user-id="${friend.friendId}"><img src="../../static/images/svg/chat.svg" alt="" style="height: auto; width: auto;" />
                                    <div class="hidden">메시지보내기</div>
                                </div>
                                <div class="icon flex-c-c cursor-p"><img src="../../static/images/svg/hyphenation.svg" alt="" style="height: auto; width: auto;" />
                                    <div class="hidden">더보기</div>
                                </div>
                            </div>
                        `;
                        userListLayer.appendChild(userItem);
                    });
                    initializeTooltips(); // 툴팁 초기화
                } else {
                    noFriendsMessage.style.display = 'block'; // 데이터 형식이 올바르지 않거나 친구가 없는 경우 메시지 표시
                }
            })
            .catch(error => {
                console.error('Error fetching friends list:', error);
                const noFriendsMessage = document.getElementById('no-friends-message');
                noFriendsMessage.style.display = 'block'; // 에러 발생 시 메시지 표시
            });
    }

    loadFriends(); // 페이지 로드 시 친구 리스트 불러오기

    $('#search-input').on('input', function() {
        var query = $(this).val();
        loadFriends(query); // 검색 쿼리를 사용하여 친구 리스트를 다시 불러오기
    });
});

function showChatRoom(chatRoomId) {
    $.get('/friend-chat/user-chats', function(data) {
        const recentMessagesContainer = $('.recentMessages');
        recentMessagesContainer.empty();

        data.forEach(chatRoom => {
            let recentMessageItem = `
                <li class="grey-1">
                    <div class="flex-row flex-c chat-toggle" data-chat-room-id="${chatRoom.id}">
                        <img class="profile-img-36" src="../static/images/profile/default.png" />
                        <div>
                            <div class="receive">
                                <p class="white px12 m-l-10">${chatRoom.roomName}</p>
                                <span class="notice px12">${chatRoom.unreadCount}</span>
                            </div>`;
                            recentMessageItem += `</div></div></li>`
            recentMessagesContainer.append(recentMessageItem);
        });
    });
}

function messengerFriendMsgSend(event) {
    var chatButton = event.target.closest('.chat-button');
    var userId = chatButton.dataset.userId;
    fetch('/friend-chat/create-chat-room', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            user2Id: userId
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.id) {
            // 채팅방 생성 성공 시 채팅방 표시
            showChatRoom(data.id);
        } else {
            console.error('Failed to create chat room');
        }
    })
    .catch(error => {
        console.error('Error creating chat room:', error);
    });
}

function setFriendFin(event) {
    if (event.target.closest('.user-list .btn-wrap .icon:nth-of-type(1)')) {
        var icon = event.target.closest('.user-list .btn-wrap .icon:nth-of-type(1)');
        var img = icon.querySelector('img');
        var userItem = icon.closest('.user-list');
        var userListLayer = document.querySelector('#user-list-layer');
        var pinsSrc = '../../static/images/svg/pins.svg';
        var goldPinsSrc = '../../static/images/svg/goldpins.svg';

        var imgSrc = new URL(img.src, window.location.href).pathname;
        var pinsPath = new URL(pinsSrc, window.location.href).pathname;
        var goldPinsPath = new URL(goldPinsSrc, window.location.href).pathname;

        if (imgSrc === pinsPath) {
            img.src = goldPinsSrc;
            userListLayer.prepend(userItem);
        } else {
            img.src = pinsSrc;
        }
    }
}
