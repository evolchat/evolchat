// 1. JSON 데이터 가져오기
fetch('/user-points/current')
    .then(response => response.json())
    .then(data => {
        // 2. 유저의 포인트 데이터
        let userCash = data.ccash;
        let userGoldChip = data.ggoldChip;
        const userBettingPoints = data.bbettingPoints;

        // 3. 모든 아이템을 순회하며 초기 상태 설정
        document.querySelectorAll('.shopping-item-wrap').forEach(item => {
            // 각 아이템의 현금 및 골드칩 가격을 가져옴
            const cashPriceElement = item.querySelector('.case:nth-child(1) .px17');
            const goldChipPriceElement = item.querySelector('.case:nth-child(3) .px17');

            const cashPrice = parseInt(cashPriceElement.innerText.replaceAll(',', ''));

            // 선물 및 구매 버튼
            const giftButton = item.querySelector('.gift');
            const purchaseButton = item.querySelector('.purchase');

            // 수량 변경 버튼과 수량 표시 요소
            const countElement = item.querySelector('.count');
            const countDownButton = item.querySelector('.count-down');
            const countUpButton = item.querySelector('.count-up');

            // 초기 수량 설정
            let itemCount = parseInt(countElement.innerText);

            // 특정 아이템 (shopping-item-5)은 bbettingPoints로 계산
            const isBettingItem = item.querySelector('.line .top .right .shopping-item').classList.contains('shopping-item-5')

            // 버튼 상태를 업데이트하는 함수
            const updateButtonState = () => {
                const totalPrice = cashPrice * itemCount;
                const goldChipPrice = goldChipPriceElement ? parseInt(goldChipPriceElement.innerText.replace(',', '')) * itemCount : 0;

                if (isBettingItem) {
                    // bbettingPoints를 사용하는 경우
                    if (userBettingPoints >= totalPrice) {
                        giftButton.classList.remove('disabled');
                        purchaseButton.classList.remove('disabled');
                        giftButton.classList.add('activate');
                        purchaseButton.classList.add('activate');
                    } else {
                        giftButton.classList.remove('activate');
                        purchaseButton.classList.remove('activate');
                        giftButton.classList.add('disabled');
                        purchaseButton.classList.add('disabled');
                    }
                } else {
                    // ccash와 ggoldChip을 사용하는 경우
                    const canBuyWithCash = userCash >= totalPrice;
                    const canBuyWithGoldChip = userGoldChip >= goldChipPrice;

                    if (canBuyWithCash || canBuyWithGoldChip) {
                        giftButton.classList.remove('disabled');
                        purchaseButton.classList.remove('disabled');
                        giftButton.classList.add('activate');
                        purchaseButton.classList.add('activate');
                    } else {
                        giftButton.classList.remove('activate');
                        purchaseButton.classList.remove('activate');
                        giftButton.classList.add('disabled');
                        purchaseButton.classList.add('disabled');
                    }
                }
            };

            // 수량 감소 버튼 클릭 이벤트
            countDownButton.addEventListener('click', () => {
                if (itemCount > 1) {
                    itemCount--;
                    countElement.innerText = itemCount;
                    updateButtonState();
                }
            });

            // 수량 증가 버튼 클릭 이벤트
            countUpButton.addEventListener('click', () => {
                itemCount++;
                countElement.innerText = itemCount;
                updateButtonState();
            });

            // 페이지 로드 시 초기 상태 업데이트
            updateButtonState();

            // 선물 버튼 클릭 이벤트
            giftButton.addEventListener('click', () => {
                if (!giftButton.classList.contains('disabled')) {
                    serviceOpen();
                }
            });

            // 구매 버튼 클릭 이벤트
            purchaseButton.addEventListener('click', () => {
                if (!purchaseButton.classList.contains('disabled')) {
                    const totalPrice = cashPrice * itemCount;
                    const goldChipPrice = goldChipPriceElement ? parseInt(goldChipPriceElement.innerText.replace(',', '')) * itemCount : 0;

                    if (isBettingItem) {
                        // bbettingPoints로 결제
                        Swal.fire({
                            title: '구매하시겠습니까?',
                            text: `총 금액: ${totalPrice} Betting Points (남은 Betting Points: ${userBettingPoints - totalPrice})`,
                            icon: 'question',
                            showCancelButton: true,
                            confirmButtonText: '구매하기',
                            cancelButtonText: '취소',
                            confirmButtonColor: '#8744FF',
                            cancelButtonColor: '#535560',
                            reverseButtons: true,
                            iconColor: '#8744FF',
                            color: '#FFFFFF',
                            background: '#35373D',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                // bbettingPoints로 결제
                                userBettingPoints -= totalPrice;
                                purchaseItem(item, itemCount, 'Betting Points', userBettingPoints);
                            }
                        });
                    } else {
                        if (userCash >= totalPrice && userGoldChip >= goldChipPrice) {
                            // 두 개 다 충분할 때 결제 수단 선택
                            Swal.fire({
                                title: '결제 수단을 선택하세요',
                                text: `CCash: ${userCash - totalPrice} 남음 | Gold Chip: ${userGoldChip - goldChipPrice} 남음`,
                                icon: 'question',
                                showCancelButton: true,
                                showDenyButton: true,
                                confirmButtonText: 'CCash로 결제',
                                denyButtonText: 'Gold Chip으로 결제',
                                cancelButtonText: '취소',
                                reverseButtons: true,
                                iconColor: '#8744FF',
                                color: '#FFFFFF',
                                background: '#35373D',
                                confirmButtonColor: '#8744FF',
                                denyButtonColor: '#BF9B30',
                                cancelButtonColor: '#535560',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    Swal.fire({
                                        title: '구매하시겠습니까?',
                                        text: `총 금액: ${totalPrice} CCash (남은 CCash: ${userCash - totalPrice})`,
                                        icon: 'question',
                                        showCancelButton: true,
                                        reverseButtons: true,
                                        iconColor: '#8744FF',
                                        color: '#FFFFFF',
                                        background: '#35373D',
                                        confirmButtonColor: '#8744FF',
                                        cancelButtonColor: '#535560',
                                        confirmButtonText: '구매하기',
                                        cancelButtonText: '취소'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            // CCash로 결제
                                            userCash -= totalPrice;
                                            purchaseItem(item, itemCount, 'CCash', userCash);
                                        }
                                    });
                                } else if (result.isDenied) {
                                    // Gold Chip으로 결제
                                    Swal.fire({
                                        title: '구매하시겠습니까?',
                                        text: `총 금액: ${goldChipPrice} Gold Chip (남은 Gold Chip: ${userGoldChip - goldChipPrice})`,
                                        icon: 'question',
                                        showCancelButton: true,
                                        reverseButtons: true,
                                        iconColor: '#8744FF',
                                        color: '#FFFFFF',
                                        background: '#35373D',
                                        confirmButtonColor: '#8744FF',
                                        cancelButtonColor: '#535560',
                                        confirmButtonText: '구매하기',
                                        cancelButtonText: '취소'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            // Gold Chip으로 결제
                                            userGoldChip -= goldChipPrice;
                                            purchaseItem(item, itemCount, 'Gold Chip', userGoldChip);
                                        }
                                    });
                                }
                            });
                        } else if (userCash >= totalPrice) {
                            // CCash로 결제
                            Swal.fire({
                                title: '구매하시겠습니까?',
                                text: `총 금액: ${totalPrice} CCash (남은 CCash: ${userCash - totalPrice})`,
                                icon: 'question',
                                showCancelButton: true,
                                reverseButtons: true,
                                iconColor: '#8744FF',
                                color: '#FFFFFF',
                                background: '#35373D',
                                confirmButtonColor: '#8744FF',
                                cancelButtonColor: '#535560',
                                confirmButtonText: '구매하기',
                                cancelButtonText: '취소'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // CCash로 결제
                                    userCash -= totalPrice;
                                    purchaseItem(item, itemCount, 'CCash', userCash);
                                }
                            });
                        } else if (userGoldChip >= goldChipPrice) {
                            // Gold Chip으로 결제
                            Swal.fire({
                                title: '구매하시겠습니까?',
                                text: `총 금액: ${goldChipPrice} Gold Chip (남은 Gold Chip: ${userGoldChip - goldChipPrice})`,
                                icon: 'question',
                                showCancelButton: true,
                                reverseButtons: true,
                                iconColor: '#8744FF',
                                color: '#FFFFFF',
                                background: '#35373D',
                                confirmButtonColor: '#8744FF',
                                cancelButtonColor: '#535560',
                                confirmButtonText: '구매하기',
                                cancelButtonText: '취소'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // Gold Chip으로 결제
                                    userGoldChip -= goldChipPrice;
                                    purchaseItem(item, itemCount, 'Gold Chip', userGoldChip);
                                }
                            });
                        }
                    }
                }
            });
        });
    })
    .catch(error => console.error('Error fetching user points:', error));

