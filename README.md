# Trellox

this project is the backend part of my web application called "Trellox" a basic version of the famous Trello built with Spring boot and PostgreSQl for the database part.

### API EndPoints:
-----------------------------
- **Route** : /createUser

  **Method** : Post

  **Description** : Create a new User Account


- **Route** : /login

  **Method** : Post

  **Description** : User Sign In

- **Route** : /verifyEmail

  **Method** : Post

  **Description** : Email Verification using Token

- **Route** : /createBoard

  **Method** : Post

  **Description** : Create a New Board

- **Route** : /getBoard

  **Method** : Get

  **Description** :Fetch Board Data 

- **Route** : /addMember/{boardName}

  **Method** : Post

  **Description** : Add a user to the board to collaborate
  
- **Route** : /addList/{boardName}

  **Method** : Put

  **Description** : Add a new List
  
- **Route** : /addCard/{boardName}

  **Method** : Post

  **Description** : Add a new Card
  

- **Route** : /changeVisibility/{boardName}

  **Method** : Put

  **Description** : change the board visibility
  

- **Route** : /addCard/{boardName}

  **Method** : Post

  **Description** : Add a new Card
  

- **Route** : /deleteList/{boardName}

  **Method** : Delete

  **Description** : Delete a specific List  
