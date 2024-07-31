document.addEventListener('DOMContentLoaded', () => {
    console.log('DOMContentLoaded event fired'); // DOMContentLoaded 이벤트 확인

    const socket = new SockJS('http://localhost:8080/chat');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('Connected to server:', frame); // 서버와의 연결 확인

        // Subscribe to the chat messages
        stompClient.subscribe('/topic/chat', (message) => {
            if (message.body) {
                const chatMessage = JSON.parse(message.body);
                displayMessage(chatMessage.sender, chatMessage.content);
            }
        });

        // Set up the send message button
        const sendMessageButton = document.getElementById('sendMessageButton');
        if (sendMessageButton) {
            sendMessageButton.addEventListener('click', sendMessage);
        } else {
            console.error('Send message button not found'); // 버튼 확인
        }

        // Set up the Enter key for sending messages
        const messageInput = document.getElementById('messageInput');
        if (messageInput) {
            messageInput.addEventListener('keydown', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault(); // Prevent default behavior (new line in textarea)
                    sendMessage();
                }
            });
        } else {
            console.error('Message input not found'); // 입력 필드 확인
        }

        // Load previous chat history and scroll to the bottom
        loadChatHistory();
    }, (error) => {
        console.error('STOMP connection error:', error); // 연결 실패 시 에러 로그
    });

    // Function to get the current username
    async function getCurrentUsername() {
        try {
            const response = await fetch('/users/current');
            if (response.ok) {
                const user = await response.json();
                return user.nickname || 'Unknown'; // 닉네임 필드가 있는 경우 반환
            } else {
                console.error('Failed to get username');
                return 'Unknown';
            }
        } catch (error) {
            console.error('Error fetching username:', error);
            return 'Unknown';
        }
    }

    // Function to send a message
    async function sendMessage() {
        const input = document.getElementById('messageInput');
        const message = input ? input.value.trim() : '';
        if (message !== '') {
            const sender = await getCurrentUsername(); // 사용자 이름 가져오기
            const chatMessage = {
                sender: sender,
                content: message
            };
            console.log('Sending message:', chatMessage); // 전송할 메시지 로그
            stompClient.send('/app/send', {}, JSON.stringify(chatMessage));
            if (input) {
                input.value = ''; // Clear the input field
            }
            console.log('Message sent'); // 메시지 전송 후 로그
        }
    }

    // Function to display a chat message
    function displayMessage(nickname, message) {
        const chatLayer = document.querySelector('#chatingLayer');
        if (chatLayer) {
            const messageElement = document.createElement('div');
            messageElement.classList.add('chatLayout', 'flex-row');
            messageElement.innerHTML = `
                <div class="profile"><img class="profile-img-44" src="../static/images/profile/default.png" alt="#"></div>
                <div class="chatContent">
                    <div class="top flex-row flex-c">
                        <div class="nickname grey-4">${nickname}</div>
                        <div class="time grey-1 m-l-10">${new Date().toLocaleTimeString()}</div>
                    </div>
                    <div class="bottom white opacity80 bc-gray-dark">
                        <div class="text">${message}</div>
                    </div>
                </div>
            `;
            chatLayer.appendChild(messageElement);

            // Smooth scroll to the bottom
            scrollToBottom(chatLayer);
        } else {
            console.error('Chat layer not found'); // 채팅 레이어 확인
        }
    }

    // Function to scroll to the bottom of the chat layer
    function scrollToBottom(element) {
        element.scroll({
            top: element.scrollHeight,
            behavior: 'smooth'
        });
    }

    // Function to load previous chat history and scroll to the bottom
    function loadChatHistory() {
        fetch('/chat/history')
            .then(response => response.json())
            .then(messages => {
                messages.forEach(msg => {
                    displayMessage(msg.sender, msg.content);
                });
                // Ensure scroll to bottom after loading history
                const chatLayer = document.querySelector('#chatingLayer');
                if (chatLayer) {
                    scrollToBottom(chatLayer);
                }
            })
            .catch(error => console.error('Error loading chat history:', error));
    }
});
