package ideau.controlePatrimonioAPI;

import ideau.controlePatrimonioAPI.repository.implementation.PatrimonioRepositoryImpl;
import ideau.controlePatrimonioAPI.services.implementation.PatrimonioServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class ControlePatrimonioAPIApplication {

	public static void main(String[] args) {
        Dotenv dotenv = new DotenvBuilder().load();
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(ControlePatrimonioAPIApplication.class, args);
	}
}
