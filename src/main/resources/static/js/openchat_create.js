document.addEventListener('DOMContentLoaded', function () {
    const titleInput = document.getElementById('title-input');
    const titleCount = document.getElementById('title-count');
    const descInput = document.getElementById('desc-input');
    const descCount = document.getElementById('desc-count');

    // 제목 입력 필드에서 글자 수 업데이트
    titleInput.addEventListener('input', function () {
        titleCount.textContent = `${titleInput.value.length} / 20`;
    });

    // 소개글 입력 필드에서 글자 수 업데이트
    descInput.addEventListener('input', function () {
        descCount.textContent = `${descInput.value.length} / 80`;
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const tags = document.querySelectorAll('.tag');

    tags.forEach(tag => {
        tag.addEventListener('click', () => {
            tags.forEach(t => t.classList.remove('active'));
            tag.classList.add('active');
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const agreeAll = document.getElementById('agreeAll');
    const subCheckboxes = document.querySelectorAll('.sub-checkbox');

    agreeAll.addEventListener('change', () => {
        subCheckboxes.forEach(checkbox => {
            checkbox.checked = agreeAll.checked;
        });
    });

    subCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            if ([...subCheckboxes].every(cb => cb.checked)) {
                agreeAll.checked = true;
            } else {
                agreeAll.checked = false;
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const inputs = document.querySelectorAll('.upload-area input[type="text"]');
    const checkboxes = document.querySelectorAll('.agreement .sub-checkbox');
    const agreeAll = document.getElementById('agreeAll');
    const openButton = document.getElementById('open');

    function updateOpenButtonState() {
        // 모든 입력 필드가 채워졌는지 확인
        const allInputsFilled = Array.from(inputs).every(input => input.value.trim() !== "");
        // 모든 체크박스가 체크되었는지 확인
        const allCheckboxesChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);

        if (allInputsFilled && allCheckboxesChecked) {
            openButton.classList.add('create-active');
        } else {
            openButton.classList.remove('create-active');
        }
    }

    inputs.forEach(input => {
        input.addEventListener('input', updateOpenButtonState);
    });

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateOpenButtonState);
    });

    agreeAll.addEventListener('change', function() {
        checkboxes.forEach(checkbox => {
            checkbox.checked = agreeAll.checked;
        });
        updateOpenButtonState(); // 전체 체크박스 상태에 따라 버튼 상태 업데이트
    });

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            if (!this.checked) {
                agreeAll.checked = false;
            } else {
                const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
                agreeAll.checked = allChecked;
            }
            updateOpenButtonState(); // 개별 체크박스 상태에 따라 버튼 상태 업데이트
        });
    });
});