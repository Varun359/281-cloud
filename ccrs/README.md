# Coffee-Maker Cloud Robot System (CCRS)

### Dev Setup

* [Dev Env Setup Notes](https://docs.google.com/document/d/10evkyro9yRHRQ_a1Ayji9QSiZLXayW6U7X9MMvzEtZc/edit#heading=h.7ortsxgrfo4n)
* [Webots Env Setup Notes](https://docs.google.com/document/d/1qmpgJ8LPIZMaXbo1Z7WWV8gch7lpew8rRGjm-IxS9jc/edit) and [Component Design Demo Make-Coffee Notes](https://docs.google.com/document/d/1OEdakL2tQviDd_9HqymZV4syTkGkvjcRSrAodI9yLcU/edit#heading=h.p0zgqyfa63qt)
* [Generate Invoice Notes](https://docs.google.com/document/d/1wdUhiRIhCWBEXUt4orWSee2qKmu9C3-gtjKoX7-_jQg/edit#)
* [Various Ubuntu/Webots/MySQL/MongoDB/Docker Notes](https://docs.google.com/document/d/15k_x43Qns8EHrBrbx7eDXl8_nCBugqnUW08QNbg_SJo/edit#heading=h.5uobbbk4yhyp)

### Description

This source code represents the CCRS APIs based on the initial [Project Design Doc](https://docs.google.com/document/d/18HokAyF6gMG-tvVFHfytWCVZ8AcsDRCTX8oLCbhA9Zw/edit#heading=h.o3hp68t4lrs4).

* Local Testing - http://localhost:8080/ccrs/swagger-ui.html

### Example Swagger Usage

1. Launch the project (see [dev setup](https://docs.google.com/document/d/10evkyro9yRHRQ_a1Ayji9QSiZLXayW6U7X9MMvzEtZc/edit#heading=h.7ortsxgrfo4n) for details). This should result in the Swagger page showing on your browser, similar to:

![Swagger Landing Page](images/swagger-01.png)

2. Create a new Admin user. Select `auth-controller`, expand `/auth/signup`, click on `Try it out`, then enter the new admin user details, for example:

```
{
  "email": "foo@bar.com",
  "password": "123456",
  "role": [
    "admin"
  ],
  "username": "foobar"
}
```
such as shown below, then click `Execute`.

![Create Admin User](images/swagger-create-admin-user.png)

This should result in a successful reponse:

```
{
  "message": "User registered successfully!"
}
```

3. Login using the new admin user credentials. Go to `/auth/login` (still under `auth-controller` section), click on `Try it out`, then enter the username and password previously submitted, for example:

![Admin User Login](images/swagger-admin-login-01.png)

Click `Execute` and it should result in the following:

![Admin User Login](images/swagger-admin-login-02.png)

Note that the response contains:

```
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb29iYXIiLCJpYXQiOjE2NDgzMjc4MjUsImV4cCI6MTY0ODQxNDIyNX0.nyg7BZ22h5UnrWoVXNWrrGML9peXe3F7Bc8u5UEBaP_afG9LTKBEO2RAU66yd9HnVG3FCF-hq5Le1IBz4dfYUg",
  "id": 3,
  "username": "foobar",
  "email": "foo@bar.com",
  "roles": [
    "ROLE_ADMIN"
  ],
  "type": "Basic"
}
```
In particular, it specifies the `token`, which is the JWT token that represents the authentication session with its associated role privilege embedded. This can be used to access APIs that a require specific role to proceed.

4. Call an API endpoint that requires Admin role privileges. For example, select `account-controller`, expand `/account/all` to list all available accounts, click on `Try it out` then `Execute`. This should result in a similar response to the following:

```
can't parse JSON.  Raw result:

<center><h1>Something went wrong</h1></center>
```

This is because the API call needs the JWT token specified in the request header. To fix this issue, make sure to copy the JWT token from earlier, then click on the `Authorize` button on the top-right of the Swagger page, as shown here:

![Swagger Authorize](images/swagger-authorize-01.png)

This should show a dialog prompt to enter the JWT token.

![Swagger Authorize](images/swagger-authorize-02.png)

Enter the following:

```
Bearer [token]
```

where `[token]` is the JWT token you copied earlier, then click on `Authorize` then `Close`. Now, clicking on `Execute` for the API should succeed:

![Swagger Account List All](images/swagger-account-all-success.png)
