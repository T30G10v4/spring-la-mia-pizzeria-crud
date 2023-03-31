package me.matteogiovagnotti.springlamiapizzeria.controllers;


import jakarta.validation.Valid;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PizzaRepository;
import me.matteogiovagnotti.springlamiapizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "name") Optional<String> keyword){

        List<Pizza> pizzas;
        if(keyword.isEmpty()) {

           pizzas = pizzaRepository.findAll(Sort.by("name"));

        } else {

            pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
            model.addAttribute("keyword", keyword.get());

        }

        model.addAttribute("list", pizzas);
        return "/pizzas/index";

    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model){

        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {

            model.addAttribute("pizza", result.get());
            return "/pizzas/show";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found.");

        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza,
                           BindingResult bindingResult, Model model) {

        // VALIDATION
        if (bindingResult.hasErrors()) {
            // ritorno alla view con il form
            return "/pizzas/create";
        }
        // se non ci sono errori procedo con la persistenza
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas";
    }
}
