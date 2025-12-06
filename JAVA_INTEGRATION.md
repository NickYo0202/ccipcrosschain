# Java & Hardhat Integration Guide

This document records the process of integrating the Hardhat smart contract project with a Spring Boot Java backend.

## Architecture
*   **Contracts**: Hardhat (JavaScript/Solidity) - Located in root.
*   **Backend**: Spring Boot 2.7 + Web3j 4.8.7 (Java 8) - Located in `backend/` folder.

## Prerequisites
*   Node.js & NPM
*   Java JDK 1.8
*   Maven 3.x

## Setup & Workflow

### 1. Smart Contract Development (Hardhat)
Compile and deploy your contracts as usual.
```bash
npx hardhat compile
npx hardhat deploy --network sepolia # or localhost
```

### 2. Sync Contract Addresses
We created a script to automatically export the deployed contract addresses from Hardhat to the Java backend's resource file (`backend/src/main/resources/contracts.properties`).

```bash
node scripts/export-address.js
```

### 3. Generate Java Wrappers
Since we are using Java 8, we use a custom workflow to generate Web3j wrappers without installing the `web3j-cli` globally.

**Step 3.1: Extract ABI & BIN**
This script extracts artifacts from Hardhat and filters out unsupported types (like tuples/structs) for Web3j 4.8.7 compatibility.
```bash
node scripts/prepare-web3j.js
```

**Step 3.2: Generate Java Classes**
Run the Maven test specifically designed to generate the wrapper code.
```bash
cd backend
mvn test -Dtest=ContractGeneratorTest
```
*Output location: `backend/src/main/java/com/crosschain/nft/contracts/`*

### 4. Run Integration Tests
A sample integration test is provided to verify the connection to the deployed `MyToken` contract.

1.  Open `backend/src/test/java/com/crosschain/nft/MyTokenIntegrationTest.java`.
2.  Update `RPC_URL` and `PRIVATE_KEY` (optional for read-only) variables.
3.  Run the test:
```bash
cd backend
mvn test -Dtest=MyTokenIntegrationTest
```

## Project Structure Changes

*   `backend/`: New Spring Boot module.
    *   `pom.xml`: Configured for Java 8 compatibility.
    *   `src/main/resources/solidity/`: Temporary storage for ABI/BIN files.
    *   `src/main/resources/contracts.properties`: Auto-generated contract addresses.
*   `scripts/export-address.js`: Updated to target the backend resources folder.
*   `scripts/prepare-web3j.js`: New script for ABI preparation.
*   `.gitignore`: Updated to ignore Maven `target/` directories.

## Troubleshooting Notes
*   **Tuple/Struct Support**: Web3j 4.8.7 (Java 8) does not support Solidity structs in ABI. The `prepare-web3j.js` script automatically filters out functions using structs (like `ccipReceive`) to prevent generation errors.
*   **Maven Version**: The `pom.xml` plugins are pinned to older versions to support Maven 3.1.0.
