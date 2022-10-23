package br.com.appdahora.lanchonete.api.controller;

import br.com.appdahora.lanchonete.api.model.ProdutosXmlWrapper;
import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Cliente;
import br.com.appdahora.lanchonete.domain.model.Produto;
import br.com.appdahora.lanchonete.domain.repository.ProdutoRepository;
import br.com.appdahora.lanchonete.domain.service.CadastroProdutoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController //Equivalente a @Controller e @ResponseBody
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    
    @GetMapping
    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ProdutosXmlWrapper listarXML(){
        return new ProdutosXmlWrapper(produtoRepository.findAll());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoId){
        Optional<Produto> produto =  produtoRepository.findById(produtoId);
        if(produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Altera o código de resposta HTTP
    public Produto adicionar (@RequestBody Produto produto){

        return cadastroProdutoService.salvar(produto);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long produtoId, @RequestBody Produto produto){
        Optional<Produto> produtoAtual =  produtoRepository.findById(produtoId);

        if(produtoAtual.isPresent()){
            // produtoAtual.setNome(produto.getNome()); //forma trabalhosa
            BeanUtils.copyProperties(produto, produtoAtual.get(), "id");
            Produto produtoSalvo = cadastroProdutoService.salvar(produtoAtual.get());
            return ResponseEntity.ok(produtoSalvo);
        }
        return ResponseEntity.notFound().build();
    }
    //TODO: Inserir atualizar parcial no controller produto
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Cliente>  remover (@PathVariable Long produtoId){
        try{
            cadastroProdutoService.remover(produtoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-nome")
    public List<Produto> produtosPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    @GetMapping("/por-preco")
    public List<Produto>   produtosPorPreco(BigDecimal precoInicial, BigDecimal precoFinal) {

        return  produtoRepository.findByPrecoBetween(precoInicial, precoFinal);

    }

    @GetMapping("/por-restaurante")
    public List<Produto>  pedidosPorRestaurante(Long restaurante) {

        return  produtoRepository.findByRestauranteId(restaurante);

    }
}
