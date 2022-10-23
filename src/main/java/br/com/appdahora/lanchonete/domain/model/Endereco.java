package br.com.appdahora.lanchonete.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Endereco {
    @Column(length = 8, name = "endereco_cep")
    private String cep;
    @Column(name = "endereco_logradouro")
    private String logradouro;
    @Column(length = 10, name = "endereco_numero")
    private String numero;
    @Column(length = 40, name = "endereco_complemento")
    private String complemento;
    @Column(length = 40, name = "endereco_bairro")
    private String  bairro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id")
    private Cidade cidade;
}
