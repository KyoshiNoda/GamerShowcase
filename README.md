# GamerShowcase

GamerShowcase is a desktop application built with JavaFX & Firebase that enables users to explore information about various video games, mark their favorite games, and search for other users to discover their favorite games.

<img width="781" alt="Screenshot 2023-12-10 at 9 10 40 PM" src="https://github.com/KyoshiNoda/GamerShowcase/assets/62672803/2f2a3f7b-908a-431b-899d-e5c17242c1f8">

## View and favorite over 500,000+ games with Rawg API
- **Game Viewing:** Explore details about any video game, including title, release date, genre, and more.

- **Game Favoriting:** Easily mark your favorite games for quick access.

- **User Search:** Search for other users and view their curated list of favorite games.

<img width="1343" alt="Screenshot 2023-12-10 at 9 09 39 PM" src="https://github.com/KyoshiNoda/GamerShowcase/assets/62672803/5f56f981-1f8a-4a58-aa90-db3cc260e45f">
<img width="797" alt="Screenshot 2023-12-10 at 9 13 37 PM" src="https://github.com/KyoshiNoda/GamerShowcase/assets/62672803/c53ccfe8-7e11-46cb-a5ea-56ff618ec5a4">

## Secure your account with SendGrid API
 - **Email Verification:** Secure user accounts with email verification using the SendGrid API.
 - Sends requests to a REST API that was self hosted using [Railway](https://railway.app/)

<img width="626" alt="Screenshot 2023-12-10 at 9 11 16 PM" src="https://github.com/KyoshiNoda/GamerShowcase/assets/62672803/94fd24cd-d26f-451d-ba16-bdbf7b36da28">


## Prerequisites
- Java 17
- JavaFX SDK
- Firebase Project with Secret KEY

## Demo Video
https://www.youtube.com/watch?v=bcKpGjdn4cM

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/KyoshiNoda/GamerShowcase.git
2. Setup API key on ENV file from the [Rawg API](https://rawg.io/apidocs)
3. Create a [Firebase Project](https://firebase.google.com/) with Java
4. Download your own refreshToken.json from firebase
5. Replace the refreshToken path in `FirebaseConfig.java` with your own file path. Like below:
   
  ```
  FileInputStream serviceAccount = new FileInputStream("/Users/kyoshinoda/Downloads/refreshToken.json");
  ```
