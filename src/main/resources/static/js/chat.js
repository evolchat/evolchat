document.addEventListener('DOMContentLoaded', () => {
    const socket = new SockJS('http://localhost:8080/chat');
    const stompClient = Stomp.over(socket);

    // Load chat rooms
    function loadChatRooms() {
        fetch('/chat/rooms')
            .then(response => response.json())
            .then(data => {
                const roomList = document.getElementById('roomList');
                roomList.innerHTML = '';
                for (const [id, room] of Object.entries(data)) {
                    const roomElement = document.createElement('li');
                    roomElement.textContent = room.name;
                    roomElement.addEventListener('click', () => joinChatRoom(id));
                    roomList.appendChild(roomElement);
                }
            });
    }

    // Create new chat room
    document.getElementById('createRoomButton').addEventListener('click', () => {
        const roomName = prompt('Enter room name:');
        if (roomName) {
            fetch('/chat/rooms', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ name: roomName })
            })
            .then(response => response.json())
            .then(data => {
                alert('Room created: ' + data.name);
                loadChatRooms();
            });
        }
    });

    // Join a chat room
    function joinChatRoom(roomId) {
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe(`/chat/${roomId}`, (message) => {
                if (message.body) {
                    const chatMessage = JSON.parse(message.body);
                    displayMessage(chatMessage.sender, chatMessage.content);
                }
            });

            // Handle message sending
            document.getElementById('sendMessageButton').addEventListener('click', () => {
                const input = document.getElementById('messageInput');
                const message = input.value.trim();
                if (message !== '') {
                    const chatMessage = {
                        sender: '사용자', // 실제 사용자 이름으로 대체
                        content: message
                    };
                    stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage));
                    input.value = '';
                }
            });
        });
    }

    // Display message
    function displayMessage(nickname, message) {
        const chatLayer = document.querySelector('.chatingLayer');
        const messageElement = document.createElement('div');
        messageElement.classList.add('chatLayout', 'flex-row');
        messageElement.innerHTML = `
            <div class="profile"><img class="profile-img-44" src="../static/images/profile/default.png" alt="#"></div>
            <div class="chatContent">
                <div class="top flex-row flex-c">
                    <div class="nickname grey-4">${nickname}</div>
                    <div class="time grey-1">${new Date().toLocaleTimeString()}</div>
                </div>
                <div class="bottom white opacity80 bc-gray-dark">
                    <div class="text">${message}</div>
                </div>
            </div>
        `;
        chatLayer.appendChild(messageElement);
        chatLayer.scrollTop = chatLayer.scrollHeight;
    }

    loadChatRooms(); // Load chat rooms on page load
});
