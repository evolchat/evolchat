$(document).ready(function() {
    const usersPerPage = 10; // 페이지당 사용자 수
    let currentPage = 1; // 현재 페이지
    let totalPages = 1; // 총 페이지 수

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
                    const medalOrText = getMedalOrText(rank);
                    const profileImage = user.profilePicture ? user.profilePicture : '../../static/images/profile/default.png';

                    const userHtml = `
                        <tr class="px15">
                            <td>
                                ${typeof medalOrText === 'number' ? medalOrText : `<img src="${medalOrText}" alt="#">`}
                            </td>
                            <td><img class="profile-img-60" src="${profileImage}" alt="#"></td>
                            <td class="opacity80 px17 text-left">${user.nickname}</td>
                            <td class="text-center">${user.bettingProfit}</td>
                            <td><span class="orange-FFBF00">${user.bettingWins}승</span><span class="blue">&nbsp${user.bettingLosses}패</span></td>
                            <td class="opacity60">${user.winRate}</td>
                            <td class="opacity60">${user.maxWinningStreak}연승</td>
                            <td class="opacity60">${user.activeDays}일</td>
                        </tr>
                    `;
                    usersContainer.append(userHtml);
                });

                totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages')) || 1;
                currentPage = page; // 현재 페이지 업데이트
                updatePagination(); // 페이지네이션 업데이트
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
        return rank;
    }

    function updatePagination() {
        const paginationContainer = $('#pagination-container');
        paginationContainer.empty(); // 기존 페이지네이션 제거

        // '맨 앞으로' 버튼
        if (currentPage > 1) {
            const firstButton = $('<div class="first-btn page flex-c-c"><img src="../../static/images/svg/prev-two.svg" alt="맨 처음 페이지"></div>');
            paginationContainer.append(firstButton);
        }

        // 이전 버튼
        if (currentPage > 1) {
            const prevButton = $('<div class="prev-btn page flex-c-c"><img src="../../static/images/svg/prev.svg" alt="이전 페이지"></div>');
            paginationContainer.append(prevButton);
        }

        // 페이지 번호 버튼
        const paginationRange = 5; // 페이지 번호 버튼 개수
        const startPage = Math.max(1, currentPage - Math.floor(paginationRange / 2));
        const endPage = Math.min(totalPages, startPage + paginationRange - 1);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c ${i === currentPage ? 'bc-activate' : ''}" data-page="${i}">${i}</div>`);
            paginationContainer.append(pageDiv);
        }

        // 다음 버튼
        if (currentPage < totalPages) {
            const nextButton = $('<div class="next-btn page flex-c-c"><img src="../../static/images/svg/next.svg" alt="다음 페이지"></div>');
            paginationContainer.append(nextButton);
        }

        // '맨 뒤로' 버튼
        if (currentPage < totalPages - 2) {
            const lastButton = $('<div class="last-btn page flex-c-c"><img src="../../static/images/svg/next-two.svg" alt="맨 마지막 페이지"></div>');
            paginationContainer.append(lastButton);
        }

        attachPaginationEventListeners(); // 페이지네이션 이벤트 리스너 부착
    }

    function attachPaginationEventListeners() {
        $('.page').off('click').on('click', function() {
            const page = $(this).data('page');
            if (page && page !== currentPage) {
                fetchRankedUsers(page);697
            }
        });

        $('.prev-btn').off('click').on('click', function() {
            if (currentPage > 1) {
                fetchRankedUsers(currentPage - 1);
            }
        });

        $('.next-btn').off('click').on('click', function() {
            if (currentPage < totalPages) {
                fetchRankedUsers(currentPage + 1);
            }
        });

        $('.first-btn').off('click').on('click', function() {
            if (currentPage > 1) {
                fetchRankedUsers(1); // 첫 페이지로 이동
            }
        });

        $('.last-btn').off('click').on('click', function() {
            if (currentPage < totalPages) {
                fetchRankedUsers(totalPages); // 마지막 페이지로 이동
            }
        });
    }

    // 초기 사용자 데이터 가져오기
    fetchRankedUsers(1);
});
