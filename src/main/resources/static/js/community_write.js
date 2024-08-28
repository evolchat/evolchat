function execCmd(command, value = null) {
    document.execCommand(command, false, value);
}

function toggleColorPicker(button) {
    const input = button.querySelector('input[type="color"]');
    input.classList.toggle('active');
}

function toggleEmojiPicker() {
    const emojiPicker = document.getElementById('emoji-picker');
    emojiPicker.style.display = emojiPicker.style.display === 'block' ? 'none' : 'block';
}

function insertEmoji(emoji) {
    execCmd('insertText', emoji);
    toggleEmojiPicker(); // 이모티콘 선택 후 이모티콘 리스트 숨김
}

function uploadFile(type) {
    const input = document.getElementById(`upload-${type}`);
    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = function(e) {
        let content;
        if (type === 'image') {
            content = `<img src="${e.target.result}" alt="Image" style="max-width: 100%;">`;
        } else if (type === 'video') {
            content = `<video controls style="max-width: 100%;"><source src="${e.target.result}" type="${file.type}">Your browser does not support the video tag.</video>`;
        } else if (type === 'file') {
            content = `<a href="${e.target.result}" download="${file.name}">${file.name}</a>`;
        }
        execCmd('insertHTML', content);
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

const draft = localStorage.getItem('postDraft');
if (draft) {
    const { title, content, tags } = JSON.parse(draft);

    if (title !== null) {
        document.getElementById('title').value = title;
    }

    if (content !== null) {
        document.getElementById('content').innerHTML = content;
    }

    if (tags !== null) {
        document.getElementById('tags').value = tags;
    }
}

function saveDraft() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').innerHTML;
    const tags = document.getElementById('tags').value;
    const draft = { title, content, tags };

    // 확인 모달 팝업을 띄우기
    Swal.fire({
        title: '임시 저장 하시겠습니까?',
        text: "작성 중인 글을 임시로 저장합니다.",
        icon: 'question',
        showCancelButton: true,
        reverseButtons: true,
        iconColor: '#8744FF',
        color: '#FFFFFF',
        background: '#35373D',
        confirmButtonColor: '#8744FF',
        cancelButtonColor: '#535560',
        confirmButtonText: '임시 저장',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            // 사용자 확인 후 임시 저장
            localStorage.setItem('postDraft', JSON.stringify(draft));

            Swal.fire({
                title: '임시 저장 완료!',
                text: '글이 임시 저장되었습니다.',
                icon: 'success',
                reverseButtons: true,
                iconColor: '#8744FF',
                color: '#FFFFFF',
                background: '#35373D',
                confirmButtonColor: '#8744FF',
                confirmButtonText: '확인'
            });
        }
    });
}

function submitPost() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').innerHTML;
    const tags = document.getElementById('tags').value;
    const boardId = $('#content-area .wrapper').attr('data-board-id');

    // 제목과 본문이 비어 있는지 확인
    if (!title || !content.trim()) {
        Swal.fire({
            title: '제목과 본문을 모두 입력해주세요.',
            icon: 'error',
            reverseButtons: true,
            iconColor: '#FF4C4C',
            color: '#FFFFFF',
            background: '#35373D',
            confirmButtonColor: '#8744FF',
            confirmButtonText: '확인'
        });
        return;
    }

    // 게시판 ID가 설정되지 않았는지 확인
    if (!boardId) {
        Swal.fire({
            title: '게시판 ID가 설정되지 않았습니다.',
            icon: 'error',
            reverseButtons: true,
            iconColor: '#FF4C4C',
            color: '#FFFFFF',
            background: '#35373D',
            confirmButtonColor: '#8744FF',
            confirmButtonText: '확인'
        });
        return;
    }

    // 확인 모달 팝업을 띄우기
    Swal.fire({
        title: '정말로 글을 등록하시겠습니까?',
        text: "등록된 글은 게시판에 추가됩니다.",
        icon: 'question',
        showCancelButton: true,
        reverseButtons: true,
        iconColor: '#8744FF',
        color: '#FFFFFF',
        background: '#35373D',
        confirmButtonColor: '#8744FF',
        cancelButtonColor: '#535560',
        confirmButtonText: '등록',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            // 사용자 확인 후 글 등록 요청
            $.ajax({
                url: '/community-posts/submit_post',
                type: 'POST',
                data: {
                    title: title,
                    content: content,
                    tags: tags,
                    boardId: boardId
                },
                success: function(response) {
                    Swal.fire({
                        title: '글 등록에 성공했습니다.',
                        text: "게시글 목록 페이지로 이동합니다.",
                        icon: 'success',
                        reverseButtons: true,
                        iconColor: '#8744FF',
                        color: '#FFFFFF',
                        background: '#35373D',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // 글 등록 후 페이지 이동
                            if (boardId == 1) {
                                window.location.href = '/community_free'; // 자유 게시판 페이지로 이동
                            } else if (boardId == 2) {
                                window.location.href = '/community_photo'; // 포토 게시판 페이지로 이동
                            } else if (boardId == 3) {
                                window.location.href = '/community_video'; // 동영상 게시판 페이지로 이동
                            }
                        }
                    });
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    Swal.fire({
                        title: '글 작성에 실패했습니다.',
                        text: "다시 시도해 주세요.",
                        icon: 'error',
                        reverseButtons: true,
                        iconColor: '#FF4C4C',
                        color: '#FFFFFF',
                        background: '#35373D',
                        confirmButtonColor: '#8744FF',
                        confirmButtonText: '확인'
                    });
                }
            });
        }
    });
}
