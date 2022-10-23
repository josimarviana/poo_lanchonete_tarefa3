package br.com.appdahora.lanchonete.domain.repository;

import br.com.appdahora.lanchonete.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto p join p.restaurante") //resolvendo problema n+1 com fetch join na JPQL
    List<Produto> findAll();
    List<Produto> findByNome(String nome);
    List<Produto> findByPrecoBetween(BigDecimal valorInicial, BigDecimal valorFinal);

    List<Produto> findByRestauranteId(Long restauranteId);

}
