package br.com.appdahora.lanchonete.api.model;

import br.com.appdahora.lanchonete.domain.model.Pedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "pedidos")
public class PedidosXmlWrapper {
    @JsonProperty("pedido")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull //obriga @Data a gerar construtor clientes
    private List<Pedido> pedidos;

}
