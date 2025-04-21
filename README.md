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

---

## 🛠️ Imágenes Utilizadas

- Imagen de la API: `docker pull tu_usuario_dockerhub/percentage-api:latest`
- Base de datos: imagen oficial de Postgres `postgres:15`
- Mock: imagen oficial de `rodolpheche/wiremock`

---

# 📦 Archivos incluidos

| Archivo              | Descripción                  |
| -------------------- | ---------------------------- |
| `docker-compose.yml` | Orquesta todos los servicios |
| `README.md`          | Instrucciones completas      |

---
