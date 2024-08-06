$(function() {
    const activeCategory = $('body').attr('data-active-category');
    const activePage = $('body').attr('data-active-page');
    console.log('Active Page:', activePage); // Debugging line

    $('select').selectric();

    if (activePage) {
        loadPageContent(activePage);
        $('.nav-link[href="' + activeCategory + '"]').addClass('active');
    }

    // 페이지 로드 시 chat의 초기 활성화 상태 설정
    var chatActive = sessionStorage.getItem('chatActive');
    if (chatActive === 'true') {
        $("#chat").show();
        $("#header-item-tooltip-7").addClass("active");
    } else {
        $("#chat").hide();
        $("#header-item-tooltip-7").removeClass("active");
    }

    $("#header-item-tooltip-7").click(function() {
        $("#chat").toggle();
        chatCheckActive();
    });

    function chatCheckActive() {
        var isActive = $("#header-item-tooltip-7").hasClass("active");
        if (isActive) {
            $("#chat").show();
            sessionStorage.setItem('chatActive', 'true'); // 활성화 상태 저장
        } else {
            $("#chat").hide();
            sessionStorage.setItem('chatActive', 'false'); // 비활성화 상태 저장
        }
    }

    window.onpopstate = function() {
        const currentPath = window.location.pathname.split("/").pop();
        loadPageContent(currentPath);
        $('.nav-link').removeClass('active');
        $('.nav-link[href="' + activeCategory + '"]').addClass('active');
    };
});

let chatInitialized = false; // 채팅 초기화 여부 플래그

function initializeChat() {
    if (chatInitialized) return; // 이미 초기화된 경우 리턴

    chatInitialized = true; // 초기화 플래그 설정

    const socket = new SockJS('/chat');
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

            // Update message count
            const currentCount = parseInt(document.querySelector('.title span').textContent);
            updateMessageCount(currentCount + 1);
        } else {
            console.error('Chat layer not found'); // 채팅 레이어 확인
        }
    }

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
                updateMessageCount(messages.length);
                // Ensure scroll to bottom after loading history
                const chatLayer = document.querySelector('#chatingLayer');
                if (chatLayer) {
                    scrollToBottom(chatLayer);
                }
            })
            .catch(error => console.error('Error loading chat history:', error));
    }

    function updateMessageCount(count) {
        document.querySelector('.title span').textContent = count;
    }
}

// 페이지 로드 시 채팅 초기화
$(document).ready(function() {
    initializeChat(); // 페이지가 처음 로드될 때 채팅 초기화
});