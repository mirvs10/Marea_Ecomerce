package com.marea.Proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class CarritoController {


    @GetMapping("/carrito")
    public String carrito() {
        return "carrito"; 
    }
} 