$(document).ready(function () {
    const sendRequestButton = document.getElementById('sendRequestButton');
    const receiverUsernameInput = document.getElementById('receiverUsername');

    function createRequestElement(request) {
        const requestElement = document.createElement('div');
        requestElement.className = 'user-list flex-row flex-c tr';

        // Determine if the current user sent the request or received it
        const isSentByCurrentUser = request.sentByCurrentUser;
        const nicknameToDisplay = isSentByCurrentUser ? request.receiverNickname : request.senderNickname;

        const buttonHtml = isSentByCurrentUser
            ? `<button class="btn" onclick="cancelFriendRequest(${request.id})">친구 요청 취소</button>`
            : `
            <button class="btn" onclick="acceptFriendRequest(${request.id})">친구 수락</button>
            <button class="btn m-l-10" onclick="declineFriendRequest(${request.id})">친구 요청 거절</button>
            `;

        requestElement.innerHTML = `
            <div class="profile">
                <img class="profile-img-52" src="../../static/images/profile/default.png" alt="#">
            </div>
            <div class="user">
                <p class="nickname opacity80">${nicknameToDisplay}</p>
                <p class="text opacity60 px14">${request.message || ''}</p>
            </div>
            ${buttonHtml}
        `;
        return requestElement;
    }

    function loadFriendRequests() {
        fetch('/messenger/friend-requests')
            .then(response => response.json())
            .then(data => {
                const userList = document.getElementById('userList');
                userList.innerHTML = '';

                // Check if data is an array
                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(request => {
                        const requestElement = createRequestElement(request);
                        userList.appendChild(requestElement);
                    });
                } else {
                    userList.innerHTML = '<div class="no-posts">다양한 친구들을 사귀어보세요.</div>';
                }
            })
            .catch(error => {
                console.error('Error loading friend requests:', error);
            });
    }

    // Function to cancel a friend request
    window.cancelFriendRequest = function (requestId) {
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
                        title: '친구 요청 취소 완료',
                        text: '친구 요청이 성공적으로 취소되었습니다.',
                        icon: 'success',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인',
                        background: '#35373D',
                        color: '#FFFFFF'
                    }).then(() => {
                        loadFriendRequests(); // Reload friend requests after canceling one
                    });
                } else {
                    Swal.fire({
                        title: '친구 요청 취소 실패',
                        text: result.message || '친구 요청 취소에 실패했습니다.',
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
    };

    // Function to accept a friend request
    window.acceptFriendRequest = function (requestId) {
        fetch('/messenger/accept-request', {
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
                        title: '친구 수락 완료',
                        text: '친구 요청을 수락했습니다.',
                        icon: 'success',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인',
                        background: '#35373D',
                        color: '#FFFFFF'
                    }).then(() => {
                        loadFriendRequests(); // Reload friend requests after accepting one
                    });
                } else {
                    Swal.fire({
                        title: '친구 수락 실패',
                        text: result.message || '친구 요청 수락에 실패했습니다.',
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
                console.error('Error accepting friend request:', error);
            });
    };

    // Function to decline a friend request
    window.declineFriendRequest = function (requestId) {
        fetch('/messenger/decline-request', {
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
                        title: '친구 요청 거절 완료',
                        text: '친구 요청이 성공적으로 거절되었습니다.',
                        icon: 'success',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인',
                        background: '#35373D',
                        color: '#FFFFFF'
                    }).then(() => {
                        loadFriendRequests(); // Reload friend requests after declining one
                    });
                } else {
                    Swal.fire({
                        title: '친구 요청 거절 실패',
                        text: result.message || '친구 요청 거절에 실패했습니다.',
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
                console.error('Error declining friend request:', error);
            });
    };

    // Load friend requests when the page loads
    loadFriendRequests();

    // Event listener for sending a friend request
    if (sendRequestButton) {
        sendRequestButton.addEventListener('click', function () {
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
        });
    }

    // Handle Enter key press to trigger friend request sending
    receiverUsernameInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendRequestButton.click();
        }
    });
});
