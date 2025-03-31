package app.healthy.bot.controller;


import app.healthy.bot.model.Habito;
import app.healthy.bot.service.HabitoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @GetMapping
    public List<Habito> obtenerTodos() {
        return habitoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Habito> obtenerPorId(@PathVariable Long id) {
        return habitoService.obtenerPorId(id);
    }

    @PostMapping
    public Habito guardar(@RequestBody Habito habito) {
        return habitoService.guardar(habito);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        habitoService.eliminar(id);
    }

}
