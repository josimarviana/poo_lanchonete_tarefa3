package br.com.appdahora.lanchonete.api.controller;

import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Estado;
import br.com.appdahora.lanchonete.domain.repository.EstadoRepository;
import br.com.appdahora.lanchonete.domain.service.CadastroEstadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,
                                            @RequestBody Estado estado) {
        Estado estadoAtual = estadoRepository.findById(estadoId).orElse(null);

        if (estadoAtual != null) {
            BeanUtils.copyProperties(estado, estadoAtual, "id");

            estadoAtual = cadastroEstado.salvar(estadoAtual);
            return ResponseEntity.ok(estadoAtual);
        }

        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{estadoId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long estadoId, @RequestBody Map<String, Object> campos){
        //Map para maior controle de chave e valor, onde valor é um objeto
        Optional<Estado> estadoAtual =  estadoRepository.findById(estadoId); //recuperando do banco

        if (!estadoAtual.isPresent()){
            return ResponseEntity.notFound().build();
        }

        merge(campos, estadoAtual.get());

        return atualizar(estadoId, estadoAtual.get());

    }

    private static void merge(Map<String, Object> dadosOrigem, Estado estadoDestino) {
        ObjectMapper objectMapper = new ObjectMapper(); // converte json em objetos
        Estado estadoOrigem = objectMapper.convertValue(dadosOrigem, Estado.class); // cria um objeto a partir do json

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            //valorPropriedade não é devidamente convertido para os tipos corretos, dá erro ao atribuir um bigint pq no json lê int
            Field field = ReflectionUtils.findField(Estado.class, nomePropriedade); //recupera a instância dos atributos
            field.setAccessible(true); //permite acesso aos membros da classe, mesmo sendo privados

            Object novoValor = ReflectionUtils.getField(field, estadoOrigem); //recupera o valos dos atributos

            ReflectionUtils.setField(field, estadoDestino, novoValor); //atribui os valores

        });
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstado.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}