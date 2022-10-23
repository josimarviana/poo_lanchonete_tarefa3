package br.com.appdahora.lanchonete.api.controller;

import br.com.appdahora.lanchonete.api.model.PedidosXmlWrapper;
import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Pedido;
import br.com.appdahora.lanchonete.domain.repository.PedidoRepository;
import br.com.appdahora.lanchonete.domain.service.CadastroPedidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController //Equivalente a @Controller e @ResponseBody
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroPedidoService cadastroPedidoService;
    
    @GetMapping
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public PedidosXmlWrapper listarXML(){
        return new PedidosXmlWrapper(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long pedidoId){
        Optional<Pedido> pedido =  pedidoRepository.findById(pedidoId);
        if(pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Altera o código de resposta HTTP
    public ResponseEntity<?>  adicionar (@RequestBody Pedido pedido){

        try{
            cadastroPedidoService.salvar(pedido);
            return ResponseEntity.ok(pedido);
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long pedidoId, @RequestBody Pedido pedido){

        try{
            Optional<Pedido>  pedidoAtual =  pedidoRepository.findById(pedidoId);
            if (pedidoAtual.isPresent()) { //verifica se está vazio
                BeanUtils.copyProperties(pedido, pedidoAtual.get(), "id", "itemPedido", "dataCriacao");
                Pedido pedidoSalvo = cadastroPedidoService.salvar(pedidoAtual.get());
                return ResponseEntity.ok(pedidoSalvo);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    //TODO: Inserir atualizar parcial no controller pedido
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Pedido>  remover (@PathVariable Long pedidoId){
        try{
            cadastroPedidoService.remover(pedidoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e){ //tratando violação de chave
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e){ //tratando registro não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-cliente-nome")
    public List<Pedido>  clientesPorNome(@RequestParam("nome") String nome) {

        return  pedidoRepository.consultaporClienteNome(nome);

    }

    @GetMapping("/por-cliente-cpf")
    public List<Pedido>  clientesPorCpf(@RequestParam("cpf") String cpf) {

        return  pedidoRepository.consultaporClienteCpf(cpf);

    }
    @GetMapping("/por-data-criacao")
    public List<Pedido>  pedidosPorDataCriacao(LocalDate dataCriacao) {

        return  pedidoRepository.findByDataCriacao(dataCriacao);

    }
    @GetMapping("/por-data-criacao-entre")
    public List<Pedido>   pedidosPorDataCriacaoEntre(LocalDate dataCriacaoInicial, LocalDate dataCriacaoFinal) {

        return  pedidoRepository.findByDataCriacaoBetween(dataCriacaoInicial, dataCriacaoFinal);

    }

    @GetMapping("/por-data-criacao-entre-e-cliente")
    public List<Pedido>  pedidosPorDataCriacaoEntreECliente(LocalDate dataCriacaoInicial, LocalDate dataCriacaoFinal, Long clienteId) {

        return  pedidoRepository.findByDataCriacaoBetweenAndClienteId(dataCriacaoInicial, dataCriacaoFinal, clienteId);

    }

    @GetMapping("/por-restaurante")
    public List<Pedido>  pedidosPorRestaurante(Long restaurante) {

        return  pedidoRepository.findByRestauranteId(restaurante);

    }
}
