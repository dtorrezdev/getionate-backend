# ARQUITECTURA PROPUESTA - FarmaBotBackend

## Arquitectura Actual (Problemática)

```
┌─────────────────────────────────────────────────────┐
│                    REST Client                      │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│                  Controllers                         │
│  (350+ líneas cada uno)                             │
│                                                     │
│  Responsabilidades:                                 │
│  ├─ HTTP handling                                   │
│  ├─ Logging (verbose) ❌ AQUÍ                       │
│  ├─ Validación (manual) ❌ AQUÍ                     │
│  ├─ Negocio ❌ AQUÍ                                 │
│  ├─ Auditoría (manual) ❌ AQUÍ                      │
│  └─ Response building                              │
│                                                     │
│  PROBLEMA: Mezcla de 5+ responsabilidades           │
└────────────┬──────────────────────────┬─────────────┘
             │                          │
     ┌───────▼────────┐        ┌────────▼──────┐
     │  Repository    │        │  Random Utils │
     │  (Data access) │        │  (Helpers)    │
     └────────────────┘        └────────────────┘
```

---

## Arquitectura Propuesta (Limpia)

```
┌─────────────────────────────────────────────────────┐
│                    REST Client                      │
└────────────────────┬────────────────────────────────┘
                     │
         ┌───────────▼─────────────┐
         │   Security Filter       │
         │  (JWT Validation)       │
         └───────────┬─────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│          Global Exception Handler                   │
│  (Centraliza manejo de excepciones)                │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│              Controllers (DELGADOS)                  │
│           <50 líneas cada uno                       │
│                                                     │
│  ✅ Solo HTTP mapping                              │
│  ├─ GET    → service.getAll()                      │
│  ├─ POST   → service.create()                      │
│  ├─ PUT    → service.update()                      │
│  └─ DELETE → service.delete()                      │
│                                                     │
│  VENTAJA: Responsabilidad única                     │
└────────┬─────────────────────────────────┬──────────┘
         │                                 │
    ┌────▼──────────────┐          ┌──────▼────────┐
    │  Services (Lógica)│          │   Mappers     │
    │                  │          │  (DTOs ↔ JPA) │
    │  @Service         │          │                │
    │  @Transactional   │          │  @Mapper       │
    │                  │          │  (MapStruct)   │
    │  ✅ Negocio      │          │                │
    │  ✅ Validación    │          │  ✅ Conversión │
    │  ✅ Transacciones │          │     automática │
    │                  │          │                │
    └────┬─────────────┘          └────────────────┘
         │
    ┌────▼────────────────────────┐
    │   Validators                 │
    │                              │
    │  @Component / @Service       │
    │                              │
    │  ✅ Reglas de validación     │
    │  ✅ Reutilizable            │
    │                              │
    └────┬────────────────────────┘
         │
    ┌────▼────────────────────────┐
    │   AuditAspect (Aspect)      │
    │                              │
    │  @Aspect                     │
    │  @Around("@annotation...")   │
    │                              │
    │  ✅ Logging automático       │
    │  ✅ Auditoría automática     │
    │  ✅ Sin tocar services       │
    │                              │
    └────┬────────────────────────┘
         │
    ┌────▼────────────────────────┐
    │  Repositories               │
    │  (Data Access)              │
    │                              │
    │  ✅ Query ejecución         │
    │                              │
    └────┬────────────────────────┘
         │
    ┌────▼────────────────────────┐
    │    PostgreSQL Database      │
    │                              │
    └─────────────────────────────┘
```

---

## Capas Detalladas

### Capa 1: Controllers (HTTP)
```java
@RestController
@RequestMapping("/api/v1/acciones")
public class AccionController {
    
    private final AccionService service;
    
    @PostMapping
    public ResponseEntity<AccionResponse> create(
            @Valid @RequestBody AccionRequest request) {
        return ResponseEntity.created(...)
            .body(service.create(request));
    }
}
// ✅ Solo 20-30 líneas
```

