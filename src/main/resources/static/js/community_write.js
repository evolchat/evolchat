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

        document.addEventListener("DOMContentLoaded", function() {
            // Load draft from local storage
            const draft = localStorage.getItem('postDraft');
            if (draft) {
                const { title, content, tags } = JSON.parse(draft);
                document.getElementById('title').value = title;
                document.getElementById('content').innerHTML = content;
                document.getElementById('tags').value = tags;
            }

            document.getElementById('save-draft').addEventListener('click', function() {
                const title = document.getElementById('title').value;
                const content = document.getElementById('content').innerHTML;
                const tags = document.getElementById('tags').value;
                const draft = { title, content, tags };
                localStorage.setItem('postDraft', JSON.stringify(draft));
                alert('임시 저장되었습니다.');
            });

            document.getElementById('submit-post').addEventListener('click', function() {
                const content = document.getElementById('content').innerHTML;
                document.getElementById('content-hidden').value = content;
                document.getElementById('post-form').submit();
            });
        });