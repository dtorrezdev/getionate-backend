package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.logger.LoggerMain;
import bo.com.micrium.modulobase.common.exceptions.ApiException;
import bo.com.micrium.modulobase.commons.Apps;
import bo.com.micrium.modulobase.commons.ConvercionUtil;
import bo.com.micrium.modulobase.commons.TiposComunes;
import bo.com.micrium.modulobase.controllers.template.GenericControler;
import bo.com.micrium.modulobase.controllers.template.ICrudControler;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.presentacion.PresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.presentacion.PresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoResponse;
import bo.com.micrium.modulobase.modulos.producto.validators.PresentacionValidator;
import bo.com.micrium.modulobase.modulos.producto.validators.ProductoValidator;
import com.micrium.bd.access.jpa.modulo.productos.models.Presentacion;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.repository.IPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PresentacionController extends GenericControler
    implements ICrudControler<PresentacionRequest, PresentacionResponse, String> {

    @Autowired
    private IPresentacionRepository repository;

    @Autowired
    private PresentacionValidator validator;

    @Override
    public Page<PresentacionResponse> list(String token, String ipClient, String form,
                                       Pageable pageRequest
    ) throws Exception {
        try {
            validator.page(this.httpServletRequest.getParameter("size"), this.httpServletRequest.getParameter("page"),
                    this.httpServletRequest.getParameter("sort"));
            final String nombre = this.httpServletRequest.getParameter("nombre");
            final String descripcion = this.httpServletRequest.getParameter("descripcion");

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
                            new AbstractMap.SimpleEntry<>("nombre ", nombre),
                            new AbstractMap.SimpleEntry<>("descripcion ", descripcion),
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("form ", form),
                            new AbstractMap.SimpleEntry<>("pageRequest ", pageRequest))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            Page<PresentacionResponse> out = repository.filter(
                    ((nombre == null || nombre.isEmpty()) ? -1 : 0),
                    ((nombre == null || nombre.trim().isEmpty()) ? ""
                            : "%" + nombre.trim().toUpperCase() + "%"),
                    ((descripcion == null || descripcion.isEmpty()) ? -1 : 0),
                    ((descripcion == null || descripcion.trim().isEmpty()) ? ""
                            : "%" + descripcion.trim().toUpperCase() + "%"),
                    pageRequest).map(model ->  ConvercionUtil.convertToObject(model, PresentacionResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out),
                            new AbstractMap.SimpleEntry<>("content ", out.getContent()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;

        } catch (Exception e) {
            final String mensajeError = "Error al filtrar presentacion, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA, "Filtrar presentacion",
                    mensajeError, e);
            HashMap<String, String> map = new HashMap<>();
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Filtrar presentacion", null, map, logSistemaId);
            throw e;
        }
    }

    @Override
    public ResponseEntity<PresentacionResponse> get(String token, String ipClient, String form,
                                                String id) throws ApiException {
        throw new ApiException("GET not support method /{id}" + id);
    }

    @Override
    public ResponseEntity<PresentacionResponse> create(String token, String ipClient, String form,
                                                   PresentacionRequest request, BindingResult result
    ) throws URISyntaxException, ApiException {
        HashMap<String, String> map = new HashMap<String, String>();
        LoggerMain.info("Llego aqui controlador");
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

            final Presentacion newPresentacion = repository.save(
                    Presentacion.builder()
                        .nombre(request.getNombre())
                        .descripcion(request.getDescripcion())
                        .unidadMedidaId(request.getUnidadMedidaId())
                        .tipoId(request.getTipoId())
                        .build()
            );

            map.put("presentacion", ConvercionUtil.toJson(newPresentacion));
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Presentacion", null, map);

            ResponseEntity<PresentacionResponse> out = ResponseEntity
                    .created(new URI("/presentaciones/" + newPresentacion.getId()))
                    .body(ConvercionUtil.convertToObject(newPresentacion, PresentacionResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return  out;
        } catch (ApiException | URISyntaxException e) {
            final String mensajeError = "Error al crear un presentacion. " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "CREAR Presentacion", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Presentacion", null, map, logSistemaId);

            throw e;
        }
    }

    @Override
    public ResponseEntity<PresentacionResponse> update(String token, String ipClient, String form,
                                           PresentacionRequest request, String id, BindingResult result
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

            Presentacion updatedPresentacion = repository.findById(idDesencriptado).orElseThrow();
            map.put("presentacion", ConvercionUtil.toJson(updatedPresentacion));

            updatedPresentacion = repository.save(
                    Presentacion.builder()
                            .nombre(request.getNombre())
                            .descripcion(request.getDescripcion())
                            .unidadMedidaId(request.getUnidadMedidaId())
                            .tipoId(request.getTipoId())
                            .build()
            );
            mapNuevo.put("presentacion", ConvercionUtil.toJson(updatedPresentacion));
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Presentacion", map, mapNuevo);

            ResponseEntity<PresentacionResponse> out = ResponseEntity.ok()
                    .body(ConvercionUtil.convertToObject(updatedPresentacion, PresentacionResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;
            //} catch (EncriptacionExcepcion | ApiException e) {
        } catch (ApiException e) {
            final String mensajeError = "Error al modificar un presentacion, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Modificar Presentacion", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Presentacion", map, null, logSistemaId);

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
            Optional<Presentacion> temp = repository.findById(idDesencriptado);

            if (temp.isEmpty()) {
                map.put(TiposComunes.ERROR, "El objeto buscado no se encuentra en la BD");
                throw new NoHandlerFoundException("DELETE", "/{id}" + id, HttpHeaders.EMPTY);
            }

            Presentacion model = temp.get();
            map.put("presentacion", ConvercionUtil.toJson(model));

            //model.setEstado(GrupoEstado.INHABILITADO);
            repository.delete(model);
            mapNuevo.put("presentacion", ConvercionUtil.toJson(model));

            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Presentacion", map, mapNuevo);
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
            final String mensajeError = "Error al eliminar un presentacion, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Eliminar Presentacion", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Presentacion", map, mapNuevo,
                    logSistemaId);

            throw new ApiException(mensajeError, e);
        }
    }
}
