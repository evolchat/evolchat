document.addEventListener('DOMContentLoaded', function() {
    // Get postId from URL query parameters
    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get('postId');

    // 댓글 등록
    document.getElementById('post-comment-button').addEventListener('click', function() {
        const content = document.getElementById('comment-text').value;
        if (content.trim() === '') {
            Swal.fire({
                title: '댓글 내용을 입력해주세요.',
                icon: 'warning',
                iconColor: '#FFA500',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인',
            });
            return;
        }

        fetch('/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                postId: postId,
                content: content
            })
        }).then(response => {
            if (response.ok) {
                Swal.fire({
                    title: '댓글 등록 완료!',
                    text: '댓글이 저장되었습니다.',
                    icon: 'success',
                    reverseButtons: true,
                    iconColor: '#8744FF',
                    color: '#FFFFFF',
                    background: '#35373D',
                    confirmButtonColor: '#8744FF',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.reload(); // 댓글을 추가한 후 페이지를 새로 고침
                    }
                });
            } else {
                Swal.fire({
                    title: '댓글 등록 실패',
                    text: '댓글 등록에 실패했습니다.',
                    icon: 'error',
                    iconColor: '#FF0000',
                    color: '#FFFFFF',
                    background: '#35373D',
                    confirmButtonColor: '#8744FF',
                    confirmButtonText: '확인',
                });
            }
        }).catch(error => {
            console.error('댓글 등록 중 오류 발생:', error);
        });
    });

    // 댓글 삭제
    document.querySelectorAll('.delete-comment').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const commentId = event.target.getAttribute('data-id');
            Swal.fire({
                title: '이 댓글을 삭제하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                reverseButtons: true,
                iconColor: '#8744FF',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                cancelButtonColor: '#535560',
                confirmButtonText: '삭제',
                cancelButtonText: '취소',
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch(`/comments/${commentId}`, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            location.reload(); // 댓글 삭제 후 페이지를 새로 고침
                        } else {
                            Swal.fire({
                                title: '댓글 삭제 실패',
                                text: '댓글 삭제에 실패했습니다.',
                                icon: 'error',
                                iconColor: '#FF0000',
                                color: '#FFFFFF',
                                background: '#35373D',
                                confirmButtonColor: '#8744FF',
                                confirmButtonText: '확인',
                            });
                        }
                    }).catch(error => {
                        console.error('댓글 삭제 중 오류 발생:', error);
                    });
                }
            });
        });
    });

    // 댓글 수정
    document.querySelectorAll('.edit-comment').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const commentId = event.target.getAttribute('data-id');
            Swal.fire({
                title: '댓글 내용을 입력하세요:',
                input: 'textarea',
                inputPlaceholder: '댓글 내용을 입력하세요...',
                inputAttributes: {
                    'aria-label': '댓글 내용'
                },
                icon: 'info',
                reverseButtons: true,
                iconColor: '#8744FF',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                cancelButtonColor: '#535560',
                confirmButtonText: '수정',
                cancelButtonText: '취소',
                showCancelButton: true,
            }).then((result) => {
                if (result.isConfirmed && result.value.trim() !== '') {
                    fetch(`/comments/${commentId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ content: result.value })
                    }).then(response => {
                        if (response.ok) {
                            location.reload(); // 댓글 수정 후 페이지를 새로 고침
                        } else {
                            Swal.fire({
                                title: '댓글 수정 실패',
                                text: '댓글 수정에 실패했습니다.',
                                icon: 'error',
                                iconColor: '#FF0000',
                                color: '#FFFFFF',
                                background: '#35373D',
                                confirmButtonColor: '#8744FF',
                                confirmButtonText: '확인',
                            });
                        }
                    }).catch(error => {
                        console.error('댓글 수정 중 오류 발생:', error);
                    });
                }
            });
        });
    });

    // 좋아요 및 싫어요 처리
    const likeButton = document.getElementById("like-button");
    const dislikeButton = document.getElementById("dislike-button");
    const likeCount = document.getElementById("like-count");

    likeButton.addEventListener("click", function() {
        updateLikeCount(true);
    });

    dislikeButton.addEventListener("click", function() {
        updateLikeCount(false);
    });

    function updateLikeCount(isLiked) {
        // 현재 좋아요 수를 화면에서 즉시 업데이트
        let currentCount = parseInt(likeCount.textContent);
        if (isLiked) {
            likeButton.style.display = "none";
            dislikeButton.style.display = "block";
            likeCount.textContent = currentCount + 1; // 화면에서 즉시 좋아요 수를 1 증가
        } else {
            likeButton.style.display = "block";
            dislikeButton.style.display = "none";
            likeCount.textContent = currentCount - 1; // 화면에서 즉시 좋아요 수를 1 감소
        }

        // 서버와 동기화
        fetch(`/likes/toggle?postId=${postId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            credentials: 'include'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                if (data.isLiked) {
                    likeButton.style.display = "none";
                    dislikeButton.style.display = "block";
                } else {
                    likeButton.style.display = "block";
                    dislikeButton.style.display = "none";
                }
                likeCount.textContent = data.likeCount;
            } else {
                Swal.fire({
                    title: '좋아요 처리 오류',
                    text: data.message,
                    icon: 'error',
                    iconColor: '#FF0000',
                    color: '#FFFFFF',
                    background: '#35373D',
                    confirmButtonColor: '#8744FF',
                    confirmButtonText: '확인',
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
});
