$(document).ready(function() {
    const usersPerPage = 10; // 페이지당 사용자 수

    function fetchRankedUsers(page = 1) {
        $.ajax({
            url: `/rank/rankedUsers?page=${page}&size=${usersPerPage}`,
            type: 'GET',
            success: function(response, status, xhr) {
                const usersContainer = $('#rank-table-body');
                usersContainer.empty(); // 기존 콘텐츠 제거

                if (response.length === 0) {
                    usersContainer.append('<tr><td colspan="8">검색 결과가 없습니다.</td></tr>'); // 데이터가 없으면 메시지 표시
                    $('#pagination-container').hide(); // 페이지네이션 숨기기
                    return;
                }

                $('#pagination-container').show(); // 데이터가 있으면 페이지네이션 표시

                response.forEach((user, index) => {
                    const rank = (page - 1) * usersPerPage + index + 1;

                    // 메달 이미지 URL 또는 숫자를 가져오기
                    const medalOrText = getMedalOrText(rank);
                    const profileImage = user.profilePicture ? user.profilePicture : '../../static/images/profile/rank-1.svg';

                    const userHtml = `
                        <tr class="px15">
                            <td>
                                ${typeof medalOrText === 'number' ? medalOrText : `<img src="${medalOrText}" alt="#">`}
                            </td>
                            <td><img src="${profileImage}" alt="#"></td>
                            <td class="opacity80 px17 text-left">${user.nickname}</td>
                            <td class="text-center">${user.bettingProfit}</td>
                            <td><span class="orange-FFBF00">${user.bettingWins}승</span><span class="blue">${user.bettingLosses}패</span></td>
                            <td class="opacity60">${user.winRate}</td>
                            <td class="opacity60">${user.maxWinningStreak}연승</td>
                            <td class="opacity60">${user.activeDays}일</td>
                        </tr>
                    `;
                    usersContainer.append(userHtml);
                });

                const totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                updatePagination(page, totalPages);
            },
            error: function() {
                console.log('사용자 데이터를 가져오는 데 실패했습니다.');
            }
        });
    }

    function getMedalOrText(rank) {
        if (rank === 1) return '../../static/images/svg/goldmedal.svg';
        if (rank === 2) return '../../static/images/svg/silvermedal.svg';
        if (rank === 3) return '../../static/images/svg/bronzemedal.svg';

        // 숫자를 직접 반환하여 HTML에서 텍스트로 표시
        return rank;
    }

    function updatePagination(currentPage, totalPages) {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // 기존 페이지네이션 제거

        // 이전 버튼
        const prevButton = $('<div class="prev-btn page flex-c-c"><img src="../../static/images/svg/prev.svg" alt=""></div>');

        // 다음 버튼
        const nextButton = $('<div class="next-btn page flex-c-c"><img src="../../static/images/svg/next.svg" alt=""></div>');

        if (currentPage === 1) {
            prevButton.addClass('hidden');
        } else {
            prevButton.removeClass('hidden');
        }

        if (currentPage === totalPages) {
            nextButton.addClass('hidden');
        } else {
            nextButton.removeClass('hidden');
        }

        paginationContainer.append(prevButton);

        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c ${i === currentPage ? 'bc-activate' : ''}" data-page="${i}">${i}</div>`);
            paginationContainer.append(pageDiv);
        }

        paginationContainer.append(nextButton);

        attachPaginationEventListeners(); // 페이지네이션 이벤트 리스너 부착
    }

    function attachPaginationEventListeners() {
        $('.page').off('click').on('click', function() {
            const page = $(this).data('page');
            fetchRankedUsers(page);
        });
    }

    // 초기 사용자 데이터 가져오기
    fetchRankedUsers(1);
});
