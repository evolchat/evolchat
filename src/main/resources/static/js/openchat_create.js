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
        const allInputsFilled = Array.from(inputs).every(input => input.value.trim() !== "");
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
        updateOpenButtonState();
    });

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            if (!this.checked) {
                agreeAll.checked = false;
            } else {
                const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
                agreeAll.checked = allChecked;
            }
            updateOpenButtonState();
        });
    });
});