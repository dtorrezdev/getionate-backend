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

- Tengo una consulta ChatGPT, en mi tbl producto, q relacion tiene con la Unidad de Medida de un producto, y puede ser que un producto se registre con una UnidadMedida particular y se venda con otra, x ejemplo Producto: 1, paracentamol, 1G Generico, tabletas,  pero en la venta puedo hacerlo por tableta, o por blíster (10 unidad), caja (50 unidad)


- Cambiar nombre de la tabla Tipo en modulo producto por TipoPresentacion

## Frontend
- Modulo Producto
  - Vista add producto: tener una seccion de Visualizacion Producto
    - donde se va imprimiendo cada campo
    - Hace una validacion q nombre de producto no se agrege a la presentacion
    - la concentracion/ principio activos
  
  - En la vista add producto: habra una seccion de "Existencia "
    - donde ingresara la cantidad/ fecha de caducidad.
  
