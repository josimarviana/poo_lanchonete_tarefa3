package br.com.appdahora.lanchonete.api.model;

import br.com.appdahora.lanchonete.domain.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "clientes")
public class ClientesXmlWrapper {
    @JsonProperty("cliente")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull //obriga @Data a gerar construtor clientes
    private List<Cliente> clientes;

}
