document.addEventListener('DOMContentLoaded', function() {
    // Get postId from URL query parameters
    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get('postId');

    // 댓글 등록
    document.getElementById('post-comment-button').addEventListener('click', function() {
        const content = document.getElementById('comment-text').value;
        if (content.trim() === '') {
            alert('댓글 내용을 입력해주세요.');
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
                // userId is not included here, the server-side will handle it
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
                });
                location.reload(); // 댓글을 추가한 후 페이지를 새로 고침
            } else {
                alert('댓글 등록에 실패했습니다.');
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
            if (confirm('이 댓글을 삭제하시겠습니까?')) {
                fetch(`/comments/${commentId}`, {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        location.reload(); // 댓글 삭제 후 페이지를 새로 고침
                    } else {
                        alert('댓글 삭제에 실패했습니다.');
                    }
                }).catch(error => {
                    console.error('댓글 삭제 중 오류 발생:', error);
                });
            }
        });
    });

    // 댓글 수정
    document.querySelectorAll('.edit-comment').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const commentId = event.target.getAttribute('data-id');
            const newContent = prompt('댓글 내용을 입력하세요:');
            if (newContent && newContent.trim() !== '') {
                fetch(`/comments/${commentId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ content: newContent })
                }).then(response => {
                    if (response.ok) {
                        location.reload(); // 댓글 수정 후 페이지를 새로 고침
                    } else {
                        alert('댓글 수정에 실패했습니다.');
                    }
                }).catch(error => {
                    console.error('댓글 수정 중 오류 발생:', error);
                });
            }
        });
    });

    // 좋아요 및 싫어요 처리
    const likeButton = document.getElementById("like-button");
    const dislikeButton = document.getElementById("dislike-button");
    const likeCount = document.getElementById("like-count");

    likeButton.addEventListener("click", function() {
        toggleLike(true);
    });

    dislikeButton.addEventListener("click", function() {
        toggleLike(false);
    });

    function toggleLike(isLiked) {
        fetch(`/toggleLike?postId=${postId}`, {
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
                console.error('Error toggling like:', data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
});
