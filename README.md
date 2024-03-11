
# Blog-Bounty - Share Your Knowledge Here.

The project was likely created to provide a platform for users to share their thoughts, ideas, and experiences through blog posts. It aims to simplify the process of creating and managing blog content, making it easier for both writers and readers to engage with the platform.

Based on the project structure and files, the purpose of this project appears to be the development of a web application that allows users to create, read, update, and delete blog posts. The application likely includes features such as user authentication, secure with JWT, CRUD operations for managing blog posts, and possibly comment functionality.

One of the key problems this project solves is the need for an easy-to-use and customizable blog platform. By providing a structured backend application, developers can focus on building the frontend and user interface, while leveraging the backend to handle the blog's core functionalities. This separation of concerns allows for a more efficient development process and a better user experience overall.


## APPLICATION APIS DOCS

![Blog App Docs](https://github.com/Anik-Dv/Blog-Application-BackEnd/assets/104680177/ed553b73-22ab-4c71-b274-06793c11d4a0)


## Demo
[Application Api Request And Response Demo](https://github.com/Anik-Dv/Blog-Application-BackEnd/assets/104680177/f8f9c7ee-9dde-4846-bc8d-cd59636ecac0)


## API Reference

#### Get all Posts

```http
  GET /api/posts
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get Post

```http
  GET /api/post/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Get Posts with Pagination & Sorting

```http
  GET /api/posts/feed?pageNumber=${pageNumberHere}&pageContentSize=${itemNumberHere}&sortBy=postId&sortDir=desc
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `pageNumber`      | `string` | **Optional**. give which page posts are you need to fetch |
| `pageContentSize`      | `string` | **Optional**. give how many post are you need to fetch |
| `sortBy`      | `string` | **Optional**. give which which way are you fetch to posts, example [ 'title', 'postId', 'content', 'createDate' ] |
| `sortDir`      | `string` | **Optional**. give what order to fetch posts example Ascending then use -> [ asc ] or Descending then use [ desc ] |


#### Create Post

```http
  POST /api/user/${id}/category/${id}/post/create
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | **Required**. Your User Id |
| `categoryId` | `string` | **Required**. Your Category Id |

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postData` | `JSON as String` | **Required**. Write your post data |
| `file` | `jpg, png, jpeg` | **Optional**. Attach Your Post Images |


#### Update Post

```http
  PUT /api/post/${id}/update
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      | `string` | **Required**. id of post to update |


| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postData` | `JSON` | **Required**. edit your post data |


#### Delete Post

```http
  DELETE /api/post/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | **Required**. Your post id |


#### Get Post by User

```http
  GET /api/user/${id}/posts
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of user to fetch user posts|

#### Get Post by Category

```http
  GET /api/category/${id}/posts
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of category to fetch category posts|

#### Search Post by Keyword

```http
  GET /api/search/post/${keyword}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Keyword`      | `string` | **Required**. keyword to fetch posts when match any keyword of post.|


## User Authentication

#### Login User

```http
  POST /api/auth/login
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `JSON` | **Required**. write your username.|
| `password`      | `JSON` | **Required**. write your password.|


#### Singin User

```http
  POST /api/auth/signin
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `JSON` | **Required**. write your full name.|
| `email`      | `JSON` | **Required**. write your email address.|
| `password`      | `JSON` | **Required**. write your password.|
| `comfirmPassword`      | `JSON` | **Required**. write your confirm password.|


## Sawager Apis Documentation

#### View JSON Docs

```http
  GET /api-docs
```

#### Graphical Interface Docs

```http
  GET /swagger-ui/index.html
```





## Features

- User Authentication JWT & OAuth2
- Authorization
- CRUD Operations
- Comment Functionality
- Customizable Templates
- Scalability 

## Installation

Install Project

## 1. Clone the Repository:
```bash
  git clone https://github.com/Anik-Dv/Blog-Application-BackEnd.git

```

## 2. Navigate to the Project Directory:
```bash
  cd Blog-Application-BackEnd

```

## 3. Install Dependencies:
```bash
  mvn clean install

```

## 4. Run the Application:
```bash
  mvn spring-boot:run

```

## 5. Access the Application:
Once the application is running, you can access it at http://localhost:9090 in your web browser.

## 6. Configure OAuth2:
The project likely includes configuration files for OAuth2, such as application.properties or application.yml. Edit these files to configure your OAuth2 provider, client ID, client secret, etc., as per your requirements.

## 7. Test the Application:
Use a REST client or a browser to test the API endpoints, such as creating a new blog post or retrieving existing posts.
## Lessons Learned

By building this project and integrating it with SonarQube, Jenkins, Nexus Repository, and JaCoCo, I was solved the problem of creating a secure, efficient, and well-managed blog application. I provided a way for users to log in easily, interact with the application through APIs, and manage their blog posts. Additionally, I ensured that My code meets high-quality standards, is continuously integrated and deployed, and has sufficient test coverage. Overall, this project likely helped me develop valuable skills in Java Spring Boot development, web application security, and DevOps practices.

## Here are some key things I probably learned:

## 1. Setting Up Spring Boot:
You learned how to start a Spring Boot project, add dependencies, and configure basic settings.

## 2. OAuth2 Authentication:
By using OAuth2, you learned how to let users log in with Google, Facebook, Github or other services, which is more secure and convenient than traditional methods.

## 3. Creating RESTful APIs: 
I was learned how to design and build APIs for Backend application, allowing users to interact with it and perform actions like creating, reading, updating, and deleting blog posts.

## 4. Database Integration: 
I probably learned how to connect an application to a database to store and retrieve data, like blog posts and user information.

## 5. Redis Caching: 
I was learned how to integrate Redis as a caching solution in an application, which can improve performance by storing frequently accessed data in memory.

## 6. Security Best Practices: 
Implementing OAuth2 and JWT Authentication Token taught about security practices for web applications, like managing access tokens and protecting user data.

## 7. Handling Errors: 
I Was likely learned how to deal with errors and exceptions in An application to ensure a smooth user experience.

## 8. Deployment: 
I was learned how to deploy an application in AWS to make it available online for users to access.

## 9. Testing: 
I was probably gained experience in testing an application to ensure it works correctly, including JUnit testing And Mockito and possibly other types of testing.

## 10. SonarQube Integration: 
I was learned how to integrate SonarQube into an project to analyze code quality and identify potential issues or bugs.

## 11. Jenkins Integration: 
I was learned how to set up Jenkins for continuous integration, allowing you to automate the build, test, and deployment processes.

## 12. Nexus Repository Integration: 
I was learned how to use Nexus Repository Manager to manage artifacts and dependencies, ensuring that an application is built and deployed correctly.

## 13. JaCoCo Integration: 
I was learned how to use JaCoCo to generate code coverage reports, which can help you identify areas of your code that need additional testing.


## Tech Stack

## Backend
- Java with Spring Boot: For building the backend application and RESTful APIs.
- Spring Security with OAuth2: For authentication and authorization.
- Redis: For caching frequently accessed data.
- MySQL or another relational database: For storing blog posts, user information, and other data.

## DevOps:
- Jenkins: For continuous integration and deployment (CI/CD).
- Nexus Repository: For managing artifacts and dependencies.
- SonarQube: For code quality analysis.
- JaCoCo: For code coverage reporting.

## Other Tools and Libraries:
- Maven: For project management and dependency resolution.
- JUnit and Mockito: For unit testing and mocking dependencies.
- Swagger: For API documentation.
- Log4j or SLF4J: For logging.

## Deployment:
- Docker: For containerization of the application.
- Deploy a cloud provider AWS for hosting the application.

## Frontend I Was Builded Already:
- React frontend framework: For building the user interface. See Here => 👩‍💻 [Front-End Blog Application](https://github.com/Anik-Dv/Blog-Application-FrontEnd-React)


## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://anik-dv.github.io/AnikKarmokar.github.io//)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/anikkarmokar/)
[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/)

