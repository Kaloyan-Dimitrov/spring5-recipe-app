package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        // get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        // get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        // get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        // Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setServings(4);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                " Place in a bowl."
                + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"
                + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the" +
                " avocado and will help delay the avocados from turning brown."
                + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the" +
                " guacamole to your desired degree of hotness."
                + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste."
                + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes" +
                " oxidation which will turn the guacamole brown.) Refrigerate until ready to serve."
                + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient("ripe avocados", 2.0, eachUom);
        guacRecipe.addIngredient("Kosher salt", 0.5, teaSpoonUom);
        guacRecipe.addIngredient("fresh lime juice or lemon juice", 2.0, tableSpoonUom);
        guacRecipe.addIngredient("minced red onion or thinly sliced green onion", 2.0, tableSpoonUom);
        guacRecipe.addIngredient("serrano chiles, stems and seeds removed, minced", 2.0, eachUom);
        guacRecipe.addIngredient("Cilantro", 2.0, tableSpoonUom);
        guacRecipe.addIngredient("freshly grated black pepper", 2.0, dashUom);
        guacRecipe.addIngredient("ripe tomato, seeds and pulp removed, chopped", 0.5, eachUom);

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        // add to return list
        recipes.add(guacRecipe);

        // Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setServings(6);
        tacosRecipe.setSource("Simply Recipes");
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat."
                + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the" +
                " orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over."
                + "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
                + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a" +
                " plate and rest for 5 minutes."
                + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the" +
                " tortilla, turn it with tongs and heat for a few seconds on the other side."
                + "Wrap warmed tortillas in a tea towel to keep them warm until serving."
                + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes," +
                " and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and all things can be put inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of" +
                " tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this" +
                " time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient("Ancho Chili Powder", 2.0, tableSpoonUom);
        tacosRecipe.addIngredient("Dried Oregano", 1.0, teaSpoonUom);
        tacosRecipe.addIngredient("Dried Cumin", 1.0, teaSpoonUom);
        tacosRecipe.addIngredient("Sugar", 1.0, teaSpoonUom);
        tacosRecipe.addIngredient("Salt", 0.5, teaSpoonUom);
        tacosRecipe.addIngredient("Clove of Garlic, Choppedr", 1.0, eachUom);
        tacosRecipe.addIngredient("finely grated orange zestr", 1.0, tableSpoonUom);
        tacosRecipe.addIngredient("fresh-squeezed orange juice", 3.0, tableSpoonUom);
        tacosRecipe.addIngredient("Olive Oil", 2.0, tableSpoonUom);
        tacosRecipe.addIngredient("boneless chicken thighs", 4.0, tableSpoonUom);
        tacosRecipe.addIngredient("small corn tortillasr", 8.0, eachUom);
        tacosRecipe.addIngredient("packed baby arugula", 3.0, cupsUom);
        tacosRecipe.addIngredient("medium ripe avocados, slic", 2.0, eachUom);
        tacosRecipe.addIngredient("radishes, thinly sliced", 4.0, eachUom);
        tacosRecipe.addIngredient("cherry tomatoes, halved", 0.5, pintUom);
        tacosRecipe.addIngredient("red onion, thinly sliced", 0.25, eachUom);
        tacosRecipe.addIngredient("Roughly chopped cilantro", 4.0, eachUom);
        tacosRecipe.addIngredient("cup sour cream thinned with 1/4 cup milk", 4.0, cupsUom);
        tacosRecipe.addIngredient("lime, cut into wedges", 4.0, eachUom);

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);

        return recipes;
    }
}
