### Banking Application

#### To get Started create a file named `keys.properties` in resource folder and paste your credentials as shown
```properties
# database keys
db.username = your_DB_name
db.password= Your_passord
db.url =  Your_Db_url

# mail secrets for sending users emails
mail.username = Email_username
mail.password = Emai_Passord

### Google credentials
google.clientId = Your_client_Id
google.clientSecret = Your_Secret
google.redirectUrl = _redirectUrl
google.userInfoUrl  = https://www.googleapis.com/oauth2/v3/userinfo
```

#### To run the project use this command
```bash
 ./mvnw spring-boot:run
```

- if you get mvnw is not a command error you will need to change permission for it to be executable
- You will use command preferred for your OS in Linux run
  ```bash
  chmod +x mvnw      
  ```
