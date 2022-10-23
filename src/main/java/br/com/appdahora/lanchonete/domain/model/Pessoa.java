package br.com.appdahora.lanchonete.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class Pessoa {
    @Column(length = 50, nullable = false)
    protected String nome;
    @EqualsAndHashCode.Include
    @Column(length = 11)
    protected String cpf;
    @Column(length = 13)
    @JsonProperty("fone")  //customizando na representação
    protected String telefone;
    @Column(length = 40)
    protected String email;
    protected LocalDate dataNascimento;
    @JsonIgnore //inibi na representação
    protected String usuario;
}


