$(document).ready(function () {
    const searchInput = $('.sub-header-search input');
    const searchButton = $('.sub-header-search .search');
    const tableBody = $('.rank-table tbody');
    const paginationContainer = $('.pagination');

    let currentPage = 1;
    const pageSize = 10; // 한 페이지에 표시할 항목 수

    // 검색 버튼 클릭 시 검색 실행
    searchButton.on('click', function () {
        const searchQuery = searchInput.val().trim();
        fetchPosts(currentPage, searchQuery);
    });

    // 페이지 번호 클릭 시 페이지 변경
    paginationContainer.on('click', '.page', function () {
        const page = $(this).data('page');
        if (page === 'prev') {
            currentPage = Math.max(1, currentPage - 1);
        } else if (page === 'next') {
            currentPage = Math.min(parseInt(paginationContainer.data('total-pages')), currentPage + 1);
        } else if (page === 'next-two') {
            currentPage = parseInt(paginationContainer.data('total-pages'));
        } else if (page === 'first') {
            currentPage = 1;
        } else if (page === 'last') {
            currentPage = parseInt(paginationContainer.data('total-pages'));
        } else {
            currentPage = parseInt(page);
        }
        fetchPosts(currentPage, searchInput.val().trim());
    });

    // 공지사항 데이터를 가져오는 함수
    function fetchPosts(page, search) {
        const boardId = 1; // 공지사항 boardId
        $.ajax({
            url: `/support-posts?page=${page}&size=${pageSize}&boardId=${boardId}&search=${encodeURIComponent(search)}`,
            method: 'GET',
            success: function (data, textStatus, xhr) {
                const totalPages = parseInt(xhr.getResponseHeader('X-Total-Pages'), 10);
                updateTable(data);
                updatePagination(totalPages, page); // 현재 페이지를 전달
                paginationContainer.data('total-pages', totalPages); // 총 페이지 수 저장
            },
            error: function (xhr, textStatus, errorThrown) {
                console.error('Error fetching posts:', errorThrown);
            }
        });
    }

    // 테이블을 업데이트하는 함수
   function updateTable(posts) {
       tableBody.empty();
       posts.forEach(post => {
           const row = $(`<tr class="pointer post-title">`);

           // 분류
           const categoryCell = $(`
               <td>
                   <a href="/support_notice_detail?boardId=1&postId=${post.postId}">
                       <div class="bc-${getCategoryColor(post.detailedCategory)} border-5 flex-c-c square">
                           ${getCategoryLabel(post.detailedCategory)}
                       </div>
                   </a>
               </td>
           `);
           row.append(categoryCell);

           // 제목
           const titleCell = $(`
               <td>
                   <a href="/support_notice_detail?boardId=1&postId=${post.postId}">
                       <div class="text-left">
                           <span class="px15">${post.title}</span>
                       </div>
                   </a>
               </td>
           `);
           row.append(titleCell);

           // 일시
           const dateCell = $(`
               <td class="opacity60">
                   <a href="/support_notice_detail?boardId=1&postId=${post.postId}">
                       ${new Date(post.createdAt).toLocaleDateString()}
                   </a>
               </td>
           `);
           row.append(dateCell);

           // 조회
           const viewsCell = $(`
               <td class="opacity60">
                   <a href="/support_notice_detail?boardId=1&postId=${post.postId}">
                       ${post.views}
                   </a>
               </td>
           `);
           row.append(viewsCell);

           // Add row to the table body
           tableBody.append(row);
       });

       // Add click event to make entire <td> clickable and trigger <a> link
       tableBody.on('click', 'td', function () {
           const link = $(this).find('a')[0];
           if (link) {
               link.click();
           }
       });
   }

    // 페이지네이션을 업데이트하는 함수
    function updatePagination(totalPages, currentPage) {
        paginationContainer.empty();

        // '맨 앞으로' 버튼
        if (currentPage > 3) {
            const firstButton = $('<div class="page flex-c-c" data-page="first"><img src="../../static/images/svg/prev-two.svg" alt="맨 앞으로"></div>');
            paginationContainer.append(firstButton);
        }

        // 이전 버튼
        if (currentPage > 1) {
            const prevButton = $('<div class="page flex-c-c" data-page="prev"><img src="../../static/images/svg/prev.svg" alt="이전 페이지"></div>');
            paginationContainer.append(prevButton);
        }

        // 페이지 번호 버튼
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const pageDiv = $(`<div class="page flex-c-c" data-page="${i}">${i}</div>`);
            if (i === currentPage) {
                pageDiv.addClass('bc-activate'); // 현재 페이지에 bc-activate 클래스 추가
            }
            paginationContainer.append(pageDiv);
        }

        // 다음 버튼
        if (currentPage < totalPages) {
            const nextButton = $('<div class="page flex-c-c" data-page="next"><img src="../../static/images/svg/next.svg" alt="다음 페이지"></div>');
            paginationContainer.append(nextButton);
        }

        // '맨 뒤로' 버튼
        if (currentPage < totalPages - 2) {
            const lastButton = $('<div class="page flex-c-c" data-page="next-two"><img src="../../static/images/svg/next-two.svg" alt="맨 마지막 페이지"></div>');
            paginationContainer.append(lastButton);
        }
    }

    // 상세 분류에 대한 색상 반환
    function getCategoryColor(category) {
        switch (category) {
            case 1: return 'red-f23f42'; // 공지
            case 2: return 'blue-2379FF'; // 업데이트
            case 3: return 'ivory'; // 이벤트
            case 4: return 'yellow'; // 확인대기
            case 5: return 'green'; // 답변대기
            case 6: return 'purple'; // 답변완료
            default: return 'gray'; // 기본값
        }
    }

    // 상세 분류에 대한 레이블 반환
    function getCategoryLabel(category) {
        switch (category) {
            case 1: return '공지';
            case 2: return '업데이트';
            case 3: return '이벤트';
            case 4: return '확인대기';
            case 5: return '답변대기';
            case 6: return '답변완료';
            default: return '기타'; // 기본값
        }
    }

    // 초기 데이터 로드
    fetchPosts(currentPage, '');
});
