$(function() {
    const activeCategory = $('body').attr('data-active-category');
    const activePage = $('body').attr('data-active-page');
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
        $("#header-item-tooltip-7").removeClass('active');
    }

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

    // Function to display a chat message
    function displayMessage(nickname, message) {
        const totalChatContent = document.querySelector('#totalChatContent'); // 전체 채팅에만 표시
        if (totalChatContent) {
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
}

// 페이지 로드 시 채팅 초기화
$(document).ready(function() {
    initializeChat(); // 페이지가 처음 로드될 때 전체 채팅 초기화

     // Handle chat visibility persistence
    const chatActive = localStorage.getItem('chatActive');
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

    if (localStorage.getItem('menuBtnState') === 'active') {
        $(".menu-btn").addClass('active');
    }
    $(".menu-btn-layer").css('visibility', 'visible');

    $("#nav_button").click(function() {
        $(".menu-btn").toggleClass('active');  // 햄버거 메뉴 버튼에 active 클래스 추가/제거

        // 버튼 상태를 localStorage에 저장
        if ($(".menu-btn").hasClass('active')) {
            localStorage.setItem('menuBtnState', 'active');
        } else {
            localStorage.removeItem('menuBtnState');
        }
    });
});

$(function() {
    // 탭 클릭 핸들러 설정
    $('#totalChatTab').click(function() {
        switchChatTab('total');
    });

    $('#tableChatTab').click(function() {
        switchChatTab('table');
    });
});

function switchChatTab(tab) {
    if (tab === 'total') {
        $('#totalChatTab').addClass('active');
        $('#tableChatTab').removeClass('active');
        $('#totalChatContent').show();
        $('#tableChatContent').hide();
    } else if (tab === 'table') {
        $('#totalChatTab').removeClass('active');
        $('#tableChatTab').addClass('active');
        $('#totalChatContent').hide();
        $('#tableChatContent').show();
        $('#tableChatContent').empty(); // 테이블 채팅 클릭 시 빈 채팅창으로 초기화
    }
}