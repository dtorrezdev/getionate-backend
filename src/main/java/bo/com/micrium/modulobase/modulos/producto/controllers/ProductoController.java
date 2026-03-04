package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.logger.LoggerMain;
import bo.com.micrium.modulobase.common.exceptions.ApiException;
import bo.com.micrium.modulobase.commons.Acciones;
import bo.com.micrium.modulobase.commons.Apps;
import bo.com.micrium.modulobase.commons.ConvercionUtil;
import bo.com.micrium.modulobase.commons.TiposComunes;
import bo.com.micrium.modulobase.controllers.dto.GrupoResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
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
    public Page<ProductoResponse> list(String token, String ipClient, String form, Pageable pageRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<ProductoResponse> get(String token, String ipClient, String form, String id) throws ApiException {
        return null;
    }

    @Override
    public ResponseEntity<ProductoResponse> create(String token, String ipClient, String form,
                                                   ProductoRequest request, BindingResult result
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

            final Producto newProduct = new Producto(
                    null, request.getCodigo(),
                    request.getNombre(),
                    request.getDescripcion()
            );

            final Producto producto = repository.save(newProduct);

            map.put("Producto", ConvercionUtil.toJson(producto));
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Producto", null, map);

            ResponseEntity<ProductoResponse> out = ResponseEntity.created(new URI("/productos/" + producto.getProductoId()))
                    .body(ConvercionUtil.convertToObject(newProduct, ProductoResponse.class));

            LoggerMain.printResponse(Stream.of(
                            new AbstractMap.SimpleEntry<>("token ", token),
                            new AbstractMap.SimpleEntry<>("trazabilidad ", obtenerNombreUsuario()),
                            new AbstractMap.SimpleEntry<>("ipClient ", ipClient),
                            new AbstractMap.SimpleEntry<>("response ", out))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return  out;
        } catch (ApiException | URISyntaxException e) {
            final String mensajeError = "Error al crear un grupo. " + e.getMessage();

            final Long logSistemaId = logWeb.error(obtenerNombreUsuario(), Apps.TRAZABILIDAD_SISTEMA,
                    "CREAR Producto", mensajeError, e);
            map.put(TiposComunes.MENSAJE_ERROR, mensajeError);
            bitacoraService.guardarBitacora(token, ipClient, form, "CREAR Producto", null, map, logSistemaId);

            throw e;
        }
    }

    @Override
    public ResponseEntity<ProductoResponse> update(String token, String ipClient, String form, ProductoRequest request, String id, BindingResult result) throws ApiException {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, String id) throws ApiException {
        return null;
    }
}