### Capa 2: Services (Lógica)
```java
@Service
@Transactional
public class AccionService {
    
    private final IAccionRepository repo;
    private final AccionValidator validator;
    private final AccionMapper mapper;
    
    @Auditable(action = "CREAR_ACCION")
    public AccionResponse create(AccionRequest request) {
        validator.validate(request);
        Accion entity = mapper.toEntity(request);
        Accion saved = repo.save(entity);
        // Auditoría y Logging automáticos vía @Auditable
        return mapper.toResponse(saved);
    }
}
// ✅ Lógica clara y testeable
```

### Capa 3: Mappers (Conversión)
```java
@Mapper(componentModel = "spring")
public interface AccionMapper {
    AccionResponse toResponse(Accion accion);
    Accion toEntity(AccionRequest request);
}
// ✅ MapStruct genera la implementación
```

### Capa 4: Validators (Reglas)
```java
@Component
public class AccionValidator {
    
    public void validate(AccionRequest request) {
        if (request.getNombre() == null) {
            throw new ValidationException("Nombre requerido");
        }
        if (request.getNombre().length() > 100) {
            throw new ValidationException("Máximo 100 caracteres");
        }
    }
}
// ✅ Reutilizable, testeable
```

### Capa 5: Aspect (Cross-Cutting Concern)
```java
@Aspect
@Component
public class AuditAspect {
    
    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint jp, Auditable auditable) 
            throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = jp.proceed();
            long duration = System.currentTimeMillis() - start;
            bitacoraService.save(getCurrentUser(), auditable.action(), 
                "SUCCESS", duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            bitacoraService.save(getCurrentUser(), auditable.action(), 
                "ERROR", duration);
            throw e;
        }
    }
}
// ✅ Automático, limpio, reutilizable
```

### Capa 6: Global Exception Handler
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handle(ApiException ex) {
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(ErrorResponse.of(ex));
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ex));
    }
}
// ✅ Consistente, centralizado
```

---

## Flujo de una Petición HTTP

### ANTES (Problemático)
```
GET /api/v1/acciones/1
  ↓
Controller (obtenerIp, validar token, logging)
  ↓
Repository.findById(1)
  ↓
Logging manual (response)
  ↓
Auditoría manual (guardar en bitacora)
  ↓
Excepciones capturadas manualmente
  ↓
Response { }

PROBLEMAS:
❌ Código duplicado en cada controller
❌ Difícil de mantener
❌ Difícil de testear
```

### DESPUÉS (Limpio)
```
GET /api/v1/acciones/1
  ↓
Security Filter (JWT validation) 🔒
  ↓
Controller.get(1) ← ✅ 3 líneas
  ↓
@Around Aspect inicia
  ↓
Service.getById(1) ← ✅ Lógica clara
  ↓
Repository.findById(1)
  ↓
@Around Aspect termina
  ↓
Auditoría automática ✅
  ↓
Response { } (global exception handler si error)

