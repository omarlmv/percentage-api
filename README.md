# Percentage API - Deploy usando Docker Compose

## 🐳 Requisitos Previos

- Tener instalado:
  - [Docker](https://docs.docker.com/get-docker/)

## 🚀 ¡Levanta todo el proyecto en segundos!

### 1. Clona este repositorio o descarga los archivos necesarios

```bash
git clone https://github.com/tu-repositorio/percentage-api.git
cd percentage-api
```

### 2. Verifica que tienes Docker activo

```bash
docker --version
docker-compose --version
```

### 3. Levanta todos los servicios (API + Base de Datos + Mock)

```bash
docker-compose up
```

- La API estará disponible en:

  - [**http://localhost:8080**](http://localhost:8080)

- La base de datos en:

  - **localhost:5432**

- El servicio mock en:

  - [**http://localhost:8081**](http://localhost:8081)

---

## 🛠️ Imágenes Utilizadas

- Imagen de la API publicada en Docker Hub:

  [📲 Ver en Docker Hub](https://hub.docker.com/r/tu_usuario_dockerhub/percentage-api)

  ```bash
  docker pull omarlemv/percentage-api
  ```

---

# 📦 Archivos incluidos

| Archivo              | Descripción                  |
| -------------------- | ---------------------------- |
| `docker-compose.yml` | Orquesta todos los servicios |
| `README.md`          | Instrucciones completas      |

---
