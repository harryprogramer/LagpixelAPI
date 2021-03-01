# LagpixelAPI
https://api.lagpixel.pl

API for Android Minecraft com.Server Menegment, 
Features:
- player list
- close, restart server
- kick, ban, mute player's
- get tps
- SQL user database
- advanced logging system for server
- advanced auto repair system
- powerful http core with apache linux server
- and more...

# API DOCS
_TCP socket json - localhost 2800_
_Android APP -> api.lagpixel.pl -> java api -> plugin paper -> server_

JSON syntax:
- code
- type
- body 
- token
- user
- password
- Protocol_version
- andrioid_version

code, body, body returned

Code: 
- 0 - exit server
- 1 - restart
- 2 - try on
- 5 - get player list | body: none | status, body: players: jsonarray
- 6 - ban | body: player, reason, expires, user | status, body: none
- 7 - kick | body: player, message, user | status, body: none
- 8 - tps | body: none | status, body: tps
- 9 - whitelist on | body: none | status, body: status, 
- 10 - whitelist off | body: none | status, body: status, 
- 11 - remove whitelist | body: player | status, body: none
- 12 - add whitelist | body: player | status, body: none
- 14 - say | body: player, message | status, body: none
- 15 - whitelist bool | body: none | status, body: whitelistbool
- 16 - whitelist list | body: none | status, body: list <array>
- 17 - whitelist player check | body: player | body: bool
- 18 - check conn | body: none | body: none
- 19 - get system data | body: none | body: data{ memory{total, free, using}, cpu{tempcpubuff, tempcpu, measure_time: []}} 

---------------------------------
#### **Example Request**

Ban player Gauza_pauza with reason "bo tak" in expires permament with source from user root 

**GET api.lagpixel.pl/api/**

    {
     "token": "token",
     "code": "7",
     "version_protocol": "012",
     "body": {
       "player": "gauza_pauza",
       "reason": "bo tak",
       "expires": "perm",
       "source": "user"
        }
    "auth": {
        "user": "root",
        "password": "root",
        }
    }

RESPONSE 200 


`
    {
      "status": "OK",
      "version_protocol": "OK",
       "version_android": "OK",
       "authentication": "OK",
       "body": {}
    }
`
---------------------------------

#### **GENERATE TOKEN**

_api.lagpixel.pl/api/gentoken_

`{
  "user": "root",
  "password": "root",
  "protocol": "012",
  "expires": „7” - days
}`

**RESPONSE 200**

`{
  "status": "OK",
  "protocol": "OK",
  "authentication": "OK",
  "token": "token",
  "expires": "7"
}`

--------

###### PROTOCOL VERSION - 08
###### ANDROID VERSION - 010


---------------------------------


PogChamp Telecomunication's System's

Lagpixel's System's

(c) 2021 PogChamp Company. All rights reserved.




