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

document.addEventListener('DOMContentLoaded', () => {
    const titleInput = document.querySelector('.input input[type="text"]:nth-child(1)');
    const descriptionInput = document.querySelector('.input input[type="text"]:nth-child(2)');
    const openButton = document.getElementById('open');
    const agreeAll = document.getElementById('agreeAll');
    const subCheckboxes = document.querySelectorAll('.sub-checkbox');

    function checkInputsAndCheckboxes() {
        const inputsFilled = titleInput.value.trim() !== '' && descriptionInput.value.trim() !== '';
        const allCheckboxesChecked = agreeAll.checked && [...subCheckboxes].every(cb => cb.checked);

        if (inputsFilled && allCheckboxesChecked) {
            openButton.classList.add('create-active');
        } else {
            openButton.classList.remove('create-active');
        }
    }

    titleInput.addEventListener('input', checkInputsAndCheckboxes);
    descriptionInput.addEventListener('input', checkInputsAndCheckboxes);
    agreeAll.addEventListener('change', () => {
        subCheckboxes.forEach(checkbox => {
            checkbox.checked = agreeAll.checked;
        });
        checkInputsAndCheckboxes();
    });

    subCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            if ([...subCheckboxes].every(cb => cb.checked)) {
                agreeAll.checked = true;
            } else {
                agreeAll.checked = false;
            }
            checkInputsAndCheckboxes();
        });
    });
});