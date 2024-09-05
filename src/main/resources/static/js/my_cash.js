    $(document).ready(function() {
        const postsPerPage = 10;
        let currentPage = 1;
        let totalPages = 1;

        function fetchTransactions(page = 1, searchQuery = '') {
            const url = `/cash_transactions?page=${page}&size=${postsPerPage}&search=${encodeURIComponent(searchQuery)}`;

            $.ajax({
                url: url,
                type: 'GET',
                success: function(response) {
                    console.log('Fetched data:', response); // 데이터 확인
                    renderTransactions(response.cashPointsHistories);
                    totalPages = response.totalPages;
                    updatePagination(currentPage, totalPages);
                },
                error: function() {
                    console.error('거래 데이터를 가져오는 데 실패했습니다.');
                }
            });
        }

        function renderTransactions(transactions) {
            const tbody = $('#cash-transaction-table-body');
            tbody.empty(); // 기존 데이터 제거

            if (transactions.length === 0) {
                tbody.append('<tr><td colspan="4" class="text-center">검색 결과가 없습니다.</td></tr>');
                $('#pagination-container').hide();
                return;
            }

            $('#pagination-container').show();

            transactions.forEach(transaction => {
                const rowHtml = `
                    <tr>
                        <td class="text-left">${transaction.content}</td>
                        <td class="text-center">${transaction.amount.toLocaleString()}</td>
                        <td class="text-center">${transaction.remainingBalance.toLocaleString()}</td>
                        <td class="text-center">${new Date(transaction.createdAt).toLocaleString()}</td>
                    </tr>
                `;
                tbody.append(rowHtml);
            });
        }

        function updatePagination(currentPage, totalPages) {
            const paginationContainer = $('#pagination-container');
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
            if (isNaN(dataPage)) {
                return $(`<div class="page flex-c-c" data-page="${page}" title="${altText}"><img src="../../static/images/svg/${dataPage}.svg" alt="${altText}"></div>`);
            }
            return $(`<div class="page flex-c-c" data-page="${page}" title="${altText}">${dataPage}</div>`);
        }

        function attachPaginationEventListeners() {
            $('.page').off('click').on('click', function() {
                const page = $(this).data('page');
                if (!isNaN(page) && page > 0 && page <= totalPages) {
                    currentPage = page;
                    fetchTransactions(currentPage, $('#cash-search-input').val());
                }
            });
        }

        function handleSearch() {
            fetchTransactions(1, $('#cash-search-input').val());
        }

        // 초기화 함수
        function init() {
            fetchTransactions();
            $('#cash-search-button').on('click', handleSearch);
            $('#cash-search-input').on('keypress', function(event) {
                if (event.which === 13) {
                    event.preventDefault();
                    handleSearch();
                }
            });
            $.ajax({
                url: `/user-points/current`,
                type: 'GET',
                success: function(response) {
                    $('#ccash').text(response.ccash.toLocaleString('ko-KR'));
                },
                error: function() {
                    console.log('Failed to fetch user points');
                }
            });

        }
        init();
    });