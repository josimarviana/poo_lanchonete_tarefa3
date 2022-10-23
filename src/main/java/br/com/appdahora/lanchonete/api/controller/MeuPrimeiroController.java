package br.com.appdahora.lanchonete.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //Controler é uma classe que vai receber uma requisição web
public class MeuPrimeiroController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
         return "Hello";
    }
}
