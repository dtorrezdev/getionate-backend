# List of content

## Objetivos

- tener codigo mas mantenible

## Arquitectura Actual

## Refactor

- Validaciones
  - Validacion a nivel Java Jakarte.validate
  - Validacion a nivel org.springframework.validation

## Pensamientos

- El controlador hereda GenericController e implementa ICRUDController varias clases, e indirectamente SubHerada la clase GlobalValidator.
- El controlador esta baste sucio
  - hace registro de trazabilidad en Log
  - hace validacines con su clase Validator (extend de GlobalValidator) respectivo
  - Hace registro en 2 tablas bitacoras y Log Sistema

- Controlar las excepciones (no capturas las validaciones)
  - cuando token expira no muestra mensaje 'error: Token expirado'

