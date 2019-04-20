# MyKaKaoTalk

Java Swing과 MultiThread Socket, JDBC(MySQL) 을 이용하여 카카오톡(구버전) 을 구현해 보았습니다.

## 기능

* 회원 등록
* 친구 검색
* 친구 추가/삭제
* 프로필 수정
* 채팅방 개설
* 채팅방 참가/나가기
* 채팅방 대화 친구 추가

## 영상

[![video](https://i.vimeocdn.com/video/776961119_640x360.jpg)](https://vimeo.com/331539305)

# 설명

좌측) 로그인 화면  우측) 회원가입 화면

![Implementation_images](readme_image/Implementation_image/imp_01.jpg)

메인화면

![Implementation_images](readme_image/Implementation_image/imp_02.jpg)

좌측) 채팅방 설정  우측) 채팅방 리스트 

![Implementation_images](readme_image/Implementation_image/imp_03.jpg)

친구 추가 좌측) admin1 계정 우측) admin 계정

![Implementation_images](readme_image/Implementation_image/imp_04.jpg)

좌측) 본인 계정 프로필 화면 우측) 친구 계정 프로필 화면

![Implementation_images](readme_image/Implementation_image/imp_05.jpg)

# DataBase

## MyKaKao DB Table

![DataBase](readme_image/Implementation_image/imp_06.jpg)

* addfriendlist

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| senderID | varchar(24) | NO | PRI | NULL |  |
| receiverID | varchar(24) | NO | PRI | NULL |  |

* chatlog

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| senderID | varchar(24) | NO | PRI | NULL |  |
| roomID | int(11) | NO | PRI | NULL |  |
| chatContent | varchar(100) | YES |  | NULL |  |
| chatTime | datetime | NO | PRI | 0000-00-00 00:00:00 |  |

* chattingroom

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| roomID | int(11) | NO | PRI | NULL |  |
| roomName | varchar(48) | NO | PRI | NULL |  |
| member | varchar(24) | NO | PRI | NULL |  |

* friend

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| userID | varchar(24) | NO | PRI | NULL |  |
| friendID | varchar(24) | NO | PRI | NULL |  |

* singlechat

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| senderID | varchar(24) | NO | PRI | NULL |  |
| roomID | int(11) | NO | PRI | NULL |  |
| chatContent | varchar(100) | YES |  | NULL |  |
| chatTime | datetime | NO | PRI | 0000-00-00 00:00:00 |  |

* user

| Field | Type | Null | Key | Default | Extra |
|:-------|:-------|:-------|:-------|:-------|:-------|
| userID | varchar(24) | NO | PRI | NULL |  |
| userPassword | varchar(24) |  | PRI | NULL |  |
| userName | varchar(16) | NO |  | NULL |  |
| userEmail | varchar(32) | NO |  | NULL |  |
| userTel | varchar(16) | NO |  | NULL |  |
| profile | varchar(32) | YES |  |  |  |

# Class Diagram

## Client

* client

![client_Diagram](readme_image/diagram/Client/client.png)

* user

![user_Diagram](readme_image/diagram/Client/user.png)

## Server

* server

![server Diagram](readme_image/diagram/Server/server.png)

* addfriendlist

![addfriendlist_Diagram](readme_image/diagram/Server/addfriendlist.png)

* chatlog

![chatlog_Diagram](readme_image/diagram/Server/chatlog.png)

* chattingroom

![chattingroom_Diagram](readme_image/diagram/Server/chattingroom.png)

* friend

![friend_Diagram](readme_image/diagram/Server/friend.png)

* singlechat

![singlechat Diagram](readme_image/diagram/Server/singlechat.png)

* user

![user_Diagram](readme_image/diagram/Server/user.png)
