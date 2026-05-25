package bo.com.micrium.modulobase.security.utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;

import bo.com.micrium.modulobase.security.controllers.dto.UserContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.micrium.bd.access.enuns.Parametro;

import bo.com.micrium.modulobase.services.ParametroService;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 *
 * @author alepaco.maton
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final Logger log = LogManager.getLogger(JwtTokenUtil.class);

    public static final String KEY_TOKEN = "Authorization";
    public static final String IP_CLIENT = "Forwarded-For";//  Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43
    public static final String FORM = "Referer";// Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43
    public static final String ROUTE = "Route";
    public static final String TENANT_ID = "Tenant-Id";

    private static final long serialVersionUID = -2550185165626007488L;

    @Autowired
    transient ParametroService parametroService; // 33646 coverity

    /*
     * @Value("${jwt.timelife}")
     * public String JWT_TOKEN_VALIDIT;
     */

    // expresado en horas
    // public Long TiempoValidacionToken=1l;

    @Value("${jwt.secret}")
    private String secret;

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        if (token == null || token.isEmpty()) {
            return token;
        }

        return getClaimFromToken(token.substring(7), Claims::getSubject);
    }

    public String getRolNombreFromToken(String token) {
        if (token == null || token.isEmpty()) {
            return token;
        }

        return (String) getAllClaimsFromToken(token.substring(7)).get("rol");
    }

    public Integer getTenantIdFromToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        return (Integer) getAllClaimsFromToken(token.substring(7)).get("tenantId");
    }

    public Integer getUsuarioIdFromToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        return (Integer) getAllClaimsFromToken(token.substring(7)).get("usuarioId");
    }

    public UserContext getUserContextFromToken(String token) {
        Long usuarioId = Long.valueOf(this.getUsuarioIdFromToken(token));
        String userName = this.getUsernameFromToken(token);
        Long tenantId = Long.valueOf(this.getTenantIdFromToken(token));
        String rolName = this.getRolNombreFromToken(token);

        return new UserContext(usuarioId, userName, rolName, tenantId);
    }

    @SuppressWarnings("unchecked")
    public List<String> getPermisosFromToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        return (List<String>) getAllClaimsFromToken(token.substring(7)).get("permisos");
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        //log.info("getClaimFromToken ");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    
    // check if the token has expired
    public Boolean isTokenExpired(String token) {
        //final Date expiration = getExpirationDateFromToken(token.substring(7));      
        token = token.replace("Bearer ","");
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    // generate token for user
    public String generateToken(String nombreUsuario, String rolName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rolName);
        return doGenerateToken(claims, nombreUsuario);
    }

    public String generateToken(UserContext user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", user.getRolName());
        claims.put("usuarioId", user.getUserId());
        claims.put("tenantId", user.getTenantId());
        return doGenerateToken(claims, user.getUserName());
    }

    public String generateToken(UserContext user, List<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", user.getRolName());
        claims.put("tenantId", user.getTenantId());
        claims.put("usuarioId", user.getUserId());
        claims.put("permisos", permissions);
        return doGenerateToken(claims, user.getUserName());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    // para que token expire en minutos seria 30min/60 *1000 *60*60
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long tokenTime = Long.parseLong(parametroService.getParametroByNombre(Parametro.DelSistema.TOKEN_TIME.name()).getValor());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
//                .setIssuer("micrium.com.bo")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (((tokenTime) * 1000) * 60)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) // Firma el token con la clave y el algoritmo HS256
                .compact();
    }
}
