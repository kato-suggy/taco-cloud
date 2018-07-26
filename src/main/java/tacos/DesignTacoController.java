// tag::head[]
package tacos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import tacos.Ingredient.Type;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

  private final IngredientRepository ingredientRepo;
  private TacoRepository designRepo;

  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo,
                              TacoRepository designRepo) {
      this.ingredientRepo = ingredientRepo;
      this.designRepo = designRepo;
  }

//end::head[]

//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach((i -> ingredients.add(i)));
    
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), 
          filterByType(ingredients, type));      
    }

    model.addAttribute("design", new Design());
    
    return "design";
  }
  
//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);
    
    return "redirect:/orders/current";
  }
  
//end::processDesign[]
 */
  @ModelAttribute(name = "order")
  public Order order() {
      return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
      return new Taco();
  }
  
//tag::processDesignValidated[]
  @PostMapping
  public String processDesign(@Valid Taco design, Errors errors,
                              @ModelAttribute Order order) {
    if (errors.hasErrors()) {
      return "design";
    }

    Taco saved = designRepo.save(design);
    order.addDesign(saved);
    
    return "redirect:/orders/current";
  }
  
//end::processDesignValidated[]

//tag::filterByType[]
  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }
  
//end::filterByType[]
//tag::foot[]	
}
//end::foot[]