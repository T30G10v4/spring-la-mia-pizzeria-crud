package me.matteogiovagnotti.springlamiapizzeria.services;

import me.matteogiovagnotti.springlamiapizzeria.exceptions.PizzaNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formPizza){

        Pizza pizzaToPersist = new Pizza();

        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(formPizza.getPrice());

        return pizzaRepository.save(pizzaToPersist);

    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            return false;

        }
    }

}