// 실제 구매 처리 함수
function purchaseItem(item, count, paymentMethod, remainingBalance) {
    // 아이템 정보 추출
    const itemId = item.querySelector('.shopping-item').classList[1].split('-')[2]; // 예: shopping-item-17 -> 17
    const itemName = item.querySelector('.name').innerText;

    // 결제 정보 설정
    const requestData = {
        itemId: Number(itemId),
        quantity: count,
        paymentMethod: paymentMethod
    };

    // 서버에 요청 전송
    fetch('/purchase', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 서버 응답이 성공적일 경우
            Swal.fire({
                title: '구매 완료!',
                text: `${itemName}를(을) ${count}개 ${paymentMethod}로 결제했습니다. 남은 ${paymentMethod}: ${remainingBalance}`,
                icon: 'success',
                confirmButtonText: '확인',
                confirmButtonColor: '#8744FF',
                background: '#35373D',
                color: '#FFFFFF'
            }).then(() => {
                    $.ajax({
                        url: `/user-points/current`,
                        type: 'GET',
                        success: function(response) {
                            $('#bettingPoints').text(response.bbettingPoints);
                            $('#goldChip').text(response.ggoldChip);
                        },
                        error: function() {
                            console.log('Failed to fetch user points');
                        }
                    });
                updateUIAfterPurchase();
            });
        } else {
            // 서버 응답이 실패일 경우
            Swal.fire({
                title: '구매 실패',
                text: `구매에 실패했습니다. 오류 메시지: ${data.message}`,
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#8744FF',
                background: '#35373D',
                color: '#FFFFFF'
            });
        }
    })
    .catch(error => {
        // 네트워크 오류 처리
        console.error('구매 처리 중 오류 발생:', error);
        Swal.fire({
            title: '네트워크 오류',
            text: '서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해주세요.',
            icon: 'error',
            confirmButtonText: '확인',
            confirmButtonColor: '#8744FF',
            background: '#35373D',
            color: '#FFFFFF'
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search-input');
    const searchButton = document.getElementById('search-button');
    const shoppingItems = document.querySelectorAll('.shopping-item-wrap');

    // 검색어에 따라 상품을 필터링하는 함수
    const filterItems = () => {
        const query = searchInput.value.toLowerCase(); // 검색어를 소문자로 변환
        shoppingItems.forEach(item => {
            const name = item.querySelector('.name').innerText.toLowerCase();
            const description = item.querySelector('.description').innerText.toLowerCase();

            if (name.includes(query) || description.includes(query)) {
                item.style.display = 'block'; // 검색어가 포함된 경우
            } else {
                item.style.display = 'none'; // 검색어가 포함되지 않은 경우
            }
        });
    };

    // 검색 입력 필드에서 Enter 키 눌렀을 때
    $('#search-input').on('keypress', function(event) {
        if (event.which === 13) { // Enter 키 코드
            event.preventDefault();
            filterItems();
        }
    });

    // 검색 버튼을 클릭했을 때
    searchButton.addEventListener('click', () => {
        filterItems();
    });
});
