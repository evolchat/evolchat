$("#header-item-tooltip-7").click(function() {
    $("#chat").toggle();
    chatCheckActive();
});

function chatCheckActive() {
    var isActive = $("#header-item-tooltip-7").hasClass('active');
    if (isActive) {
        $("#chat").show();
        sessionStorage.setItem('chatActive', 'true'); // 활성화 상태 저장
    } else {
        $("#chat").hide();
        sessionStorage.setItem('chatActive', 'false'); // 비활성화 상태 저장
    }
}

if (typeof chatInitialized === 'undefined') {
    var chatInitialized = false; // 채팅 초기화 여부 플래그
}

if (typeof stompClient === 'undefined') {
    var stompClient = null; // 전역 변수로 선언
}

function initializeChat() {
    if (chatInitialized) return; // 이미 초기화된 경우 리턴

    chatInitialized = true; // 초기화 플래그 설정

    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        stompClient.subscribe('/topic/chat', (message) => {
            if (message.body) {
                const chatMessage = JSON.parse(message.body);
                displayMessage(chatMessage.sender, chatMessage.content);
            }
        });

        const messageInput = document.getElementById('messageInput');
        if (messageInput) {
            messageInput.addEventListener('keydown', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    sendMessage();
                }
            });
        } else {
            console.error('Message input not found'); // 입력 필드 확인
        }

        loadChatHistory();
    }, (error) => {
        console.error('STOMP connection error:', error); // 연결 실패 시 에러 로그
    });
}

async function getCurrentUsername() {
    try {
        const response = await fetch('/users/current');
        if (response.ok) {
            const user = await response.json();
            return user.nickname || 'Unknown'; // 닉네임 필드가 있는 경우 반환
        } else {
            Swal.fire({
                title: '회원님의 이름을 가지고 오지 못 했습니다.',
                icon: 'error',
                reverseButtons: true,
                iconColor: '#FF4C4C',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인'
            });
            return 'Unknown';
        }
    } catch (error) {
        Swal.fire({
            title: '회원님의 이름을 가지고 오지 못 했습니다.',
            icon: 'error',
            reverseButtons: true,
            iconColor: '#FF4C4C',
            color: '#FFFFFF',
            background: '#35373D',
            confirmButtonColor: '#8744FF',
            confirmButtonText: '확인'
        });
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
        stompClient.send('/app/send', {}, JSON.stringify(chatMessage));
        if (input) {
            input.value = ''; // Clear the input field
        }
    }
}

function displayMessage(nickname, message) {
    const totalChatContent = document.querySelector('#totalChatContent'); // 전체 채팅에만 표시
    if (totalChatContent) {
        const messageElement = document.createElement('div');
        messageElement.classList.add('chatLayout', 'flex-row');
        messageElement.innerHTML = `
            <div class="profile"><img class="profile-img-44" src="../static/images/profile/default.png" alt="#"></div>
            <div class="chatContent">
                <div class="fs flex-row flex-c">
                    <div class="nickname grey-4 px14">${nickname}</div>
                    <div class="time grey-5 m-l-5 m-t-3 px10">${new Date().toLocaleTimeString()}</div>
                </div>
                <div class="bottom white opacity80 bc-dark-chat">
                    <div class="text">${message}</div>
                </div>
            </div>
        `;
        totalChatContent.appendChild(messageElement);

        const chatingLayer = document.querySelector('#chatingLayer'); // 전체 채팅에만 표시
        scrollToBottom(chatingLayer);

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

function loadChatHistory() {
    fetch('/chat/history')
        .then(response => response.json())
        .then(messages => {
            messages.forEach(msg => {
                displayMessage(msg.sender, msg.content);
            });
            updateMessageCount(messages.length);
            // Ensure scroll to bottom after loading history
            const chatLayer = document.querySelector('#totalChatContent'); // 전체 채팅에만 기록을 불러옴
            if (chatLayer) {
                scrollToBottom(chatLayer);
            }
        })
        .catch(error => console.error('Error loading chat history:', error));
}

function updateMessageCount(count) {
    document.querySelector('#messageCount').textContent = count;
}

initializeChat(); // 페이지가 처음 로드될 때 전체 채팅 초기화

if (typeof chatActive === 'undefined') {
    var chatActive = localStorage.getItem('chatActive');
}

if (chatActive === 'true') {
    $("#chat").show();
    $("#header-item-tooltip-7").addClass('active');
} else {
    $("#chat").hide();
    $("#header-item-tooltip-7").removeClass('active');
}

$("#header-item-tooltip-7").click(function() {
    $("#chat").slideToggle(400, function() {
        const chatVisible = $("#chat").is(":visible");
        localStorage.setItem('chatActive', chatVisible.toString());
        if (chatVisible) {
            $("#header-item-tooltip-7").addClass('active');
        } else {
            $("#header-item-tooltip-7").removeClass('active');
        }
    });
});

// 탭 클릭 핸들러 설정
$('#totalChatTab').click(function() {
    switchChatTab('total');
});

$('#tableChatTab').click(function() {
    switchChatTab('table');
});

$('#alarmChatTab').click(function() {
    switchChatTab('alarm');
});

function switchChatTab(tab) {
    // 모든 탭의 활성 상태 제거
    $('#totalChatTab').removeClass('active');
    $('#tableChatTab').removeClass('active');
    $('#alarmChatTab').removeClass('active');

    // 모든 콘텐츠 영역 숨기기
    $('#totalChatContent').hide();
    $('#tableChatContent').hide();
    $('#alarmChatContent').hide();

    // 선택된 탭에 따라 활성화 및 표시
    if (tab === 'total') {
        $('#totalChatTab').addClass('active');
        $('#totalChatContent').show();
    } else if (tab === 'table') {
        $('#tableChatTab').addClass('active');
        $('#tableChatContent').show();
        $('#tableChatContent').empty(); // 테이블 채팅 클릭 시 빈 채팅창으로 초기화
    } else if (tab === 'alarm') {
        $('#alarmChatTab').addClass('active');
        $('#alarmChatContent').show();
        $('#alarmChatContent').empty(); // 알림 채팅 클릭 시 빈 채팅창으로 초기화
    }
}