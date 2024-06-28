$(document).ready(function() {
    function fetchPosts(page = 1) {
        $.ajax({
            url: `/posts?page=${page}`,
            type: 'GET',
            success: function(response) {
                const postsContainer = $('#posts-container');
                postsContainer.empty(); // Clear any existing content

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
                                        <div class="nickname px15">User ID: ${post.userId}</div>
                                    </div>
                                    <div class="info px12 flex-c flex-row">
                                        <span class="m-r-20">${new Date(post.createdAt).toLocaleString()}</span>
                                        <span class="flex-c flex-row m-r-20"><img
                                                src="../static/images/svg/input-icon-eye.svg" alt=""
                                                class="m-r-4">${post.views}</span>
                                        <span class="flex-c flex-row"><img
                                                src="../static/images/svg/hearts.svg" alt=""
                                                class="m-r-4">${post.likes}</span>
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

                updatePagination(response.currentPage, response.totalPages);
            },
            error: function() {
                console.log('Failed to fetch posts');
            }
        });
    }

    function updatePagination(currentPage, totalPages) {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // Clear existing pagination

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = $(`<div class="page flex-c-c" data-page="${i}">${i}</div>`);
            if (i === currentPage) {
                pageButton.addClass('bc-activate');
            }
            paginationContainer.append(pageButton);
        }

        // Next and Next Two buttons
        paginationContainer.append('<button class="next-btn page flex-c-c" data-page="next"><img src="../static/images/svg/next.svg" alt=""></button>');
        paginationContainer.append('<button class="next-btn page flex-c-c" data-page="next-two"><img src="../static/images/svg/next-two.svg" alt=""></button>');

        attachPaginationEventListeners();
    }

    function attachPaginationEventListeners() {
        $('.page').click(function() {
            let page = $(this).data('page');
            const currentPage = parseInt($('.bc-activate').data('page'));

            if (page === 'next') {
                page = currentPage + 1;
            } else if (page === 'next-two') {
                page = currentPage + 2;
            } else {
                page = parseInt(page);
            }

            fetchPosts(page);
        });
    }

    // Initial fetch
    fetchPosts();
});
