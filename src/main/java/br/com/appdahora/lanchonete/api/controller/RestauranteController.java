package br.com.appdahora.lanchonete.api.controller;

import br.com.appdahora.lanchonete.api.model.RestaurantesXmlWrapper;
import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Restaurante;
import br.com.appdahora.lanchonete.domain.repository.RestauranteRepository;
import br.com.appdahora.lanchonete.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Equivalente a @Controller e @ResponseBody
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    
    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public RestaurantesXmlWrapper listarXML(){
        return new RestaurantesXmlWrapper(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
        Optional<Restaurante> restaurante =  restauranteRepository.findById(restauranteId);
        if(restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Altera o código de resposta HTTP
    public Restaurante adicionar (@RequestBody Restaurante restaurante){

        return cadastroRestauranteService.salvar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
        Optional<Restaurante> restauranteAtual =  restauranteRepository.findById(restauranteId);

        if(restauranteAtual.isPresent()){
            BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
            Restaurante restauranteSalva = cadastroRestauranteService.salvar(restauranteAtual.get());
            return ResponseEntity.ok(restauranteSalva);
        }
        return ResponseEntity.notFound().build();
    }
    //TODO: Inserir atualizar parcial no controller empresa
    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante>  remover (@PathVariable Long restauranteId){
        try{
            cadastroRestauranteService.remover(restauranteId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-nome")
    public List<Restaurante> empresaPorNome(@RequestParam("nome") String nome) {
        return restauranteRepository.findByNomeContaining(nome);
    }

}
