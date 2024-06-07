-- 사용자 테이블
CREATE TABLE users (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    user_id INT UNIQUE NOT NULL COMMENT '사용자 ID',
    username VARCHAR(50) NOT NULL COMMENT '사용자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    role_id INT NOT NULL COMMENT '역할 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '삭제 여부',
    profile_picture VARCHAR(255) COMMENT '프로필 사진',
    home_background_picture VARCHAR(255) COMMENT '마이홈 배경 사진',
    my_home_url VARCHAR(255) COMMENT '마이홈 URL',
    nickname VARCHAR(50) COMMENT '닉네임',
    todays_message TEXT COMMENT '오늘의 한마디',
    country VARCHAR(100) COMMENT '국가',
    region VARCHAR(100) COMMENT '지역',
    gender ENUM('Male', 'Female', 'Other') COMMENT '성별',
    is_location_public BOOLEAN DEFAULT TRUE COMMENT '지역정보 공개 여부',
    bank_name VARCHAR(100) COMMENT '은행 종류',
    account_number VARCHAR(30) COMMENT '계좌번호',
    id_card_picture VARCHAR(255) COMMENT '주민등록증 사진',
    interests VARCHAR(255) COMMENT '관심사' -- 최대 5개의 관심사를 저장하는 컬럼입니다. 여러 개의 관심사를 저장하기 위해 CSV 형식으로 저장하거나 별도의 관심사 테이블을 만들어야 할 수도 있습니다.
) COMMENT='사용자 정보를 저장하는 테이블';

-- 역할 테이블 (사용자 권한)
CREATE TABLE roles (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    role_id INT UNIQUE NOT NULL COMMENT '역할 ID',
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '역할 이름'
) COMMENT='사용자 권한(역할)을 정의하는 테이블';

-- 게시판 테이블 (다양한 게시판 종류)
CREATE TABLE boards (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    board_id INT UNIQUE NOT NULL COMMENT '게시판 ID',
    board_name VARCHAR(100) NOT NULL UNIQUE COMMENT '게시판 이름',
    description TEXT COMMENT '게시판 설명'
) COMMENT='다양한 게시판 종류를 정의하는 테이블';

-- 게시글 테이블
CREATE TABLE posts (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호', -- 일련 번호
    post_id INT UNIQUE NOT NULL COMMENT '게시글 ID', -- 게시글 ID
    user_id INT NOT NULL COMMENT '사용자 ID (작성자)', -- 사용자 ID (작성자)
    board_id INT NOT NULL COMMENT '게시판 ID', -- 게시판 ID
    title VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
    content TEXT NOT NULL COMMENT '내용', -- 내용
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간', -- 생성 시간
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간', -- 수정 시간
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '삭제 여부', -- 삭제 여부
    views INT DEFAULT 0 COMMENT '조회수', -- 조회수
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE, -- 사용자 ID 외래 키
    FOREIGN KEY (board_id) REFERENCES boards(board_id) ON DELETE CASCADE -- 게시판 ID 외래 키
) COMMENT='게시글 정보를 저장하는 테이블';

-- 댓글 테이블
CREATE TABLE comments (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    comment_id INT UNIQUE NOT NULL COMMENT '댓글 ID',
    post_id INT NOT NULL COMMENT '게시글 ID',
    user_id INT NOT NULL COMMENT '사용자 ID (작성자)',
    content TEXT NOT NULL COMMENT '내용',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간',
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='댓글 정보를 저장하는 테이블';

-- 좋아요 테이블
CREATE TABLE likes (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    like_id INT UNIQUE NOT NULL COMMENT '좋아요 ID',
    post_id INT NOT NULL COMMENT '게시글 ID',
    user_id INT NOT NULL COMMENT '사용자 ID (좋아요 누른 사람)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (post_id, user_id) COMMENT '게시글과 사용자 쌍은 유일해야 함'
) COMMENT='게시글에 대한 좋아요 정보를 저장하는 테이블';

-- 팔로우 테이블
CREATE TABLE follows (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    follower_id INT NOT NULL COMMENT '팔로우하는 사용자 ID',
    following_id INT NOT NULL COMMENT '팔로우 당하는 사용자 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    FOREIGN KEY (follower_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (follower_id, following_id) COMMENT '팔로우 관계는 유일해야 함'
) COMMENT='사용자 간의 팔로우 관계를 저장하는 테이블';

-- 친구 테이블
CREATE TABLE friends (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    user_id1 INT NOT NULL COMMENT '사용자 ID 1',
    user_id2 INT NOT NULL COMMENT '사용자 ID 2',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    FOREIGN KEY (user_id1) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id2) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (user_id1, user_id2) COMMENT '친구 관계는 유일해야 함'
) COMMENT='사용자 간의 친구 관계를 저장하는 테이블';

