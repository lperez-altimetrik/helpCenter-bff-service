# 🚀 HelpCenter BFF Service

The **HelpCenter BFF Service** is a Backend for Frontend (BFF) application designed to handle API requests and enhance the performance of the HelpCenter platform.

---

## 🐳 Build and Run the Docker Image

Follow these steps to build and run the Docker image locally:

### **Build the Docker Image**
```bash
  docker build -t helpcenter-bff-service .
```

### **Run the Docker Container**
```bash
  docker run -p 8080:8080 --name helpcenter-bff helpcenter-bff-service
```
This maps your local machine’s port 8080 to the container’s port 8080.

---

## 📦 Commands to Manage Docker Containers

Follow these steps to build and start the containers locally:

### **Build and Start the Containers**
```bash
  docker-compose up --build
```

### **Stop the Containers**
```bash
  docker-compose down
```

---

## ✅ Verify the Dockerized Application

### **Access the Application**
Visit http://localhost:8080 to ensure the application is running.

### **Check Application Logs**
```bash
  docker logs helpcenter-bff
```

---

## 🔗 Verify Redis Service

### **Access Redis**
Ensure the Redis service is running and accessible by executing:

```bash
  docker exec -it redis_cache redis-cli
```

### **Ping Redis**
Run the following command inside the Redis CLI:

```bash
  PING
```
A successful response will return PONG.

---

## 🛠 Environment Variables

Ensure the following environment variables are configured for the application:

| Variable           | Description           | Example                       |
|--------------------|-----------------------|-------------------------------|
| `SPRING_REDIS_HOST`| Redis host            | `redis`                       |
| `SPRING_REDIS_PORT`| Redis port            | `6379`                        |
| `OKTA_ORG_URL`     | Okta organization URL | `https://dev-example.okta.com`|
| `OKTA_CLIENT_TOKEN`| Okta API client token | `your-okta-token`             |

---

## 💡 Tips

1. If you're running the application for the first time, ensure Docker Desktop is installed and running.
2. Use ```docker-compose logs -f ``` to monitor logs from all services in real time.
3. Restart individual services with ```docker-compose restart <service_name>```.