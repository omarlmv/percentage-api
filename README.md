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

## 📖 Acceso a la Documentación Swagger

Una vez que la API esté corriendo, puedes acceder a la documentación Swagger desde cualquiera de las siguientes rutas:

- [http://localhost:8080/webjars/swagger-ui/index.html](http://localhost:8080/webjars/swagger-ui/index.html) *(alternativa directa si las anteriores fallan)*

> Si ves el mensaje `Failed to load remote configuration`, asegúrate de que `/v3/api-docs` esté habilitado correctamente.

---

## 🛠️ Imágenes Utilizadas

- Imagen de la API publicada en Docker Hub:

  [📲 Ver en Docker Hub](https://hub.docker.com/repository/docker/omarlemv/percentage-api/)

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

## 🧪 Ejecución en entorno local (IntelliJ IDEA)

Si deseas **probar la aplicación localmente** sin usar Docker Compose:

1. Abre IntelliJ IDEA.
2. Dirígete a **Run > Edit Configurations...**
3. Crea una nueva configuración de tipo **Spring Boot** (o edita la existente).
4. En el campo **Program arguments**, agrega:

   ```
   --spring.profiles.active=local
   ```

5. Aplica y guarda la configuración.
6. Ejecuta la aplicación normalmente (botón verde ▶️).

Esto permitirá que la aplicación se conecte a las configuraciones locales preparadas para desarrollo.

---

## 🐳 Ejecución usando Docker Compose

Si prefieres **no configurar el perfil local manualmente**, simplemente puedes levantar todo el entorno usando:

```bash
docker-compose up
```

Esto ejecutará:

- La API
- La base de datos
- El servicio mock

**sin necesidad de configurar perfiles manualmente** en tu entorno local.

---

## 📥 Descarga del Proyecto

Puedes descargar este proyecto actualizado con todas las instrucciones directamente desde tu repositorio o carpeta actual.
