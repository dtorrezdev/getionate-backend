package bo.com.micrium.modulobase.controllers.template;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.Serializable;

public interface IGetController<T extends Serializable, P> {
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<T>> get(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
           @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
           @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
           @PathVariable P id);
}
