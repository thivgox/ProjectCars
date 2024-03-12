package br.com.alura.ProjectCars;

import br.com.alura.ProjectCars.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectCarsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjectCarsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
