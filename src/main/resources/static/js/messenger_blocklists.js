document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById('search-input');
    const blocklistContainer = document.getElementById('blocklist-container');

    function fetchBlocklist(query = '') {
        fetch(`/messenger/blocked-users?query=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    renderBlocklist(data.blockedUsers);
                } else {
                    blocklistContainer.innerHTML = '<p>차단 목록을 가져오는 데 실패했습니다.</p>';
                }
            })
            .catch(error => {
                console.error('차단 목록을 가져오는 중 오류 발생:', error);
                blocklistContainer.innerHTML = '<p>차단 목록을 가져오는 데 실패했습니다.</p>';
            });
    }

    function renderBlocklist(blockedUsers) {
        blocklistContainer.innerHTML = '';
        blockedUsers.forEach(user => {
            const userElement = document.createElement('div');
            userElement.className = 'user-list flex-row flex-c tr';
            userElement.innerHTML = `
                <div class="profile"><img src="${user.profileImg || '../../static/images/profile/default.png'}" alt="#"></div>
                <div class="user">
                    <p class="nickname opacity80">${user.nickname}</p>
                    ${user.status ? `<p class="text opacity60 px14">${user.status}</p>` : ''}
                </div>
                <button class="btn" data-user-id="${user.id}">차단 해제</button>
            `;
            blocklistContainer.appendChild(userElement);
        });
    }

    function filterBlocklist(query) {
        const users = blocklistContainer.querySelectorAll('.user-list');
        users.forEach(user => {
            const nickname = user.querySelector('.nickname').textContent.toLowerCase();
            if (nickname.includes(query.toLowerCase())) {
                user.style.display = '';
            } else {
                user.style.display = 'none';
            }
        });
    }

    searchInput.addEventListener('input', (event) => {
        fetchBlocklist(event.target.value);
    });

    // 초기 차단 목록 가져오기
    fetchBlocklist();
});
