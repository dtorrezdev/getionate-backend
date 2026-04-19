package bo.com.micrium.modulobase.controllers.template;

import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public interface IDeleteMethod<R extends Serializable> {

    @DeleteMapping
    ResponseEntity<?> delete(@RequestHeader(value = JwtTokenUtil.KEY_TOKEN) String token,
                       @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
                       @RequestHeader(value = JwtTokenUtil.ROUTE, required = false) String form,
                       @Valid @RequestBody R request);

}
