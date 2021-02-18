# **Error Code for Lagpixel API:**

Error code explain:
e.g

    {"status": "ERROR", "errorCode": "0x6e10", "date" ...}

# PAPER

    - e0 - value 'code' could not be parsed to integer value
    
    - e1 - the body key could not be parsed
    
    - e2 - value 'code' out of range
    
    - e3 - restarting script is not found
    
    - e4 - general json parser error
    
    - e5 - general ban error
    
    - e6 - kick error api, player after kick is still on server (kick)
    
    - e7 - method is not enabled (run server)
    
    - e8 - 'player' body not exist (general)
    
    - e8 - general api error (general)
    
    - e9 - 'expires; body not exist or is incorrect (ban)
    
    - e10 - invalid ban date, out of range (ban)
    
    - e11 - invalid 'reason' body or is incorrect (general)
    
    - e12 - invalid 'user' body or us incorrect (general)
    
    - e13 - player is already have ban (ban)
    
    - e14 - player is not on server (kick)
    
    - e15 - 'player' body is not exist or incorrect (kick)
    
    - e16 - whitelist is already on (whitelist on)
    
    - e17 - whitelist is already off (whitelist off)
    
    - e18 - the client requested a whitelist player list when it is disabled
    
    - e19 - whitelist file in server is not exist
    
    - e20 - player is already on whitelist
    
    - e21 - player after added to whitelist still he's no there
    
    - e22 - player is already not whitelisted
    
    - e23 - broadcast message failed
    
    - e24 - 'message' key not found or incorrect


#LAGPIXEL
    - e1 - unknown paper api response
   
    - e2 - no connection with paper api 

    - e3 - incorrect paper response to system

    - e4 - paper error but invalid response errorCode or reason

    - e5 - paper timeout

    - e6 - incorrect paper response, body: bool, is incorrect

    - e7 - incorrect json structure

    - e8 - invalid id code
    
    - e8 - id code out of range

    - e9 - invalid body key

    - e10 - invalid auth data