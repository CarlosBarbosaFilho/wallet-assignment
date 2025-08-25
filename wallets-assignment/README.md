# Wallet Bank Project
This document provides an overview of the Wallet Bank project, detailing the technologies used, architectural design, and the functionality of each microservice.

* Running the Application
  To get the application up and running, follow these steps in order:

* Run the script STEP1-START-ENVIRONMENT-HML.sh: This will execute all the project's Docker containers.
* Run the script STEP2-START-RESOURCES-AWS-LOCALSTACK.sh: This will create all the necessary AWS resources within LocalStack.
* Download and use the HML collection (Insomnia): You can find this collection in the project's docs directory.

## Technologies Utilized

Java: Version 17
Apache Maven: Version 3.9.2
Spring Boot: Version 3.5.4
Spring Cloud: Version 2025.0.0
Spring Cloud AWS: Version 3.1.0
ModelMapper: Version 3.1.1
PostgreSQL: Databases
Docker: Version 27.3.1

## Architectural Design
The project employs a Hexagonal Architecture (also known as Ports and Adapters).

This architecture was chosen to protect the core business logic, making it independent of external technologies. This results in a more robust, testable, and evolvable software. It helps build sustainable, high-quality projects over the long term by separating the core domain from external concerns like databases, frameworks, and APIs.

## Developed Services and Rationale

1. Microservice ms-wallets
   This service was developed to store and protect the domain logic related to the Wallet product. Its internal responsibilities are exclusive to wallet management, but it provides the necessary services to activate its internal requirements, which are the functionalities related to its domain.

2. Microservice ms-transactions
   The transaction microservice was developed to perform operations involving clients and their wallets throughout the transactional process. It also handles the distribution of notifications and the historical storage of these processes.

3. Microservice ms-clients
   The client service ensures the link between a client and their wallet. A wallet is only meant to exist if it is linked to a real client, making client data a foundational part of this process.

## Business Rules

### Microservice ms-wallets

- API Endpoints

Wallet Creation: The service must allow a wallet to be created.
Required data for registration: wallet number, wallet type, and the client (wallet owner).
All fields are mandatory.

* Balance Inquiry by Wallet Number: The service must allow a wallet's balance to be checked by its number (passed as a parameter).
  The wallet number must exist for the balance inquiry. Its absence will result in a system exception.

* Balance Inquiry by Client Document: The service must allow a wallet's balance to be checked by the document of the wallet's owner (client).
  The client's document linked to the wallet must exist for the balance inquiry. Its absence will result in a system exception.

Multiple Wallets: A client may have multiple wallets linked to them.

### Microservice ms-transactions

- Core Functionality

* Deposit:
  The service must allow a deposit to be made into a wallet.
  To perform a deposit, the transaction amount and the wallet number must be provided.
  For this process, the source wallet and the destination wallet are the same.

* Withdrawal: The service must allow a withdrawal from a wallet.
  To perform a withdrawal, the transaction amount and the source wallet number must be provided.
  When starting the withdrawal process, the system must validate that the transaction amount is less than or equal to the current wallet balance.
  Wallets cannot have a negative balance.

* Transfers: The service must allow value transfers between wallets.
  For a transfer to be completed, the source wallet must have a positive and sufficient balance for the transaction. Otherwise, the system will throw a business exception.
  All fields involved in the transaction (source wallet, destination wallet, and transaction amount) are mandatory.

* Balance Inquiry by Wallet Number: The wallet number must exist for the balance inquiry.
* Balance Inquiry by Client Document: The client's document linked to the wallet must exist for the balance inquiry.

### Microservice ms-clients

- Client Management

The service must provide a CRUD (Create, Read, Update, Delete) interface for clients.
It must also support queries by:
* Email
* Document (CPF)
* ID

Uniqueness: There cannot be duplicate client registrations in the system based on the same email or document (CPF).

Mandatory Fields: All client registration fields, except for salary, are mandatory.


## Project Diagrams and Architectural Design
This document provides a detailed overview of the project's architecture, including its diagrams, components, and communication flows.

### Architecture Diagrams

System Diagram (Services)
This diagram illustrates the main services and their relationships within the system.

### Architectural Overview
This diagram provides a high-level view of the entire system architecture.

### Architectural Components

### Current Architectural Agents

* API Gateway: The single entry point for all microservices.
* MS-CLIENTS: Microservice for managing client data.
* MS-TRANSACTIONS: Microservice for handling all transaction-related operations.
* MS-WALLETS: Microservice dedicated to wallet management.
* SNS AWS: Amazon Simple Notification Service for fan-out messaging.
* SQS AWS: Amazon Simple Queue Service for message queuing.
* DynamoDB: Database used for specific microservices.


### Future Architectural Agents

* SQS - DLQ: Dead-Letter Queue for handling message failures.
* LAMBDA - PROCESSOR: A Lambda function to reprocess messages from the DLQ.
* MS-NOTIFICATION: A new microservice for handling notifications.
* SES AWS: Amazon Simple Email Service for sending emails.

### Communication Flow

#### Client Flow
* To create a new client, a request with the following mandatory fields is sent to the API:

{
"name": "Gabryella Waleska",
"email": "belabela@gmail.com",
"document": "99999999999",
"income": 1000
}

Creation Response:

{
"id": 1756135947850,
"name": "Gabryella Waleska",
"email": "belabela@gmail.com",
"document": "99999999999",
"income": 1000,
"createdAt": "25/08/2025 15:32:27",
"status": "ACTIVE"
}

#### Wallet Flow
To create a new wallet, a request with the following mandatory fields is sent:

{
"walletNumber": "0001",
"walletType": "SAVINGS",
"client": "1756135947850"
}

Creation Response:

{
"id": 1,
"walletNumber": "0001",
"walletType": "SAVINGS",
"walletStatus": "ACTIVE",
"balance": 0,
"createdAt": "25/08/2025 15:32:36",
"client": 1756135947850
}

#### Flow Details: For this process, the ms-wallets service internally retrieves the client's data synchronously to link the wallet to its owner. This is done using an HTTP call with OpenFeign. The data is then stored and the link is successfully completed.

### Transaction Flow
To process calls and flows within the ms-transactions service, a synchronous call is made to the ms-wallets service to process transaction information for deposits, transfers, and withdrawals.

#### Flow Details: The transaction flow is carried out in two main steps, plus an exception flow.

Step 1: The transaction is first recorded with a PENDING status.

Step 2: After the subprocess of validations, if approved, a new transaction record with the same data is saved with a COMPLETED status.

Exception Flow: If there is any invalidity during the process, the transaction is recorded with a FAILED status (including the transaction data).

This record is then sent to an SNS topic so that interested queues can retrieve the transaction information. In the current architecture, there are two queues: a processing queue to notify clients about the transaction and an assistant DLQ (Dead-Letter Queue) that captures initial failures in the sending process. This DLQ will hold messages for reprocessing by a future Lambda function, which will then resend the messages to the main queue. The main queue will be consumed by a future notification service (MS-NOTIFICATION), which will use AWS SES to send emails to the client who performed the transaction.
