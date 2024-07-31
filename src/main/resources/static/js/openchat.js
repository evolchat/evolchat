document.addEventListener('DOMContentLoaded', () => {
    function loadChatRooms() {
        fetch('/chatrooms')
            .then(response => response.json())
            .then(chatRooms => {
                const roomLayer = document.querySelector('.room-layer');
                roomLayer.innerHTML = ''; // Clear existing rooms

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

    loadChatRooms(); // Load chat rooms on page load
});
