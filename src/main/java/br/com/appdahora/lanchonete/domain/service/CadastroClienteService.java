package br.com.appdahora.lanchonete.domain.service;

import br.com.appdahora.lanchonete.domain.exception.CPFInvalidoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Cliente;
import br.com.appdahora.lanchonete.domain.repository.ClienteRepository;
import br.com.appdahora.lanchonete.domain.util.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ValidaCPF validaCPF;

    public Cliente salvar (Cliente cliente){
        //regras de negócio
        if(!validaCPF.isCPF(cliente.getCpf())){
            throw new CPFInvalidoException(
                    String.format("Cliente não tem CPF válido")
            );
        }
        return clienteRepository.save(cliente);
    }

    public void remover(Long clienteId){
        try {
            clienteRepository.deleteById(clienteId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Cliente de código %d não pode ser encontrado", clienteId));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cliente de código %d não pode ser removida, pois está em uso", clienteId));
        }
    }
}
