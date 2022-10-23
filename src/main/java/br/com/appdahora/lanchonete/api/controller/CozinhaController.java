package br.com.appdahora.lanchonete.api.controller;

import br.com.appdahora.lanchonete.api.model.CozinhasXmlWrapper;
import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Cozinha;
import br.com.appdahora.lanchonete.domain.repository.CozinhaRepository;
import br.com.appdahora.lanchonete.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Equivalente a @Controller e @ResponseBody
@RequestMapping("/cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    
    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXML(){
        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
        Optional<Cozinha> cozinha =  cozinhaRepository.findById(cozinhaId);
        if(cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Altera o código de resposta HTTP
    public Cozinha adicionar (@RequestBody Cozinha cozinha){

        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
        Optional<Cozinha> cozinhaAtual =  cozinhaRepository.findById(cozinhaId);

        if(cozinhaAtual.isPresent()){
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaSalva);
        }
        return ResponseEntity.notFound().build();
    }
    //TODO: Inserir atualizar parcial no controller empresa
    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha>  remover (@PathVariable Long cozinhaId){
        try{
            cadastroCozinhaService.remover(cozinhaId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-nome")
    public List<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
        return cozinhaRepository.findByNome(nome);
    }

}
