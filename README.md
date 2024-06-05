# Ramen GO

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Ramen GO é uma API para uma aplicação web chamada RamenGo, uma plataforma para que o usuário possa montar um pedido de ramen, escolhendo os tipos de caldos e proteínas do prato.

### Requires
- Java 17
- SpringBoot 3.3.0

### Endpoints
The API offers the following endpoints:

1. **GET /broths**
   - Description: List all available broths.
   - Responses:
      - 200: List of broths retrieved successfully
      - 403: Error: "x-api-key header missing"
      
2. **GET /proteins**
   - Description: List all available proteins.
   - Responses:
      - 200: List of proteins retrieved successfully
      - 403: Error: "x-api-key header missing"
        
3. **POST /orders**
   - Description: Create a new user with the provided user information.
   - Parameters:
      - `brothId` (path, required): Id of broth to be retrieved.
      - `proteinId` (path, required): Id of protein to be retrieved.
   - Responses:
      - 201: OK (User created successfully)
      - 400: Bad Request: "both brothId and proteinId are required"
      - 403: Forbidden: "x-api-key header missing"
      - 500: Internal Server Error: "could not place order"
### Test

Configure the x-api-key for the project.

You can use tools like curl or Postman to test the endpoints, right below are some curl examples:

- **List available broths:**

  ```
  curl -X GET -H "Content-Type: application/json" -H "x-api-key: YOUR_API_KEY" http://localhost:8080/proteins
  ```

- **List available proteins:**

  ```
  curl -X GET -H "Content-Type: application/json" -H "x-api-key: YOUR_API_KEY" http://localhost:8080/broths
  ```

- **Place an order:**

  ```
  curl -X POST -H "Content-Type: application/json" -H "x-api-key: YOUR_API_KEY" -d "{\"brothId\": \"1\", \"proteinId\": \"1\"}" http://localhost:8080/orders
  ```











