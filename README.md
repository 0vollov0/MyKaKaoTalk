# MyKaKaoTalk

이클립스로 제작 했고 MySql 데이터베이스입니다.

## 영상

[![video](https://i.vimeocdn.com/video/776961119_640x360.jpg)](https://vimeo.com/331539305)

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
