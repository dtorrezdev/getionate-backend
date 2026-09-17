package com.dtorrez.main.controllers.template;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.security.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.Serializable;

public interface IUpdateMethod <R extends Serializable, T extends Serializable, P> {

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<T>> update(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                                          @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                                          @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
                                          @Valid @RequestBody R request,
                                          @PathVariable P id);
}