VENTAJAS:
✅ Flujo claro
✅ Responsabilidades claras
✅ Fácil de testear
✅ Fácil de mantener
```

---

## Comparación Lado a Lado

```
┌────────────────────────┬─────────────────────────┐
│  ANTES (Actual)        │  DESPUÉS (Propuesto)    │
├────────────────────────┼─────────────────────────┤
│                        │                         │
│ Controllers: 300 líneas│ Controllers: 50 líneas  │
│ Lógica en controller   │ Lógica en service       │
│ Logging manual         │ Logging automático      │
│ Auditoría manual       │ Auditoría automática    │
│ Validación duplicada   │ Validación única        │
│ Excepciones manuales   │ Excepciones globales    │
│ Difícil testear        │ Fácil testear           │
│ Difícil mantener       │ Fácil mantener          │
│ Sin documentación      │ Swagger automático      │
│ Deuda técnica alta     │ Deuda técnica baja      │
│                        │                         │
└────────────────────────┴─────────────────────────┘
```

---

## Dependencias de Componentes

```
              ┌─────────────────────┐
              │  Controllers        │
              │  (HTTP Layer)       │
              └──────────┬──────────┘
                         │ depends on
              ┌──────────▼──────────┐
              │  Services           │
              │  (Business Logic)   │
              └──────┬─────────┬────┘
                     │         │
          depends on │         │ depends on
                     │         │
           ┌─────────▼──┐ ┌───▼──────┐
           │ Validators │ │ Mappers  │
           │ (Rules)    │ │ (DTO ↔   │
           └────────────┘ │  Entity) │
                          └──────────┘
                               │
                    depends on │
                               │
                        ┌──────▼──────┐
                        │ Repositories│
                        │ (DB Access) │
                        └─────────────┘
                               │
                    depends on │
                               │
                        ┌──────▼──────┐
                        │ PostgreSQL  │
                        │ Database    │
                        └─────────────┘

Cross-Cutting Concerns (Aspect):
├─ AuditAspect    → Intercepta @Auditable
├─ SecurityFilter → Intercepta @Secured
├─ CacheAspect    → Intercepta @Cacheable
└─ GlobalHandler  → Intercepta Excepciones
```

---

## Diagrama de Secuencia: Crear Acción

```
Actor           Controller          Service         Repository      DB
  │                 │                  │               │             │
  ├─POST /acciones──>                  │               │             │
  │                 │                  │               │             │
  │                 │@Valid Validate    │               │             │
  │                 │(Spring)           │               │             │
  │                 │                  │               │             │
  │                 ├──create(request)─>               │             │
  │                 │                  │               │             │
  │                 │             validator            │             │
  │                 │             .validate()          │             │
  │                 │                  │               │             │
  │                 │             @Auditable           │             │
  │                 │             {save start time}    │             │
  │                 │                  │               │             │
  │                 │             mapper.toEntity()    │             │
  │                 │                  │               │             │
  │                 │                  ├─save(entity)─>              │
  │                 │                  │               ├─INSERT──────>
  │                 │                  │               │             │
  │                 │                  │               <─ID────────┤ │
  │                 │                  │<──entity──────┤             │
  │                 │                  │               │             │
  │                 │             @Auditable           │             │
  │                 │             {save to bitacora}   │             │
  │                 │                  │               │             │
  │                 │             mapper.toResponse()  │             │
  │                 │                  │               │             │
  │                 │<──response────────┤               │             │
  │                 │                  │               │             │
  │<─201 Created────┤                  │               │             │
  │{response body}  │                  │               │             │
```

---

## Transición: Plan de Refactorización

```
FASE 1: Preparación
├─ GlobalExceptionHandler creado
├─ DTOs estandarizados
├─ POM.xml limpio
└─ Swagger configurado

FASE 2: Servicios
├─ AccionService creado
├─ Lógica extraída
├─ Tests agregados
└─ Controller simplificado

FASE 3: Aspect
├─ AuditAspect creado
├─ Logging automático
├─ Auditoría automática
└─ Controller aún más simple

FASE 4: Generalización
├─ Todos los servicios creados
├─ Todos los controllers simplificados
├─ Todos con Aspect
└─ Todos con DTOs

FASE 5: Validación
├─ Tests >60%
├─ Performance baseline
├─ Documentación
└─ Production ready
```

---

## Conclusion

La nueva arquitectura es **más limpia, testeable y mantenible**:

✅ Separación clara de responsabilidades  
✅ Controllers delgados  
✅ Servicios enfocados  
✅ Logging/Auditoría automático  
✅ Excepciones centralizadas  
✅ Fácil de testear  
✅ Fácil de mantener  
✅ Mejor para onboarding  
✅ Menos bugs  
✅ Team más productivo  

