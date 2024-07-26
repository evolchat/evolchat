$(document).ready(function() {
    const postsPerPage = 10; // 페이지당 게시물 수
    let currentPage = 1; // 현재 페이지 번호

    // 게시물과 페이지네이션을 가져오는 함수
    function fetchPosts(page = 1) {
        $.ajax({
            url: `/posts?page=${page}&size=${postsPerPage}&boardId=3`, // boardId=3인 게시물 요청
            type: 'GET',
            success: function(response, status, xhr) {
                const postsContainer = $('.mainLayer');
                postsContainer.empty(); // 기존 게시물 항목 제거

                if (response.length === 0) {
                    postsContainer.append('<div class="no-posts">게시물이 없습니다.</div>'); // 게시물이 없으면 메시지 표시
                    $('#pagination-container').hide(); // 페이지네이션 숨기기
                    return;
                }

                $('#pagination-container').show(); // 게시물이 있을 때 페이지네이션 표시

                response.forEach(post => {
                    const postHtml = `
                        <a href="community_detail?postId=${post.postId}">
                            <div class="video-container">
                                <img class="img" src="${post.imageUrl || '../../static/images/svg/logo.svg'}" alt="Video Thumbnail">
                                <div class="video-details">
                                    <h3>${post.title}</h3>
                                    <p><img class="mini" src="${post.profileImageUrl || '../../static/images/svg/minister.svg'}" alt=""> &nbsp ${post.userId}</p>
                                    <p class="meta">${new Date(post.createdAt).toLocaleString()} &nbsp &nbsp <img src="../../static/images/svg/input-icon-eye.svg" alt="">${post.views} &nbsp &nbsp <img src="../../static/images/svg/hearts.svg" alt="">${post.likeCount}</p>
                                </div>
                            </div>
                        </a>
                    `;
                    postsContainer.append(postHtml);
                });

                const totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                updatePagination(page, totalPages); // 페이지네이션 업데이트
            },
            error: function() {
                console.log('게시물을 가져오는 데 실패했습니다.');
            }
        });
    }

    // 페이지네이션을 업데이트하는 함수
    function updatePagination(currentPage, totalPages) {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // 기존 페이지네이션 제거

        // 이전 버튼
        const prevButton = $('<div class="prev-btn page flex-c-c" data-page="prev"><img src="../../static/images/svg/prev.svg" alt=""></div>');

        // 다음 버튼
        const nextButton = $('<div class="next-btn page flex-c-c" data-page="next"><img src="../../static/images/svg/next.svg" alt=""></div>');

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

        // 현재 페이지에 대한 페이지 번호만 표시
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c" data-page="${i}">${i}</div>`);
            if (i === currentPage) {
                pageDiv.addClass('bc-activate'); // 현재 페이지에 bc-activate 클래스 추가
            }
            paginationContainer.append(pageDiv);
        }

        paginationContainer.append(nextButton);

        attachPaginationEventListeners(); // 페이지네이션 이벤트 리스너 부착
    }

    // 페이지네이션 클릭 이벤트 리스너 부착
    function attachPaginationEventListeners() {
        $('.page').off('click').on('click', function() { // 중복 바인딩 방지
            const page = $(this).data('page');
            if (page === 'next') {
                fetchPosts(currentPage + 1);
            } else if (page === 'prev') {
                fetchPosts(currentPage - 1);
            } else {
                fetchPosts(parseInt(page));
            }
        });
    }

    // 초기 게시물 가져오기
    fetchPosts(currentPage);

    // 게시물 클릭 이벤트 추가
    $('.mainLayer').on('click', '.video-container', function() {
        const postId = $(this).closest('a').attr('href').split('?postId=')[1];
        if (postId) {
            window.location.href = `/community_detail?postId=${postId}`;
        }
    });
});
