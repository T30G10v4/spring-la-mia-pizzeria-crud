package me.matteogiovagnotti.springlamiapizzeria.repositories;

import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository <Pizza, Integer> {
}
