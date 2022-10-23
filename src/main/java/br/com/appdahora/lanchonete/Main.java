package br.com.appdahora.lanchonete;

import br.com.appdahora.lanchonete.domain.model.Cliente;
import br.com.appdahora.lanchonete.domain.repository.ClienteRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext =  new SpringApplicationBuilder(LanchoneteApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        /*
        ClienteRepository clientes = applicationContext.getBean(ClienteRepository.class);

        //listando todos os clientes
        List<Cliente> todosClientes = clientes.findAll();

        for (Cliente cliente : todosClientes) {
            System.out.println(cliente.getId()+" - "+cliente.getNome());
        }

        //inserção de novo cliente
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Pedro");
        cliente1.setCpf("44444444444");
        cliente1.setEmail("pedro@gmail.com");
        cliente1.setTelefone("5478945612");

        clientes.save(cliente1);

        //busca por id
        Cliente cliente2 = clientes.buscar(1L);
        System.out.println(cliente2.getId()+ " - "+cliente2.getNome());

        //atualização de cliente
        Cliente cliente3 = clientes.buscar(1L);
        //Cliente cliente3 = new Cliente();
        //cliente3.setId(1L);
        cliente3.setNome("Josimar Viana");

        clientes.save(cliente3);

        //remoção de cliente
        Cliente cliente4 = clientes.buscar(2L);
        //Cliente cliente4 = new Cliente();
        cliente4.setId(1L);

        clientes.buscar(1L);
        */
    }
}
