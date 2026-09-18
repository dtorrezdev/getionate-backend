# Sistema de Microservicios con NestJS

## 📋 Descripción

Sistema completo de microservicios desarrollado con **NestJS** y **TypeScript**, que incluye:

- 🌐 **API Gateway** - Punto de entrada principal
- 👤 **User Service** - Gestión de usuarios con PostgreSQL
- 📦 **Product Service** - Gestión de productos con MongoDB  
- 🔔 **Notification Service** - Notificaciones en tiempo real con WebSockets y Redis

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │   User Service  │    │ Product Service │
│    (Port 3000)  │────│   (Port 3001)   │    │   (Port 3002)   │
│                 │    │   PostgreSQL    │    │    MongoDB      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                                              
         │              ┌─────────────────┐              
         └──────────────│Notification Svc │              
                        │   (Port 3003)   │              
                        │ WebSockets+Redis│              
                        └─────────────────┘              
```

## 🚀 Inicio Rápido

### Prerrequisitos
- Docker y Docker Compose
- Node.js 20+ (para desarrollo local)
- Git

### 1. Clonar el repositorio
```bash
git clone https://github.com/dtorrezdev/getionate-backend.git
cd getionate-backend
```

### 2. Configurar variables de entorno
```bash
cp env.example .env
# Editar .env según tus necesidades
```

### 3. Iniciar todos los servicios
```bash
# Opción 1: Script automatizado
./scripts/start-dev.sh

# Opción 2: Docker Compose manual
docker-compose up -d
```

### 4. Verificar servicios
- 🌐 API Gateway: http://localhost:3000
- 👤 User Service: http://localhost:3001  
- 📦 Product Service: http://localhost:3002
- 🔔 Notification Service: http://localhost:3003

## 📊 Servicios y Puertos

| Servicio | Puerto | Base de Datos | Descripción |
|----------|--------|---------------|-------------|
| API Gateway | 3000 | - | Enrutamiento y autenticación |
| User Service | 3001 | PostgreSQL:5432 | Gestión de usuarios |
| Product Service | 3002 | MongoDB:27017 | Gestión de productos |
| Notification Service | 3003 | Redis:6379 | Notificaciones tiempo real |

## 🔧 Desarrollo Local

### Instalar dependencias en todos los servicios
```bash
./scripts/build-all.sh
```

### Ejecutar servicio individual
```bash
cd microservices/[servicio]
npm install
npm run start:dev
```

### Ver logs
```bash
# Todos los servicios
docker-compose logs -f

# Servicio específico
docker-compose logs -f user-service
```

## 📡 API Endpoints

### Autenticación
```http
POST /api/v1/auth/register    # Registro
POST /api/v1/auth/login       # Login
GET  /api/v1/auth/profile     # Perfil (JWT requerido)
```

### Usuarios
```http
GET    /api/v1/users          # Listar usuarios
GET    /api/v1/users/:id      # Obtener usuario
POST   /api/v1/users          # Crear usuario
PUT    /api/v1/users/:id      # Actualizar usuario
DELETE /api/v1/users/:id      # Eliminar usuario
```

### Productos
```http
GET    /api/v1/products              # Listar productos
GET    /api/v1/products/:id          # Obtener producto
POST   /api/v1/products              # Crear producto
PUT    /api/v1/products/:id          # Actualizar producto
DELETE /api/v1/products/:id          # Eliminar producto
POST   /api/v1/products/:id/reviews  # Agregar reseña
```

### Notificaciones
```http
GET  /api/v1/notifications           # Obtener notificaciones
POST /api/v1/notifications/send      # Enviar notificación
POST /api/v1/notifications/:id/read  # Marcar como leída
GET  /api/v1/notifications/unread-count # Contador no leídas
```

## 🔌 WebSockets (Notificaciones)

### Conexión
```javascript
import io from 'socket.io-client';

const socket = io('http://localhost:3003/notifications', {
  query: { userId: 'user-id-here' }
});
```

### Eventos disponibles
```javascript
// Escuchar nueva notificación
socket.on('new_notification', (notification) => {
  console.log('Nueva notificación:', notification);
});

// Escuchar actualizaciones de contador
socket.on('unread_count', (data) => {
  console.log('Notificaciones no leídas:', data.count);
});

