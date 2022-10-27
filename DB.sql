```sql
# DB 생성
DROP DATABASE IF EXISTS SB_AM;
DROP TABLE SB_AM;
CREATE DATABASE SB_AM;
USE SB_AM;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

SELECT * 
FROM article;
SELECT LAST_INSERT_ID()

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED NOT NULL DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    `nickname` CHAR(20) NOT NULL,
    cellphoneNum CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 회원 테스트 데이터 생성(관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '관리자',
`nickname` = '관리자',
cellphoneNum = '01012341234',
email = 'abcdef@gmail.com';

# 회원 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
`nickname` = '사용자1',
cellphoneNum = '01043214321',
email = 'fedcba@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
`nickname` = '사용자2',
cellphoneNum = '01098769876',
email = 'zxcvbnm@gmail.com';



#게시물테이블에 회원정보(memberId)추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

#기존 게시물의 작성자를 2번으로 수정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

#기존게시물에 회원명 JOIN으로 추가해서 보여주기
SELECT article.*,`member`.nickname AS extra__writerName 
FROM article LEFT JOIN `member`
ON article.memberId = `member`.id
WHERE article.id= 1


# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2),..',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부 (0=삭제 전,1=삭제 후)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 기본게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';


# 기본게시판 생성
INSERT INTO board
SET regDate =NOW(),
updateDate = NOW(),
`code`= 'free1',
`name` = '자유게시판';

# 게시판 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

# 1,2 번 게시물을 공지사항 게시물로 수정
UPDATE article
SET boardId = 1
WHERE id IN (1,2);


# 3 번 게시물을 자유게시판 게시물로 수정
UPDATE article
SET boardId = 2
WHERE id IN (3);



# WHERE 1=1(참)을 이용해 boardId에 해당하는 게시글목록만 가져오기
SELECT article.*,`member`.nickname AS extra_writerName 
FROM article LEFT JOIN `member` 
ON article.memberId = `member`.Id
WHERE 1=1 
AND article.boardId = 1
ORDER BY article.id DESC

#게시물갯수늘리기
INSERT INTO article
(
regDate,updateDate,memberId,boardId,title,`body`
)
SELECT NOW(),NOW(),FLOOR(RAND()*2)+1,FLOOR(RAND()*2)+1,CONCAT('제목_',RAND()),CONCAT('내용_',RAND())
FROM article;
SELECT FLOOR(RAND()*2)+1

# 게시물 테이블에 조회수 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;


# reationPoint 테이블생성
CREATE TABLE reactionPoint  (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
	relId INT(10) UNSIGNED NOT NULL COMMENT '관련 데이터  번호',
    `point` SMALLINT(2) NOT NULL
);

# reactionPoint 테스트 데이터
# 1번 회원이 1번 article 에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 article 에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 article 에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 article 에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = -1;

# 3번 회원이 1번 article 에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;


#artice 전체을 서브쿼리로 묶고 reationPoint테이블을 조인해서 조회/좋아요가 눌린 누적수/싫어요가 눌린 누적수
SELECT A.*,
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
SUM(IF(RP.point>0, RP.point ,0)) AS extra__goodReactionPoint,
SUM(IF(RP.point<0, RP.point,0)) AS extra__badReactionPoint
FROM
(
    SELECT A.*, M.nickname AS
    extra__writerName
    FROM article AS A
    LEFT JOIN `member` AS M
    ON A.memberId= M.id WHERE 1
    ORDER BY A.id DESC
) AS A
LEFT JOIN 
reactionPoint AS RP
ON RP.relId = A.id
AND RP.relTypecode = 'article'
GROUP BY A.id


#getForPrintArticle 추천수가 포함된 해당 id 게시글 객체 1개 불러오기
SELECT A.*, M.nickname AS extra__writerName,
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
SUM(IF(RP.point>0, RP.point ,0)) AS extra__goodReactionPoint,
SUM(IF(RP.point<0, RP.point,0)) AS extra__badReactionPoint
FROM article AS A
LEFT JOIN `member` AS M
ON A.memberId = M.id
LEFT JOIN 
reactionPoint AS RP
ON RP.relId = A.id
AND RP.relTypecode = 'article'
WHERE A.id = 3
GROUP BY A.id

#게시글에 대해 추천버튼을 누를수 있는지 권한체크(쿼리실행시 null값이라면 추천버튼을 보여줘야함)
SELECT IFNULL(SUM(RP.point),0) AS s
FROM reactionPoint AS RP
WHERE RP.relTypeCode = 'article'
AND RP.relId = #{id}
AND RP.memberId = #{memberId}
				
# 게시글에 goodReactionPoint,badReactionPoint컬럼추가하기
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

#각게시글별 좋아요와 싫어요의 총합을 조회하기
SELECT * FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId

SELECT RP.relTypeCode, RP.relId, 
SUM(IF(RP.point>0, RP.point, 0)) AS goodReactionPoint,
SUM(IF(RP.point<0, RP.point*-1, 0)) AS badReactionPoint
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId 

#게시글과 추천수 테이블에 둘다업데이트가 되기위해서 게시글을 UPDATE해줘야함(JOIN UPDATE사용)
UPDATE article AS A
INNER JOIN(
    SELECT RP.relTypeCode, RP.relId, 
    SUM(IF(RP.point>0, RP.point, 0)) AS goodReactionPoint,
    SUM(IF(RP.point<0, RP.point*-1, 0)) AS badReactionPoint
    FROM reactionPoint AS RP
    GROUP BY RP.relTypeCode, RP.relId 
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
 A.badReactionPoint = RP_SUM.badReactionPoint


#게시글에 대해 눌렀다면 1, 없다면 0
SELECT COUNT(*) FROM reactionPoint WHERE relId = 3 AND memberId = 1

SELECT * FROM reactionPoint
SELECT * FROM article
SELECT * FROM `member`

```

