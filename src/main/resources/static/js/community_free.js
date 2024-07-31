$(document).ready(function() {
    const postsPerPage = 10; // 페이지당 게시물 수
    let currentBoardId = 1; // 초기 boardId 값 설정

    function fetchPosts(page = 1, boardId = currentBoardId, searchQuery = '') {
        let url = `/community-posts?page=${page}&size=${postsPerPage}&boardId=${boardId}&search=${encodeURIComponent(searchQuery)}`;

        $.ajax({
            url: url,
            type: 'GET',
            success: function(response, status, xhr) {
                const postsContainer = $('#posts-container');
                postsContainer.empty(); // 기존 콘텐츠 제거

                if (response.length === 0) {
                    postsContainer.append('<div class="no-posts">검색 결과가 없습니다.</div>'); // 게시물이 없으면 메시지 표시
                    $('#pagination-container').hide(); // 페이지네이션 숨기기
                    return;
                }

                $('#pagination-container').show(); // 게시물이 있을 때는 페이지네이션 표시

                response.forEach(post => {
                    const postHtml = `
                        <div class="post flex-row tr" data-post-id="${post.postId}">
                            <div class="text-wrap">
                                <div class="title white m-b-10 px17">
                                    ${post.title} <span>+3</span>
                                </div>
                                <div class="summary m-b-18 px15">
                                    ${post.content}
                                </div>
                                <div class="etc">
                                    <div class="profile flex-row flex-c m-b-8">
                                        <img class="profile-img-24 m-r-10" src="../static/images/profile/default.png" alt="#" class="m-r-6">
                                        <div class="nickname px15">${post.userId}</div>
                                    </div>
                                    <div class="info px12 flex-c flex-row">
                                        <span class="m-r-20">${new Date(post.createdAt).toLocaleString()}</span>
                                        <span class="flex-c flex-row m-r-20"><img
                                                src="../static/images/svg/input-icon-eye.svg" alt=""
                                                class="m-r-4">${post.views}</span>
                                        <span class="flex-c flex-row"><img
                                                src="../static/images/svg/hearts.svg" alt=""
                                                class="m-r-4">${post.likeCount}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="img-wrap">
                                <img src="${post.imageUrl || '../../static/images/svg/logo.svg'}" alt="#" onerror="this.src='../../static/images/svg/logo.svg'">
                            </div>
                        </div>
                    `;
                    postsContainer.append(postHtml);
                });

                const totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                updatePagination(page, totalPages, boardId); // boardId 전달
            },
            error: function() {
                console.log('게시물을 가져오는 데 실패했습니다.');
            }
        });
    }

    function updatePagination(currentPage, totalPages, boardId) {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // 기존 페이지네이션 제거

        // 이전 버튼
        const prevButton = $('<div class="prev-btn page flex-c-c" data-page="prev" data-board="' + boardId + '"><img src="../static/images/svg/prev.svg" alt=""></div>');

        // 다음 버튼
        const nextButton = $('<div class="next-btn page flex-c-c" data-page="next" data-board="' + boardId + '"><img src="../static/images/svg/next.svg" alt=""></div>');

        // 현재 페이지에 따라 이전 버튼 표시 여부 설정
        if (currentPage === 1) {
            prevButton.addClass('hidden');
        } else {
            prevButton.removeClass('hidden');
        }

        // 현재 페이지와 총 페이지 수에 따라 다음 버튼 표시 여부 설정
        if (currentPage === totalPages) {
            nextButton.addClass('hidden');
        } else {
            nextButton.removeClass('hidden');
        }

        paginationContainer.append(prevButton);

        // 관련 페이지 번호만 표시
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c" data-page="${i}" data-board="${boardId}">${i}</div>`);
            if (i === currentPage) {
                pageDiv.addClass('bc-activate'); // 현재 페이지에 bc-activate 클래스 추가
            }
            paginationContainer.append(pageDiv);
        }

        paginationContainer.append(nextButton);

        // 이전 두 페이지 버튼
        const prevTwoButton = $('<div class="prev-btn page flex-c-c" data-page="prev-two" data-board="' + boardId + '"><img src="../static/images/svg/prev-two.svg" alt=""></div>');

        // 현재 페이지에 따라 이전 두 페이지 버튼 표시 여부 설정
        if (currentPage <= 2) {
            prevTwoButton.addClass('hidden');
        } else {
            prevTwoButton.removeClass('hidden');
        }

        paginationContainer.prepend(prevTwoButton);

        // 다음 두 페이지 버튼
        const nextTwoButton = $('<div class="next-btn page flex-c-c" data-page="next-two" data-board="' + boardId + '"><img src="../static/images/svg/next-two.svg" alt=""></div>');

        // 현재 페이지와 총 페이지 수에 따라 다음 두 페이지 버튼 표시 여부 설정
        if (currentPage >= totalPages - 1) {
            nextTwoButton.addClass('hidden');
        } else {
            nextTwoButton.removeClass('hidden');
        }

        paginationContainer.append(nextTwoButton);

        attachPaginationEventListeners(); // 페이지네이션 이벤트 리스너 부착
    }

    function attachPaginationEventListeners() {
        $('.page').off('click').on('click', function() { // 중복 바인딩 방지
            let page = $(this).data('page');
            const boardId = $(this).data('board'); // 데이터 속성에서 boardId 가져오기
            const currentPage = parseInt($('.bc-activate').data('page'));

            if (page === 'next') {
                page = currentPage + 1;
            } else if (page === 'prev') {
                page = currentPage - 1;
            } else if (page === 'next-two') {
                page = currentPage + 2;
            } else if (page === 'prev-two') {
                page = currentPage - 2;
            } else {
                page = parseInt(page);
            }

            fetchPosts(page, boardId, $('#search-input').val());
        });
    }

    function handleSearch() {
        const searchQuery = $('#search-input').val();
        fetchPosts(1, currentBoardId, searchQuery); // 첫 페이지부터 검색
    }

    // 초기 게시물 가져오기
    fetchPosts(1, currentBoardId);

    // 검색 버튼 클릭 이벤트
    $('#search-button').on('click', function() {
        handleSearch();
    });

    // 검색 입력 필드에서 Enter 키 눌렀을 때
    $('#search-input').on('keypress', function(event) {
        if (event.which === 13) { // Enter 키 코드
            event.preventDefault();
            handleSearch();
        }
    });

    // 게시물 클릭 이벤트 추가
    $('#posts-container').on('click', '.post', function() {
        const postId = $(this).data('post-id');
        if (postId) {
            window.location.href = `/community_detail?postId=${postId}`;
        }
    });
});
