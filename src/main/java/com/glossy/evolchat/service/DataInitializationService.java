package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {

    private final ItemRepository itemRepository;

    public DataInitializationService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        initializeData();
    }

    private void initializeData() {
        if (itemRepository.count() == 0) {
            itemRepository.save(new Items(1, "일회용", "닉네임 변경권", "프로필을 클릭하여 계정관리 메뉴에서 닉네임을 변경하실 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(2, "일회용", "프로필 사진 등록권", "프로필을 클릭하여 프로필 사진을 변경할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(3, "일회용", "마이홈 배경 등록권", "마이홈 배경 이미지를 등록, 변경할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(4, "일회용", "오늘의 한마디 변경권", "오늘의 한마디를 변경할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(5, "무제한", "움직이는 프로필 이미지", "WebP, GIF확장자를 업로드하여 움직이는 이미지를 프로필 사진으로 등록 할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(6, "무제한", "채팅 이용권", "신규회원 대기시간을 무시하고 전체 채팅과 오픈채팅을 이용할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(7, "24시간", "오픈채팅 개설권", "오픈채팅에 나만의 방을 개설할 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(8, "1개월", "채팅 GIF & 이모티콘 이용권", "채팅 메세지에 GIF와 이모티콘을 보낼 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(9, "1개월", "채팅 이미지 & 파일 업로드", "채팅 메세지에 이미지를 보낼 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(10, "1개월", "메신저 이용권", "회원님들과 자유롭게 메세지를 주고 받으실 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(11, "일회용", "1,000만 배팅 포인트 충전권", "보유한 배팅 포인트에서 1,000만 배팅 포인트 추가지급 됩니다. 5,000만 이상의 배팅 포인트 시 사용이 불가합니다.", 0, 0, 1000000000));
            itemRepository.save(new Items(12, "에볼챗 가상화폐", "골드칩", "원하는 회원님에게 도네이션(후원)을 목적으로 사용되는 애볼챗 가상화폐로 환전이 가능합니다.", 19800, 198, 0));
            itemRepository.save(new Items(13, "1개월", "에볼챗 프리미엄 구독권", "닉네임, 프로필 사진, 마이홈 배경, 오늘의 한마디, 채팅, 메신저, 채팅 이미지, GIF, 이모티콘 무제한 이용가능", 19800, 198, 0));
            itemRepository.save(new Items(14, "일회용", "전적 초기화", "지난 배팅 내역을 초기화 합니다.", 19800, 198, 0));
            itemRepository.save(new Items(15, "일회용", "활동정지 해제", "애볼쳇 이용규정을 어겨 활동정지된 상태에서 벗어 날 수 있습니다.", 19800, 198, 0));
            itemRepository.save(new Items(16, "", "", "", 0, 0, 0));
            itemRepository.save(new Items(17, "1개월", "고성능 확성기", "모든 채팅창 상단에 메세지를 5분간 노출시켜 줍니다. 이미 노출된 메세지가 있다면 순서대로 노출 됩니다.", 19800, 198, 0));
            itemRepository.save(new Items(18, "일회용", "무료충전 리필권", "무료충전을 10회 리필합니다. 잔여 무료충전 횟수에서 10회가 추가됩니다.", 19800, 198, 0));
        }
    }
}
