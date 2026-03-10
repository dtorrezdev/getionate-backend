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

- Debe haber un proceso de SANATIZACION DE DATOS INGRESADOS A LA BD
  - Los valores seran ingresado en MAYUSCULAS
  - EVITANDO CARACTERES extranios (acentos, latinos, excepto Nie)  




## Frontend

- Modulo Producto
  - Vista add producto Presentacion: tener una seccion de Visualizacion Producto
    - donde se va imprimiendo cada campo
    - Hace una validacion q nombre de producto no se agrege a la presentacion
    - la concentracion/ principio activos
    - No poder crear producto presentacion con factor_conversion igual a la unidad = 1
    - No poder crear un producto Presentacion si no existe otro producto Presentacion donde el campo es_unidad_base = true

  - Vista Producto General:    
    - al crear un nuevo producto general, crea un producto presentacion con su unidad_base_id 


  - Vista Producto Presentacion
    - Cuando se registre una nueva Presentacion en el campo descripcion (principio activo/ concentracion), q tenga la opcion en update el campo descripcion donde seal del mismo producto_id General

  - Vista add producto: en formulardio para agregar (input:mod_prod.producto.nombre) hacer un busqueda de nombres simmilares, para q no ingresen repetidos

  - En la vista add producto: habra una seccion de "Existencia "
    - donde ingresara la cantidad/ fecha de caducidad.

- Modulo Inventario
- 