-- 채팅방 테이블
CREATE TABLE chat_rooms (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    chat_room_id INT UNIQUE NOT NULL COMMENT '채팅방 ID',
    room_name VARCHAR(100) COMMENT '채팅방 이름 (오픈 채팅방인 경우)',
    is_private BOOLEAN DEFAULT FALSE COMMENT '채팅방 종류 (TRUE: 1대1 채팅, FALSE: 오픈 채팅)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간'
) COMMENT='채팅방 정보를 저장하는 테이블';

-- 채팅방 멤버 테이블
CREATE TABLE chat_room_members (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    chat_room_id INT NOT NULL COMMENT '채팅방 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '참여 시간',
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(chat_room_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (chat_room_id, user_id) COMMENT '채팅방과 사용자 쌍은 유일해야 함'
) COMMENT='채팅방에 참여한 사용자들을 저장하는 테이블';

-- 채팅 메시지 테이블
CREATE TABLE chat_messages (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    message_id INT UNIQUE NOT NULL COMMENT '메시지 ID',
    chat_room_id INT NOT NULL COMMENT '채팅방 ID',
    user_id INT NOT NULL COMMENT '사용자 ID (보낸 사람)',
    message TEXT COMMENT '메시지 내용',
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '전송 시간',
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(chat_room_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='채팅방에서 주고받은 메시지를 저장하는 테이블';

-- 파일 테이블
CREATE TABLE files (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    file_id INT UNIQUE NOT NULL COMMENT '파일 ID',
    chat_room_id INT NOT NULL COMMENT '채팅방 ID',
    user_id INT NOT NULL COMMENT '사용자 ID (보낸 사람)',
    file_name VARCHAR(255) NOT NULL COMMENT '파일명',
    file_path VARCHAR(255) NOT NULL COMMENT '파일 경로',
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '업로드 시간',
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(chat_room_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='채팅방에서 주고받은 파일을 저장하는 테이블';

-- 사용자 포인트 테이블
CREATE TABLE user_points (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    user_id INT NOT NULL COMMENT '사용자 ID',
    c_cash INT DEFAULT 0 COMMENT 'C 캐시',
    b_betting_points INT DEFAULT 0 COMMENT 'B 배팅 포인트',
    a_activity_points INT DEFAULT 0 COMMENT 'A 활동 포인트',
    g_gold_chip INT DEFAULT 0 COMMENT 'G 골드칩',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (user_id) COMMENT '사용자 ID는 유일해야 함'
) COMMENT='사용자별 포인트를 저장하는 테이블';

-- 포인트 사용 내역 테이블
CREATE TABLE point_usage (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    usage_id INT UNIQUE NOT NULL COMMENT '사용 내역 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    point_type ENUM('C 캐시', 'B 배팅 포인트', 'A 활동 포인트', 'G 골드칩') NOT NULL COMMENT '포인트 종류',
    amount INT NOT NULL COMMENT '사용된 포인트 양',
    usage_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '사용 날짜',
    purpose VARCHAR(255) NOT NULL COMMENT '사용 목적',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='사용자가 포인트를 어디에 사용했는지 기록하는 테이블';

-- 아이템 테이블
CREATE TABLE items (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    item_id INT UNIQUE NOT NULL COMMENT '아이템 ID',
    item_name VARCHAR(100) NOT NULL COMMENT '아이템 이름',
    description TEXT COMMENT '아이템 설명',
    is_consumable BOOLEAN DEFAULT TRUE COMMENT '소모성 아이템 여부'
) COMMENT='아이템 정보를 저장하는 테이블';

-- 사용자 아이템 소지 테이블
CREATE TABLE user_items (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    user_id INT NOT NULL COMMENT '사용자 ID',
    item_id INT NOT NULL COMMENT '아이템 ID',
    quantity INT NOT NULL COMMENT '아이템 수량',
    acquired_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '획득 시간',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    UNIQUE (user_id, item_id) COMMENT '사용자와 아이템 쌍은 유일해야 함'
) COMMENT='사용자가 소지한 아이템 정보를 저장하는 테이블';

-- 바카라 게임 히스토리 테이블
CREATE TABLE baccarat_game_history (
    seq INT AUTO_INCREMENT PRIMARY KEY COMMENT '일련 번호',
    game_id INT UNIQUE NOT NULL COMMENT '게임 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    player_hand VARCHAR(10) NOT NULL COMMENT '플레이어 카드',
    banker_hand VARCHAR(10) NOT NULL COMMENT '뱅커 카드',
    result VARCHAR(20) NOT NULL COMMENT '게임 결과',
    bet_amount DECIMAL(10, 2) NOT NULL COMMENT '배팅 금액',
    played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '게임 플레이 시간',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='바카라 게임 플레이 기록을 저장하는 테이블';