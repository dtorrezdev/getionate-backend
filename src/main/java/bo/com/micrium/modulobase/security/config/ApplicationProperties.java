package bo.com.micrium.modulobase.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

/**
 *
 * @author micrium
 */
@Configuration
@Getter
public class ApplicationProperties {

    @Value("${bucket4j.limit}")
    private int limit;

    @Value("${bucket4j.duration.minutes}")
    private int durationMinutes;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public static final Long LIMIT = 50L;

    public static final Long DURATION = 1L; // en minutos
}
