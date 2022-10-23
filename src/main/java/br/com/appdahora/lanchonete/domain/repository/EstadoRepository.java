package br.com.appdahora.lanchonete.domain.repository;


import br.com.appdahora.lanchonete.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}