# Scrabble API

Scrabble-API, a REST backend API created using Spring Boot, Spring Data JPA with an in-memory H2 database, 
and Mockito for testing. This API serves as the backend for [Scrabble-FE](https://github.com/naweenn/scrabble-fe).

Scrabble-FE is a frontend implementation for a simplified version of classic word game Scrabble

## Table of Contents

- [Project Overview](#project-overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Project Overview

The Scrabble-API is a backend API that facilitates score management for 
[Scrabble-FE](https://github.com/naweenn/scrabble-fe). This API leverages the following technologies:

- **Spring Boot**: A powerful framework for building Java applications with ease.
- **Spring Data JPA**: Simplifies data access and provides a repository pattern for working with the database.
- **H2 In-Memory Database**: A lightweight, in-memory database for development and testing.
- **Mockito**: A popular Java testing framework for unit testing and mocking dependencies.
- **Gradle**: The build tool used for this project.

## Getting Started

Follow these steps to set up and run the Scrabble-API on your local machine.

### Prerequisites

Before you begin, make sure you have the following software and tools installed:

- Open Java Development Kit (JDK) 17 or higher
- Gradle (for building and managing dependencies)

### Installation

1. **Clone the Repository:** Start by cloning this repository to your local machine using Git.

`git clone https://github.com/naweenn/scrabble-api.git`

2. **Navigate to the Project Folder:** Change your working directory to the project folder.

`cd scrabble-api`

3. **Build the Application:** Use Gradle to build the application.

`gradle build`

4. **Run the Application:** Start the Spring Boot application.

`java -jar scrabble-api-0.0.1-SNAPSHOT.jar`

The API should now be up and running at http://localhost:8080.

## Usage

The Scrabble-API provides several endpoints for Scrabble-FE. You can interact with the API using tools 
like Postman, cURL, or any HTTP client.

## API Endpoints

Endpoints provided by the API:

- `GET /api/score/rules`: Retrieve list of scoring rules.
- `POST /api/score`: Record new score.
- `GET /api/score/top-scores`: Retrieve top 10 scores from the recorded scores.

## Testing
The Scrabble-api includes unit tests to ensure the correctness of its functionality. Mockito is used for mocking 
dependencies in tests. You can run the tests using the following command:

`gradle test`

