package br.com.appdahora.lanchonete.domain.service;

import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Cliente;
import br.com.appdahora.lanchonete.domain.model.Pedido;
import br.com.appdahora.lanchonete.domain.repository.ClienteRepository;
import br.com.appdahora.lanchonete.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pedido salvar (Pedido pedido){

        Long clienteId = pedido.getCliente().getId();
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if (cliente.isEmpty()) {  //verifica se tem algum cliente dentro do optional
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de cliente com o código %d", clienteId)
            );
        }
        pedido.setCliente(cliente.get());
        return pedidoRepository.save(pedido);

    }

    public void remover(Long pedidoId){
        try {
            pedidoRepository.deleteById(pedidoId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Produto de código %d não pode ser encontrado", pedidoId));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Produto de código %d não pode ser removida, pois está em uso", pedidoId));
        }
    }

}
