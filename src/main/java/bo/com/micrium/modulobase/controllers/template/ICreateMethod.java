package bo.com.micrium.modulobase.controllers.template;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public interface ICreateMethod<R extends Serializable, T extends Serializable> {

    @PostMapping
    ResponseEntity<ApiResponse<T>> create(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                                          @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                                          @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
                                          @Valid @RequestBody R request);


}
