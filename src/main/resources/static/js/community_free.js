$(document).ready(function() {
    const postsPerPage = 10;
    let currentBoardId = 1;
    let currentPage = 1;
    let totalPages = 1;
    let currentSort = 'latest';

    function fetchPosts(page = 1, boardId = currentBoardId, searchQuery = '', sort = currentSort) {
        const url = `/community-posts?page=${page}&size=${postsPerPage}&boardId=${boardId}&search=${encodeURIComponent(searchQuery)}&sort=${sort}`;

        $.ajax({
            url: url,
            type: 'GET',
            success: function(response, status, xhr) {
                renderPosts(response);
                totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                updatePagination(currentPage, totalPages);
            },
            error: function() {
                console.error('게시물을 가져오는 데 실패했습니다.');
            }
        });
    }

    function renderPosts(posts) {
        const postsContainer = $('#posts-container');
        postsContainer.empty();

        if (posts.length === 0) {
            postsContainer.append('<div class="no-posts">검색 결과가 없습니다.</div>');
            $('#comm-free-pagination-container').hide();
            return;
        }

        $('#comm-free-pagination-container').show();

        posts.forEach(post => {
            const postHtml = generatePostHtml(post);
            postsContainer.append(postHtml);
        });
    }

    function generatePostHtml(post) {
        const commentCountHtml = post.commentCount > 0 ? `<span>+${post.commentCount}</span>` : '';
        return `
            <a class="post flex-row tr" data-post-id="${post.postId}" href="/community_detail?boardId=1&postId=${post.postId}">
                <div class="text-wrap">
                    <div class="title white m-b-10 px17">${post.title} ${commentCountHtml}</div>
                    <div class="summary m-b-18 px15">${post.content}</div>
                    <div class="etc">
                        <div class="profile flex-row flex-c m-b-8">
                            <img class="profile-img-24 m-r-10" src="../static/images/profile/default.png" alt="#">
                            <div class="nickname px15">${post.userId}</div>
                        </div>
                        <div class="info px12 flex-c flex-row">
                            <span class="m-r-20">${new Date(post.createdAt).toLocaleString()}</span>
                            <span class="flex-c flex-row m-r-20"><img src="../static/images/svg/input-icon-eye.svg" alt="" class="m-r-4">${post.views}</span>
                            <span class="flex-c flex-row"><img src="../static/images/svg/hearts.svg" alt="" class="m-r-4">${post.likeCount}</span>
                        </div>
                    </div>
                </div>
                <div class="img-wrap">
                    <img src="${post.imageUrl || '../../static/images/svg/logo.svg'}" alt="#" onerror="this.src='../../static/images/svg/logo.svg'">
                </div>
            </a>
        `;
    }

    function updatePagination(currentPage, totalPages) {
        const paginationContainer = $('#comm-free-pagination-container');
        paginationContainer.empty();

        if (currentPage > 3) {
            paginationContainer.append(createPaginationButton('prev-two', '맨 처음 페이지', 1));
        }
        if (currentPage > 1) {
            paginationContainer.append(createPaginationButton('prev', '이전 페이지', currentPage - 1));
        }

        for (let i = Math.max(1, currentPage - 2); i <= Math.min(totalPages, currentPage + 2); i++) {
            const pageDiv = createPaginationButton(i, `${i}`, i);
            if (i === currentPage) {
                pageDiv.addClass('bc-activate');
            }
            paginationContainer.append(pageDiv);
        }

        if (currentPage < totalPages) {
            paginationContainer.append(createPaginationButton('next', '다음 페이지', currentPage + 1));
        }
        if (currentPage < totalPages - 2) {
            paginationContainer.append(createPaginationButton('next-two', '맨 마지막 페이지', totalPages));
        }

        attachPaginationEventListeners();
    }

    function createPaginationButton(dataPage, altText, page) {
        if(isNaN(dataPage)) {
            return $(`<div class="page flex-c-c" data-page="${page}" title="${altText}"><img src="../static/images/svg/${dataPage}.svg" alt="${altText}"></div>`);
        }
        return $(`<div class="page flex-c-c" data-page="${page}" title="${altText}">${dataPage}</div>`);
    }

    function attachPaginationEventListeners() {
        $('.page').off('click').on('click', function() {
            const page = $(this).data('page');
            if (!isNaN(page) && page > 0 && page <= totalPages) {
                currentPage = page;
                fetchPosts(currentPage, currentBoardId, $('#comm-free-search-input').val(), currentSort);
            }
        });
    }

    function handleSearch() {
        fetchPosts(1, currentBoardId, $('#comm-free-search-input').val(), currentSort);
    }

    function setupSortButtons() {
        $('.sort-btn').on('click', function() {
            currentSort = $(this).data('sort');
            fetchPosts(1, currentBoardId, $('#comm-free-search-input').val(), currentSort);
            updateActiveClass($(this));
        });
    }

    function updateActiveClass(activeElement) {
        $('.sorting .sort-btn').removeClass('white active');
        activeElement.addClass('white active');
    }

    // 초기화 함수
    function init() {
        fetchPosts();
        $('#comm-free-search-button').on('click', handleSearch);
        $('#comm-free-search-input').on('keypress', function(event) {
            if (event.which === 13) {
                event.preventDefault();
                handleSearch();
            }
        });
        setupSortButtons();
    }

    // 초기화 실행
    init();
});
