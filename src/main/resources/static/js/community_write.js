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
    document.getElementById('title').value = title;
    document.getElementById('content').innerHTML = content;
    document.getElementById('tags').value = tags;
}

function saveDraft() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').innerHTML;
    const tags = document.getElementById('tags').value;
    const draft = { title, content, tags };
    localStorage.setItem('postDraft', JSON.stringify(draft));
    alert('임시 저장되었습니다.');
}

function submitPost() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').innerHTML;
    const tags = document.getElementById('tags').value;
    const boardId = document.body.getAttribute('data-board-id');

    if (!title || !content.trim()) {
        alert('제목과 본문을 모두 입력해주세요.');
        return;
    }

    if (!boardId) {
        alert('게시판 ID가 설정되지 않았습니다.');
        return;
    }

    $.ajax({
        url: '/submit_post',
        type: 'POST',
        data: {
            title: title,
            content: content,
            tags: tags,
            boardId: boardId
        },
        success: function(response) {
            alert('글 등록에 성공했습니다.');
            // 글 등록 후 원하는 페이지로 이동
            window.location.href = '/community_free'; // 예시로 게시글 목록 페이지로 이동
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('글 작성에 실패했습니다. 다시 시도해 주세요.');
        }
    });
}