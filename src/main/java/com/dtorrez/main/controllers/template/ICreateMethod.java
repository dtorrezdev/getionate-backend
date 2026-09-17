package com.dtorrez.main.controllers.template;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.security.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public interface ICreateMethod<R extends Serializable, T extends Serializable> {

    @PostMapping
    ResponseEntity<ApiResponse<T>> create(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                                          @RequestHeader(value = JwtTokenUtil.TENANT_ID, required = true) String tenantId,
                                          @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                                          @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
                                          @Valid @RequestBody R request);


}
