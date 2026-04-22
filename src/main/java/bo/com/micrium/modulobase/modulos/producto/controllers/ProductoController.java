package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.logger.LoggerMain;
import bo.com.micrium.modulobase.common.exceptions.ApiException;
import bo.com.micrium.modulobase.commons.*;
import bo.com.micrium.modulobase.controllers.template.GenericControler;
import bo.com.micrium.modulobase.controllers.template.ICrudControler;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoResponse;
import bo.com.micrium.modulobase.modulos.producto.validators.ProductoValidator;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/productos", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProductoController extends GenericControler
    implements ICrudControler<ProductoRequest, ProductoResponse, String>
{
    @Autowired
    private IProductoRepository repository;

    @Autowired
    private ProductoValidator validator;

    @Override
    public Page<ProductoResponse> list(String token, String ipClient, String form,
                                       Pageable pageRequest
    ) throws Exception {
        try {
            validator.page(this.httpServletRequest.getParameter("size"), this.httpServletRequest.getParameter("page"),
                    this.httpServletRequest.getParameter("sort"));
            final String codigo = this.httpServletRequest.getParameter("codigo");
            final String nombre = this.httpServletRequest.getParameter("nombre");
            final String descripcion = this.httpServletRequest.getParameter("descripcion");

            if (!isBlanck(codigo) && (codigo.length() > 100)) {
                throw new Exception("La longitud del nombre no debe ser mayor a 100.");
            }

            if (!isBlanck(nombre) && (nombre.length() > 100)) {
                throw new Exception("La longitud del nombre no debe ser mayor a 100.");
            }

            if (!isBlanck(descripcion) && (descripcion.length() > 255)) {
                throw new Exception("La longitud de descripcion no debe ser mayor a 255.");
            }

            ipClient = obtenerIp(ipClient);

            LoggerMain.printRequest(Stream.of(
                            new AbstractMap.SimpleEntry<>("url ", httpServletRequest.getRequestURL()),
                            new AbstractMap.SimpleEntry<>("metodo ", httpServletRequest.getMethod()),
                            new AbstractMap.SimpleEntry<>("codigo ", codigo),
                            new AbstractMap.SimpleEntry<>("nombre ", nombre),
                            new AbstractMap.SimpleEntry<>("descripcion ", descripcion),
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("form ", form),
                            new AbstractMap.SimpleEntry<>("pageRequest ", pageRequest))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            Page<ProductoResponse> out = repository.filter(
                    ((codigo == null || codigo.isEmpty()) ? -1 : 0),
                    ((codigo == null || codigo.trim().isEmpty()) ? ""
                            : "%" + codigo.trim().toUpperCase() + "%"),
                    ((nombre == null || nombre.isEmpty()) ? -1 : 0),
                    ((nombre == null || nombre.trim().isEmpty()) ? ""
                            : "%" + nombre.trim().toUpperCase() + "%"),
                    ((descripcion == null || descripcion.isEmpty()) ? -1 : 0),
                    ((descripcion == null || descripcion.trim().isEmpty()) ? ""
                            : "%" + descripcion.trim().toUpperCase() + "%"),
                    pageRequest).map(model ->  ConvercionUtil.convertToObject(model, ProductoResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out),
                            new AbstractMap.SimpleEntry<>("content ", out.getContent()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;

        } catch (Exception e) {
            final String mensajeError = "Error al filtrar producto, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA, "Filtrar producto",
                    mensajeError, e);
            HashMap<String, String> map = new HashMap<>();
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Filtrar producto", null, map, logSistemaId);
            throw e;
        }
    }

    @Override
    public ResponseEntity<ProductoResponse> get(String token, String ipClient, String form,
                                                String id) throws ApiException {
        throw new ApiException("GET not support method /{id}" + id);
    }

    @Override
    public ResponseEntity<ProductoResponse> create(String token, String ipClient, String form,
                                                   ProductoRequest request, BindingResult result
    ) throws URISyntaxException, ApiException {
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            ipClient = obtenerIp(ipClient);
            LoggerMain.printRequest(Stream.of(
                            new AbstractMap.SimpleEntry<>("url ", httpServletRequest.getRequestURL()),
                            new AbstractMap.SimpleEntry<>("metodo ", httpServletRequest.getMethod()),
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("form ", form),
                            new AbstractMap.SimpleEntry<>("request ", request))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            validator.validate(request, null, result);
            if (result.hasErrors()) {
                map.put(TiposComunes.ERROR, obtenerErrores(result));
                throw new ApiException(result, "Errores en la validacion");
            }

            final Producto newProducto = repository.save(
                    Producto.builder()
                            .codigo(request.getCodigo())
                            .nombre(request.getNombre())
                            .descripcion(request.getDescripcion())
                            .categoriaId(request.getCategoriaId())
                            .build()
            );

            map.put("Producto", ConvercionUtil.toJson(newProducto));
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Producto", null, map);

            ResponseEntity<ProductoResponse> out = ResponseEntity.created(new URI("/productos/" + newProducto.getId()))
                    .body(ConvercionUtil.convertToObject(newProducto, ProductoResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return  out;
        } catch (ApiException | URISyntaxException e) {
            final String mensajeError = "Error al crear un producto. " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "CREAR Producto", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Producto", null, map, logSistemaId);

            throw e;
        }
    }

    @Override
    public ResponseEntity<ProductoResponse> update(String token, String ipClient, String form,
                                                   ProductoRequest request, String id, BindingResult result
    ) throws ApiException {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> mapNuevo = new HashMap<>();
        try {
            ipClient = obtenerIp(ipClient);
            LoggerMain.printRequest(Stream.of(
                            new AbstractMap.SimpleEntry<>("url ", httpServletRequest.getRequestURL()),
                            new AbstractMap.SimpleEntry<>("metodo ", httpServletRequest.getMethod()),
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("form ", form),
                            new AbstractMap.SimpleEntry<>("id ", id),
                            new AbstractMap.SimpleEntry<>("request ", request))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            //id = limpiarCaracterEspecialEncriptacion(id);

            Long idDesencriptado = Long.valueOf(id); //ConfigEncriptacion.desencryptIdToConvertLong(id);
            validator.validate(request, idDesencriptado, result);

            if (result.hasErrors()) {
                map.put(TiposComunes.ERROR, obtenerErrores(result));
                throw new ApiException(result, "Errores en la validacion");
            }

            Producto updatedProducto = repository.findById(idDesencriptado).orElseThrow();
            map.put("Producto", ConvercionUtil.toJson(updatedProducto));


            updatedProducto = repository.save(
                    Producto.builder()
                            .id(updatedProducto.getId())
                            .codigo(request.getCodigo())
                            .nombre(request.getNombre())
                            .descripcion(request.getDescripcion())
                            .categoriaId(request.getCategoriaId())
                            .build()
            );
            mapNuevo.put("Producto", ConvercionUtil.toJson(updatedProducto));
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Producto", map, mapNuevo);

            ResponseEntity<ProductoResponse> out = ResponseEntity.ok()
                    .body(ConvercionUtil.convertToObject(updatedProducto, ProductoResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;
        //} catch (EncriptacionExcepcion | ApiException e) {
        } catch (ApiException e) {
            final String mensajeError = "Error al modificar un producto, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Modificar Producto", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Producto", map, null, logSistemaId);

            throw new ApiException(result, mensajeError, e);
        }
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, String id
    ) throws ApiException {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> mapNuevo = new HashMap<>();

        try {
            ipClient = obtenerIp(ipClient);
            LoggerMain.printRequest(Stream.of(
                            new AbstractMap.SimpleEntry<>("url ", httpServletRequest.getRequestURL()),
                            new AbstractMap.SimpleEntry<>("metodo ", httpServletRequest.getMethod()),
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("form ", form),
                            new AbstractMap.SimpleEntry<>("id ", id))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            //id = limpiarCaracterEspecialEncriptacion(id);
            Long idDesencriptado = Long.valueOf(id); // ConfigEncriptacion.desencryptIdToConvertLong(id);
            Optional<Producto> temp = repository.findById(idDesencriptado);

            if (temp.isEmpty()) {
                map.put(TiposComunes.ERROR, "El objeto buscado no se encuentra en la BD");
                throw new NoHandlerFoundException("DELETE", "/{id}" + id, HttpHeaders.EMPTY);
            }

            Producto model = temp.get();
            model.setEsActivo(Boolean.FALSE);
            map.put("producto", ConvercionUtil.toJson(model));

            repository.save(model);
            mapNuevo.put("Producto", ConvercionUtil.toJson(model));

            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Producto", map, mapNuevo);
            ResponseEntity<Object> out = ResponseEntity.ok().build();

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;
        //} catch (EncriptacionExcepcion | RuntimeException | NoHandlerFoundException e) {
        } catch (RuntimeException | NoHandlerFoundException e) {
            final String mensajeError = "Error al eliminar un producto, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Eliminar Producto", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Producto", map, mapNuevo,
                    logSistemaId);

            throw new ApiException(mensajeError, e);
        }
    }
}
