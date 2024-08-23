document.addEventListener('DOMContentLoaded', (event) => {
        // Select all checkboxes with the class 'interest-checkbox'
        const checkboxes = document.querySelectorAll('.interest-checkbox');

    // Add event listener to each checkbox
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            // Count the number of checked checkboxes
            const checkedCount = document.querySelectorAll('.interest-checkbox:checked').length;

            // If more than 5 checkboxes are checked, uncheck the current one
            if (checkedCount > 5) {
                this.checked = false;
                Swal.fire({
                  icon: "error",
                  title: "최대 5개까지만 선택할 수 있습니다.",
                  color: '#FFFFFF',
                  background: '#35373D'
                });
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const nicknameSearchInput = document.getElementById('nicknameSearch');
    const nicknameSearchCount = document.getElementById('nicknameSearch-count');

    // "닉네임 검색" 입력 필드에서 글자 수 업데이트
    nicknameSearchInput.addEventListener('input', function () {
        nicknameSearchCount.textContent = `${nicknameSearchInput.value.length} / 20`;
    });

    // 기존 코드: "오늘의 한마디" 입력 필드에 대한 글자 수 업데이트
    const todaysMessageInput = document.getElementById('todaysMessage');
    const todaysMessageCount = document.getElementById('todaysMessage-count');

    todaysMessageInput.addEventListener('input', function () {
        todaysMessageCount.textContent = `${todaysMessageInput.value.length} / 60`;
    });

    // 추가적으로 다른 입력 필드에 대해 글자 수 제한을 표시하려면 동일한 방식으로 추가
});