package bo.com.micrium.modulobase.controllers.template;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author alepaco.maton
 * @param <R>
 * @param <T>
 * @param <P>
 */
public interface ICrudMethods<R extends Serializable, T extends Serializable, P> {

    @GetMapping
    ResponseEntity<ApiResponse<Page<T>>> list(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                                             @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                                             @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
                                             @RequestParam Map<String, String> params,
                                             Pageable pageRequest
    );
            //@RequestHeader(value = JWTTokenUtil.ROUTE) String form, @Valid @RequestBody @Size(max = 1000) Pageable pageRequest)throws Exception;

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<T>> get(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                                       @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                                       @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form, @PathVariable P id);

    @PostMapping
    ResponseEntity<ApiResponse<T>> create(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
            @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
            @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
            @Valid @RequestBody R request);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<T>> update(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
            @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
            @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
            @Valid @RequestBody R request,
            // @Min(value = 1, message = "Id invalido, no puede ser menor a 1")
            @PathVariable P id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
            @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
            @RequestHeader(value = JwtTokenUtil.ROUTE) String form,
            @PathVariable P id);

}
