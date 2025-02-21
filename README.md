# 🚀 GitHub User Activity

A project that analyzes GitHub user activity based on data fetched through the GitHub API. The project demonstrates key concepts such as basic API usage, JSON processing, data filtering, in-memory caching, date-time handling, and modular design.

## ✨ Features

- 🔗 Fetch and process user activity from the GitHub API
- 🎯 Filter data based on event type
- ⚡ Cache data in-memory for faster performance

---

## 📖 Table of Contents

1. [🔧 Installation](#installation)
2. [🚦 Usage](#usage)
3. [🛠️ Technologies Used](#technologies-used)
4. [🤝 Contributing](#contributing)
5. [✅ To-Do](#-to-do)
6. [📜 License](#license)

---

## 🔧 Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/username/github-user-activity.git
   ```

2. Navigate into the project directory:
   ```bash
   cd github-user-activity
   ```

3. Set up the environment:
    - Ensure that **Java 23** is installed.
    - Install **Maven**

4. Build the project:
   ```bash
   mvn clean install
   ```
---

## 🚦 Usage

1. Run the application:
   ```bash
   java -jar target/github-user-activity.jar USERNAME
   ```
   OR, if you're using an IDE like IntelliJ IDEA, simply run the main class.

---

## 🛠️ Technologies Used

The project was developed using the following technologies and libraries:

- **Language**: Java ☕
- **Build Tool**: Maven 🛠️
- **Libraries/Frameworks**:
    - JSON Processing (org.json) 📦
- **GitHub API**: Fetching and processing user data 🔍
- **Caching**: Basic in-memory caching techniques 💾

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`feature/some-feature`).
3. Commit your changes.
4. Push the branch.
5. Submit a pull request.

---

## ✅ To-Do

- [x] Add a feature to filter activity by event type (e.g., PushEvent, CreateEvent, etc.).
- [x] Display the fetched activity in a more structured format for better readability.
- [x] Implement caching for fetched data to improve performance and reduce repeated API calls.
- [ ] Add testing
- [ ] Add Spring Boot
- [ ] Refactor for cleaner code

## 📜 License

This repository is licensed under the [MIT License](LICENSE).

---
https://roadmap.sh/projects/github-user-activity


