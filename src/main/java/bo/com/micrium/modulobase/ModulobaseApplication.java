package bo.com.micrium.modulobase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ModulobaseApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ModulobaseApplication.class, args);
	}

}
