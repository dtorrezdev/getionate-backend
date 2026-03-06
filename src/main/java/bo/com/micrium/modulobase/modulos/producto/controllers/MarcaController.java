package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.logger.LoggerMain;
import bo.com.micrium.modulobase.common.exceptions.ApiException;
import bo.com.micrium.modulobase.commons.*;
import bo.com.micrium.modulobase.controllers.template.GenericControler;
import bo.com.micrium.modulobase.controllers.template.ICrudControler;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import bo.com.micrium.modulobase.modulos.producto.validators.MarcaValidator;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
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
@RequestMapping(value = "/marcas", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MarcaController extends GenericControler
    implements ICrudControler<MarcaRequest, MarcaResponse, String> {

    @Autowired
    private IMarcaRepository repository;

    @Autowired
    private MarcaValidator validator;


    @Override
    public Page<MarcaResponse> list(String token, String ipClient, String form, Pageable pageRequest
    ) throws Exception {
        try {
            validator.page(this.httpServletRequest.getParameter("size"), this.httpServletRequest.getParameter("page"),
                    this.httpServletRequest.getParameter("sort"));
            final String nombre = this.httpServletRequest.getParameter("nombre");
            final String descripcion = this.httpServletRequest.getParameter("descripcion");

            if (!isBlanck(nombre) && (nombre.length() > 60)) {
                throw new Exception("La longitud del nombre no debe ser mayor a 60.");
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

            Page<MarcaResponse> out = repository.filter(
                    ((nombre == null || nombre.isEmpty()) ? -1 : 0),
                    ((nombre == null || nombre.trim().isEmpty()) ? ""
                            : "%" + nombre.trim().toUpperCase() + "%"),
                    ((descripcion == null || descripcion.isEmpty()) ? -1 : 0),
                    ((descripcion == null || descripcion.trim().isEmpty()) ? ""
                            : "%" + descripcion.trim().toUpperCase() + "%"),
                    pageRequest).map(model ->  ConvercionUtil.convertToObject(model, MarcaResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out),
                            new AbstractMap.SimpleEntry<>("content ", out.getContent()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;

        } catch (Exception e) {
            final String mensajeError = "Error al filtrar marcas, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA, "Filtrar marcas",
                    mensajeError, e);
            HashMap<String, String> map = new HashMap<>();
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Filtrar marcas", null, map, logSistemaId);
            throw e;
        }
    }

    @Override
    public ResponseEntity<MarcaResponse> get(String token, String ipClient, String form, String id) throws ApiException {
        throw new ApiException("GET not support method /{id}" + id);
    }

    @Override
    public ResponseEntity<MarcaResponse> create(String token, String ipClient, String form,
                                                MarcaRequest request, BindingResult result
    ) throws URISyntaxException, ApiException {
        HashMap<String, String> map = new HashMap<>();
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

            final Marca newMarca = repository.save(
                    Marca.builder()
                         .nombre(request.getNombre())
                         .descripcion(request.getDescripcion())
                         .build()
            );
            map.put("marca", ConvercionUtil.toJson(newMarca));
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Marca", null, map);

            ResponseEntity<MarcaResponse> out = ResponseEntity.created(new URI("/marcas/" + newMarca.getMarcaId()))
                    .body(ConvercionUtil.convertToObject(newMarca, MarcaResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return  out;
        } catch (ApiException | URISyntaxException e) {
            final String mensajeError = "Error al crear un marca. " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "CREAR Marca", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Marca", null, map, logSistemaId);

            throw e;
        }
    }

    @Override
    public ResponseEntity<MarcaResponse> update(String token, String ipClient, String form, MarcaRequest request, String id, BindingResult result) throws ApiException {
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

            Marca updateMarca = repository.findById(idDesencriptado).orElseThrow();
            map.put("Marca", ConvercionUtil.toJson(updateMarca));

            updateMarca = repository.save(
                    Marca.builder()
                            .marcaId(updateMarca.getMarcaId())
                            .nombre(request.getNombre())
                            .descripcion(request.getDescripcion())
                            .build()
            );
            mapNuevo.put("Marca", ConvercionUtil.toJson(updateMarca));
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Marca", map, mapNuevo);

            ResponseEntity<MarcaResponse> out = ResponseEntity.ok()
                    .body(ConvercionUtil.convertToObject(updateMarca, MarcaResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            return out;
            //} catch (EncriptacionExcepcion | ApiException e) {
        } catch (ApiException e) {
            final String mensajeError = "Error al modificar un marca, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Modificar Marca", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Modificar Marca", map, null, logSistemaId);

            throw new ApiException(result, mensajeError, e);
        }
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, String id) throws ApiException {
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
            Optional<Marca> temp = repository.findById(idDesencriptado);

            if (temp.isEmpty()) {
                map.put(TiposComunes.ERROR, "El objeto buscado no se encuentra en la BD");
                throw new NoHandlerFoundException("DELETE", "/{id}" + id, HttpHeaders.EMPTY);
            }

            Marca model = temp.get();
            map.put("Marca", ConvercionUtil.toJson(model));

            repository.delete(model);
            mapNuevo.put("Marca", ConvercionUtil.toJson(model));

            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Marca", map, mapNuevo);
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
            final String mensajeError = "Error al eliminar un marca, " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "Eliminar Marca", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "Eliminar Marca", map, mapNuevo,
                    logSistemaId);

            throw new ApiException(mensajeError, e);
        }
    }
}
