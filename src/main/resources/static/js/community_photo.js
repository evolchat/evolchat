$(document).ready(function() {
    const postsPerPage = 10; // Number of posts per page

    function fetchPosts(page = 1, boardId = null) {
        let url = `/posts?page=${page}&size=${postsPerPage}`;
        if (boardId) {
            url += `&boardId=${boardId}`;
        }

        $.ajax({
            url: url,
            type: 'GET',
            success: function(response, status, xhr) {
                const postsContainer = $('#posts-container');
                postsContainer.empty(); // Clear any existing content

                if (response.length === 0) {
                    $('#pagination-container').hide(); // Hide pagination if no posts
                    return;
                }

                response.forEach(post => {
                    const postHtml = `
                        <div class="post flex-row tr">
                            <div class="text-wrap">
                                <div class="title white m-b-10 px17">
                                    ${post.title} <span>+3</span>
                                </div>
                                <div class="summary m-b-18 px15">
                                    ${post.content}
                                </div>
                                <div class="etc">
                                    <div class="profile flex-row flex-c m-b-8">
                                        <img src="../static/images/profile/user1.png" alt="#" class="m-r-6">
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
                                <img src="../static/images/photo/photo_1.jpg" alt="123123.png">
                            </div>
                        </div>
                    `;
                    postsContainer.append(postHtml);
                });

                const totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                updatePagination(page, totalPages, boardId); // Pass boardId to updatePagination
            },
            error: function() {
                console.log('Failed to fetch posts');
            }
        });
    }

    function updatePagination(currentPage, totalPages, boardId) {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // Clear existing pagination

        // Previous button as div element
        const prevButton = $('<div class="prev-btn page flex-c-c" data-page="prev" data-board="' + boardId + '"><img src="../static/images/svg/prev.svg" alt=""></div>');

        // Next button as div element
        const nextButton = $('<div class="next-btn page flex-c-c" data-page="next" data-board="' + boardId + '"><img src="../static/images/svg/next.svg" alt=""></div>');

        // Show/hide previous button based on currentPage
        if (currentPage === 1) {
            prevButton.addClass('hidden');
        } else {
            prevButton.removeClass('hidden');
        }

        // Show/hide next button based on currentPage and totalPages
        if (currentPage === totalPages) {
            nextButton.addClass('hidden');
        } else {
            nextButton.removeClass('hidden');
        }

        paginationContainer.append(prevButton);

        // Show only relevant page numbers
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c" data-page="${i}" data-board="${boardId}">${i}</div>`);
            if (i === currentPage) {
                pageDiv.addClass('bc-activate'); // Add bc-activate class to current page
            }
            paginationContainer.append(pageDiv);
        }

        paginationContainer.append(nextButton);

        // Previous two pages button as div element
        const prevTwoButton = $('<div class="prev-btn page flex-c-c" data-page="prev-two" data-board="' + boardId + '"><img src="../static/images/svg/prev-two.svg" alt=""></div>');

        // Show/hide previous two pages button based on currentPage
        if (currentPage <= 2) {
            prevTwoButton.addClass('hidden');
        } else {
            prevTwoButton.removeClass('hidden');
        }

        paginationContainer.prepend(prevTwoButton);

        // Next two pages button as div element
        const nextTwoButton = $('<div class="next-btn page flex-c-c" data-page="next-two" data-board="' + boardId + '"><img src="../static/images/svg/next-two.svg" alt=""></div>');

        // Show/hide next two pages button based on currentPage and totalPages
        if (currentPage >= totalPages - 1) {
            nextTwoButton.addClass('hidden');
        } else {
            nextTwoButton.removeClass('hidden');
        }

        paginationContainer.append(nextTwoButton);

        attachPaginationEventListeners(); // Ensure event listeners are attached after updating pagination
    }

    function attachPaginationEventListeners() {
        $('.page').click(function() {
            let page = $(this).data('page');
            const boardId = $(this).data('board'); // Retrieve boardId from data attribute
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

            fetchPosts(page, boardId);
        });
    }

    // Initial fetch for boardId = 1 (자유 게시판)
    const initialBoardId = 1;
    fetchPosts(1, initialBoardId);
});
