# JapanGO Project

This repository contains both the front-end (FE) and back-end (BE) for the JapanGO project.

## Prerequisites

- [Git](https://git-scm.com/) installed on your machine.
- [Node.js and npm](https://nodejs.org/) installed for running the front-end.
- [Docker](https://www.docker.com/) installed for running the back-end.

---

## Steps to Run the Project

### 1. Clone the Repository
Clone the project repository to your local machine:
```bash
git clone https://github.com/PluckySquirrel/japango.git
```
Navigate to the project directory:
```bash
cd japango
```
### 2. Run the Back-end
If Docker is not installed, please install it from [Docker's official website](https://www.docker.com/).

Navigate to the `be/` directory:
```bash
cd ../be
```
Start the back-end services using Docker Compose:
```bash
docker-compose up
```
The back-end will be available at [http://localhost:8080](http://localhost:8080) (or the port specified in the `docker-compose.yml` file).
### 3. Run the Front-end
Navigate to the `fe/` directory:
```bash
cd fe
```
Install dependencies:
```bash
npm install
```
Start the front-end:
```bash
npm start
```
The front-end will be available at [http://localhost:3000](http://localhost:3000).
## Notes
- Make sure the front-end and back-end are running simultaneously for full functionality.
- For further details on API endpoints, configurations, or troubleshooting, refer to the project documentation.
