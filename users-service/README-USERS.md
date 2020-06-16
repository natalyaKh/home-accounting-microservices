# users-service:

Functionality:

SignUp (without authentication)

SignIn (getting JWT Token): using e-mail and password

+CRUD

При создлании пользователя  createUSer создается emailVerificationToken, который долженбыть отправлен в письме 
пользователю. Когда пользователь нажмет на ссылку в письме - emailVerificationStatus должке стать true,
а emailVerificationToken будет удален из БД.
До этого, пока emailVerificationStatus = false логин запрещен.

Swager -> 

!!! Token contains UserID !!!