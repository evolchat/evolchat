$(document).ready(function() {
    function loadChatRooms() {
        fetch('/chatrooms')
            .then(response => response.json())
            .then(chatRooms => {
                const roomLayer = document.querySelector('.room-layer');
                roomLayer.innerHTML = ''; // 기존 채팅방 내용 지우기

                if (chatRooms.length === 0) {
                    roomLayer.innerHTML = '<div class="no-rooms">오픈채팅방이 없습니다.</div>'; // 데이터가 없을 경우 메시지 표시
                    document.querySelector('#pagination-container').style.display = 'none'; // 페이지네이션 숨기기
                    return;
                }

                chatRooms.forEach(room => {
                    const roomElement = document.createElement('div');
                    roomElement.className = 'room';

                    roomElement.innerHTML = `
                        <div class="photo-layout">
                            <div class="img">
                                <img src="../../static/images/photo/photo_1.jpg" alt="#">
                            </div>
                        </div>
                        <div class="text-wrap position-r">
                            <div class="position-a">
                                <img class="profile-img-52" src="../../static/images/profile/default.png" alt="#">
                            </div>
                            <div class="v-h"></div>
                            <div class="top">
                                <div class="tit-wrap white px17 m-b-10">
                                    ${room.name}
                                </div>
                                <div class="summary px14">
                                    ${room.description}
                                </div>
                            </div>
                            <div class="v-h"></div>
                            <div class="bottom flex-c">
                                <img src="../../static/images/svg/people.svg" alt="" style="height: auto; width: auto;" class="m-r-4"/>
                                <span class="opacity60 px14 m-r-14">${room.participants}</span>
                                <img src="../../static/images/svg/hearts.svg" alt="" style="height: auto; width: auto;" class="m-r-4"/>
                                <span class="opacity60 px14 m-r-14">${room.likes}</span>
                                <img src="../../static/images/svg/star.svg" alt="" style="height: auto; width: auto;" class="m-r-4"/>
                                <span class="opacity60 px14 m-r-14">${room.stars}</span>
                            </div>
                        </div>
                    `;

                    roomLayer.appendChild(roomElement);
                });
            })
            .catch(error => console.error('Error loading chat rooms:', error));
    }

    loadChatRooms(); // 페이지 로드 시 채팅방 목록 불러오기
});
