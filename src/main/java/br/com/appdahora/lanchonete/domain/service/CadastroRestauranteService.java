package br.com.appdahora.lanchonete.domain.service;

import br.com.appdahora.lanchonete.domain.exception.EntidadeEmUsoException;
import br.com.appdahora.lanchonete.domain.exception.EntidadeNaoEncontradaException;
import br.com.appdahora.lanchonete.domain.model.Restaurante;
import br.com.appdahora.lanchonete.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante salvar (Restaurante restaurante){
        //regras de negócio
        return restauranteRepository.save(restaurante);
    }

    public void remover(Long empresaId){
        try {
            restauranteRepository.deleteById(empresaId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Restaurante de código %d não pode ser encontrado", empresaId));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Empresa de código %d não pode ser removida, pois está em uso", empresaId));
        }
    }

}
