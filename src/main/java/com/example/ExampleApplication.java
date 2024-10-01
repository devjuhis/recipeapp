package com.example;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.domain.*;

@SpringBootApplication
public class ExampleApplication {
	
	//private static final Logger log = LoggerFactory.getLogger(ExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}
 
	@Bean
	public CommandLineRunner RecipeDemo(RecipeRepository repository, AppUserRepository Urepository) {
		return (args) -> {

			/*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			// Hashataan salasanat
			String hashedPassword1 = passwordEncoder.encode("asd");
			String hashedPassword2 = passwordEncoder.encode("asd");

			// Luodaan käyttäjät hashatuilla salasanoilla
			AppUser user1 = new AppUser("user", hashedPassword1, "USER");
			AppUser user2 = new AppUser("admin", hashedPassword2, "ADMIN");

			// Tallennetaan käyttäjät tietokantaan
			Urepository.save(user1);
			Urepository.save(user2);*/

			/*Optional<AppUser> user1Optional = Urepository.findById(1L);
			Optional<AppUser> user2Optional = Urepository.findById(2L);
	
			if (user1Optional.isPresent() && user2Optional.isPresent()) {
				AppUser user1 = user1Optional.get();
				AppUser user2 = user2Optional.get();
	
				Recipe recipe1 = new Recipe(
					"Spaghetti Bolognese",
					"Spaghetti, minced meat, tomato sauce, onions, garlic",
					"1. Cook the spaghetti. 2. Prepare the sauce. 3. Mix them together.",
					null, // Image is optional and not provided here
					user1 // Associate with user 1
				);
	
				Recipe recipe2 = new Recipe(
					"Chicken Curry",
					"Chicken, curry powder, coconut milk, onions, garlic, ginger",
					"1. Fry the onions and garlic. 2. Add chicken and cook until brown. 3. Add curry powder and coconut milk. 4. Simmer until cooked.",
					null,
					user1 // Associate with user 1
				);
	
				Recipe recipe3 = new Recipe(
					"Vegetable Stir Fry",
					"Mixed vegetables, soy sauce, garlic, ginger, sesame oil",
					"1. Heat oil in a pan. 2. Add garlic and ginger. 3. Stir-fry vegetables. 4. Add soy sauce and serve.",
					null,
					user2 // Associate with user 2
				);
	
				repository.save(recipe1);
				repository.save(recipe2);
				repository.save(recipe3);
	
				System.out.println("Dummy recipes saved.");
			} else {
				if (user1Optional.isEmpty()) {
					System.out.println("User with ID 1 not found.");
				}
				if (user2Optional.isEmpty()) {
					System.out.println("User with ID 2 not found.");
				}
			}*/
		};
	}
	
}
