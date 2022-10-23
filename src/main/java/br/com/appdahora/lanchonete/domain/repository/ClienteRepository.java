package br.com.appdahora.lanchonete.domain.repository;

import br.com.appdahora.lanchonete.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContaining(String nome);

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Optional<Cliente> findFirstByNomeContaining(String nome);
    List<Cliente> findTop2ByNomeContaining(String nome);
    int countByNomeContaining(String nome);
    boolean existsByNomeContaining(String nome);

    Cliente findByCpf(String cpf);
    Cliente findByTelefone(String telefone);
    Cliente findByEmail(String email);
    List<Cliente> findByDataNascimento(LocalDate dataNascimento);
}
