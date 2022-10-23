package br.com.appdahora.lanchonete.domain.repository;

import br.com.appdahora.lanchonete.domain.model.Cidade;
import br.com.appdahora.lanchonete.domain.model.Cozinha;
import br.com.appdahora.lanchonete.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findByNome(String nome);

}