// Marcar como leída
socket.emit('mark_as_read', { notificationId: 'notification-id' });
```

## 🗄️ Bases de Datos

### PostgreSQL (User Service)
```bash
# Conectar
docker exec -it microservices-postgres psql -U postgres -d userdb

# Tablas principales: users
```

### MongoDB (Product Service)  
```bash
# Conectar
docker exec -it microservices-mongo mongosh -u mongo -p mongo123

# Colecciones: products, categories
```

### Redis (Notification Service)
```bash
# Conectar  
docker exec -it microservices-redis redis-cli -a redis123

# Keys: notifications:*, connected_users
```

## 🛠️ Scripts Útiles

```bash
# Iniciar desarrollo
./scripts/start-dev.sh

# Detener servicios
./scripts/stop-dev.sh

# Construir todos los servicios
./scripts/build-all.sh

# Pruebas rápidas del sistema
./scripts/quick-test.sh

# Pruebas completas automatizadas
./scripts/test-all.sh

# Reconstruir imágenes
docker-compose build --no-cache

# Limpiar todo (cuidado: elimina datos)
docker-compose down -v
```

## 🧪 Pruebas y Testing

### Archivos de prueba disponibles:
- **`test-endpoints.md`** - Guía completa con todos los comandos cURL
- **`scripts/quick-test.sh`** - Verificación rápida de servicios
- **`scripts/test-all.sh`** - Suite completa de pruebas automatizadas

### Ejecutar pruebas:
```bash
# Verificación rápida (30 segundos)
./scripts/quick-test.sh

# Suite completa de pruebas (2-3 minutos)
./scripts/test-all.sh

# Ver guía completa de endpoints
cat test-endpoints.md
```

## 📦 Estructura del Proyecto

```
base_microservicios/
├── microservices/
│   ├── api-gateway/          # Gateway principal
│   ├── user-service/         # Servicio de usuarios
│   ├── product-service/      # Servicio de productos
│   └── notification-service/ # Servicio de notificaciones
├── shared/                   # Código compartido
│   ├── common/              # Interfaces y constantes
│   └── config/              # Configuraciones
├── scripts/                 # Scripts de automatización
├── docker-compose.yml       # Orquestación de servicios
├── env.example             # Variables de entorno ejemplo
└── project_summary.json    # Resumen del proyecto
```

## 🔐 Autenticación

El sistema usa **JWT (JSON Web Tokens)** para autenticación:

1. **Registro/Login** → Obtener token JWT
2. **Incluir token** en header: `Authorization: Bearer <token>`
3. **API Gateway** valida y reenvía peticiones

### Ejemplo de uso
```javascript
// Login
const response = await fetch('/api/v1/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ email: 'user@example.com', password: 'password' })
});

const { access_token } = await response.json();

// Usar token en peticiones
const userResponse = await fetch('/api/v1/users/profile', {
  headers: { 'Authorization': `Bearer ${access_token}` }
});
```

## 🚨 Solución de Problemas

### Servicios no inician
```bash
# Verificar Docker
docker --version
docker-compose --version

# Ver logs de error
docker-compose logs [servicio]

# Reiniciar servicios
docker-compose restart
```

### Problemas de conexión a BD
```bash
# Verificar que las BD estén corriendo
docker-compose ps

# Esperar más tiempo para inicialización
docker-compose up -d postgres mongodb redis
sleep 30
docker-compose up -d api-gateway user-service product-service notification-service
```

### Puerto ocupado
```bash
# Ver qué usa el puerto
netstat -tulpn | grep :3000

# Cambiar puerto en docker-compose.yml
```

## 📈 Próximos Pasos

- [X] Tests unitarios y de integración
- [ ] Documentación Swagger/OpenAPI  
- [ ] Circuit breakers y resilience
- [ ] Monitoreo y métricas (Prometheus)
- [ ] Logging centralizado (ELK Stack)
- [ ] CI/CD pipeline
- [ ] Rate limiting
- [ ] Tracing distribuido

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia UNLICENSED - ver el archivo LICENSE para detalles.

---

**¡Listo para usar! 🎉**

Para cualquier duda o problema, revisa los logs con `docker-compose logs -f` o consulta la documentación de cada servicio.