package bookauthor.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ChenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChenApplication.class, args);
	}
	@Autowired
	JdbcTemplate jdbcTemplate;
}

