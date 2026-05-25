package bo.com.micrium.modulobase.controllers.template;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.Serializable;

public interface IListMethod<R extends Serializable, T extends Serializable> {

    @GetMapping
    ResponseEntity<ApiResponse<Page<T>>> list(
            @RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
            @RequestHeader(value = JwtTokenUtil.TENANT_ID, required = true) String tenantId,
            @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
            @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
            @ModelAttribute R request,
            Pageable pageRequest
    );
}
