document.addEventListener("DOMContentLoaded", function () {
    const sendRequestButton = document.getElementById('sendRequestButton');
    const receiverUsernameInput = document.getElementById('receiverUsername');

    // Function to create a new friend request element
    function createRequestElement(request) {
        const requestElement = document.createElement('div');
        requestElement.className = 'user-list flex-row flex-c tr';
        requestElement.innerHTML = `
            <div class="profile">
                <img class="profile-img-52" src="../../static/images/profile/default.png" alt="#">
            </div>
            <div class="user">
                <p class="nickname opacity80">${request.nickname}</p>
                <p class="text opacity60 px14">${request.message || ''}</p>
            </div>
            <button class="btn" onclick="cancelRequest(${request.id})">친구 요청 취소</button>
        `;
        return requestElement;
    }

    // Function to fetch and display friend requests
    function loadFriendRequests() {
        fetch('/messenger/friend-requests')
            .then(response => response.json())
            .then(data => {
                const userList = document.getElementById('userList');
                userList.innerHTML = '';
                data.forEach(request => {
                    userList.innerHTML += `
                        <div class="user-list flex-row flex-c tr">
                            <div class="profile"><img class="profile-img-52" src="../../static/images/profile/default.png" alt="#"></div>
                            <div class="user">
                                <p class="nickname opacity80">${request.nickname}</p>
                                <p class="text opacity60 px14">${request.message || ''}</p>
                            </div>
                            <button class="btn" data-id="${request.id}">친구 요청 취소</button>
                        </div>
                    `;
                });
            });
    }

    // Function to send a friend request
    function sendFriendRequest() {
        const receiverUsername = receiverUsernameInput.value.trim();
        if (!receiverUsername) {
            Swal.fire({
                title: '닉네임 입력 오류',
                text: '닉네임을 입력해주세요.',
                icon: 'warning',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인',
                background: '#35373D',
                color: '#FFFFFF'
            });
            return;
        }

        fetch('/messenger/send-request', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                receiverUsername: receiverUsername
            })
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                Swal.fire({
                    title: '친구 요청 완료!',
                    text: '친구 요청이 성공적으로 전송되었습니다.',
                    icon: 'success',
                    confirmButtonColor: '#8744FF',
                    confirmButtonText: '확인',
                    background: '#35373D',
                    color: '#FFFFFF'
                }).then(() => {
                    loadFriendRequests();
                    receiverUsernameInput.value = ''; // Clear the input field
                });
            } else {
                Swal.fire({
                    title: '친구 요청 실패',
                    text: result.message || '친구 요청에 실패했습니다.',
                    icon: 'error',
                    confirmButtonColor: '#8744FF',
                    confirmButtonText: '확인',
                    background: '#35373D',
                    color: '#FFFFFF'
                });
            }
        })
        .catch(error => {
            Swal.fire({
                title: '오류 발생',
                text: '요청 처리 중 오류가 발생했습니다.',
                icon: 'error',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인',
                background: '#35373D',
                color: '#FFFFFF'
            });
            console.error('Error sending friend request:', error);
        });
    }

    // Function to cancel a friend request
    window.cancelRequest = function (requestId) {
        Swal.fire({
            title: '친구 요청 취소',
            text: '정말로 이 친구 요청을 취소하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#8744FF',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            background: '#35373D',
            color: '#FFFFFF'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch('/messenger/cancel-request', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: new URLSearchParams({
                        requestId: requestId
                    })
                })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        Swal.fire({
                            title: '요청 취소 완료',
                            text: '친구 요청이 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#8744FF',
                            confirmButtonText: '확인',
                            background: '#35373D',
                            color: '#FFFFFF'
                        }).then(() => {
                            loadFriendRequests(); // Reload friend requests
                        });
                    } else {
                        Swal.fire({
                            title: '요청 취소 실패',
                            text: result.message || '요청 취소에 실패했습니다.',
                            icon: 'error',
                            confirmButtonColor: '#8744FF',
                            confirmButtonText: '확인',
                            background: '#35373D',
                            color: '#FFFFFF'
                        });
                    }
                })
                .catch(error => {
                    Swal.fire({
                        title: '오류 발생',
                        text: '요청 처리 중 오류가 발생했습니다.',
                        icon: 'error',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인',
                        background: '#35373D',
                        color: '#FFFFFF'
                    });
                    console.error('Error canceling friend request:', error);
                });
            }
        });
    };

    // Event listener for the send request button
    sendRequestButton.addEventListener('click', sendFriendRequest);

    // Focus the input field when the page loads
    receiverUsernameInput.focus();

    // Handle Enter key press to trigger friend request sending
    receiverUsernameInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendFriendRequest();
        }
    });

    // Load friend requests when the page loads
    loadFriendRequests();

    function loadFriendList() {
        fetch('/messenger/friends')
            .then(response => response.json())
            .then(data => {
                const userList = document.getElementById('userList');
                userList.innerHTML = '';
                data.forEach(friend => {
                    userList.innerHTML += `
                        <div class="user-list flex-row flex-c tr">
                            <div class="profile"><img class="profile-img-52" src="../../static/images/profile/default.png" alt="#"></div>
                            <div class="user">
                                <p class="nickname opacity80">${friend.nickname}</p>
                                <p class="text opacity60 px14">현재 페이지: ${friend.currentPage}</p>
                            </div>
                        </div>
                    `;
                });
            });
    }

    // 페이지 로드 시 친구 목록을 불러옵니다.
    document.addEventListener("DOMContentLoaded", function () {
        loadFriendList();
    });
});
