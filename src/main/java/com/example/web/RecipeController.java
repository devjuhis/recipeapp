package com.example.web;

import com.example.domain.Recipe;
import com.example.domain.RecipeRepository;
import com.example.domain.AppUser;
import com.example.domain.Meal;
import com.example.service.FoodApiService;
import com.example.service.MealApiService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private FoodApiService foodApiService;

    @Autowired
    private MealApiService recipeService;

    @GetMapping("/recipes")
    public String getRecipes(@RequestParam String query, 
        Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {

        java.util.List<Meal> apiRecipes = recipeService.getRecipeByName(query);
        if (currentUser != null) {
            Long userId = currentUser.getUserId();
            logger.info("userid: " + userId);
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("recipes", repository.findByUserId(userId));
            model.addAttribute("apirecipes", apiRecipes);
        }

        return "index";
    }

    //ei enään käytössä vanha api
    @GetMapping("/search")
    public String searchRecipes(@RequestParam("query") String query, Model model) {
        
        Recipe[] apiRecipes = foodApiService.fetchRecipes(query);
        
        model.addAttribute("apirecipes", apiRecipes);
        
        model.addAttribute("recipes", repository.findAll());
        
        return "index"; 
    }

    @PostMapping("/save")
    public String saveRecipe(@AuthenticationPrincipal CustomUserDetails currentUser, 
                            @ModelAttribute Recipe recipe) {

        if (currentUser != null) {
            Long userId = currentUser.getUserId();
            logger.info("Current user ID: " + userId);

            AppUser user = new AppUser();
            user.setId(userId);

            recipe.setUser(user);
            recipe.setTitle(recipe.getTitle());
            recipe.setIngredients(recipe.getIngredients());
            recipe.setInstructions(recipe.getInstructions());
            recipe.setImage(recipe.getImage());

            repository.save(recipe);
            logger.info("New recipe saved successfully.");
        } else {
            logger.warn("No authenticated user found. Recipe not saved.");
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteRecipe(@PathVariable("id") Long id, 
        Model model,
        @AuthenticationPrincipal CustomUserDetails currentUser) {

        if (currentUser != null) {
            Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id: " + id));

            if (recipe.getUser().getId().equals(currentUser.getUserId())) {
                repository.deleteById(id);
                return "redirect:../";
            } else {
                logger.warn("User {} attempted to delete recipe with id {} without permission.", 
                            currentUser.getUsername(), id);
                return "redirect:/access-denied";
            }
        } else {
            logger.warn("Attempt to delete recipe without authentication.");
            return "redirect:/login";
        }
    }


    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }
    

    @GetMapping("/edit/{id}")
    public String editRecipe(@PathVariable("id") long id, 
            Model model, 
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        if (currentUser != null) {
            Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id: " + id));

            if (recipe.getUser().getId().equals(currentUser.getUserId())) {
                model.addAttribute("recipe", recipe);
                return "edit";
            } else {
                logger.warn("User {} attempted to edit recipe with id {} without permission.", 
                            currentUser.getUsername(), id);
                return "redirect:/access-denied";
            }
        } else {
            logger.warn("Attempt to access edit page without authentication.");
            return "redirect:/login";
        }
    }

    
    @RequestMapping(value = {"/", "/index"})
    public String books(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        if (currentUser != null) {
            Long userId = currentUser.getUserId();
            logger.info("userid: " + userId);
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("recipes", repository.findByUserId(userId));
        }
        return "index";
    }
}