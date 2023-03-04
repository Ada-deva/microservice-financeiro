package com.br.microservice.financeiro.service;

import com.br.microservice.financeiro.client.ViaCEPApiCLient;
import com.br.microservice.financeiro.dto.ClienteDTO;
import com.br.microservice.financeiro.exception.InformacaoInvalidaException;
import com.br.microservice.financeiro.model.Cliente;
import com.br.microservice.financeiro.model.Endereco;
import com.br.microservice.financeiro.model.ViaCEPApi;
import com.br.microservice.financeiro.repository.ClienteRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCEPApiCLient viaCEPApiCLient;

    @Value("${application.email}")
    private String applicationEmail;

    @Value("${sendgrid.sendgrid-api-key}")
    private String sendgridKey;

    public Optional<Cliente> cadastrar(Cliente cliente) throws InformacaoInvalidaException, IOException {
        Endereco endereco = cliente.getEndereco();
        ViaCEPApi enderecoEncontrado =  viaCEPApiCLient.getViaCEPApi(endereco.getCep());
        if(enderecoEncontrado == null) {
            throw new InformacaoInvalidaException("CEP não encontrado!");
        }
        if(endereco.getLogradouro() == null
                || endereco.getLogradouro().isBlank()
                || endereco.getLogradouro().isEmpty()) {
            endereco.setLogradouro(enderecoEncontrado.getLogradouro());
        }

        if(endereco.getBairro() == null
                || endereco.getBairro().isBlank()
                || endereco.getBairro().isEmpty()) {
            endereco.setBairro(enderecoEncontrado.getBairro());
        }

        if(endereco.getCidade() == null
                || endereco.getCidade().isBlank()
                || endereco.getCidade().isEmpty()) {
            endereco.setCidade(enderecoEncontrado.getLocalidade());
        }

        if(endereco.getEstado() == null
                || endereco.getEstado().isBlank()
                || endereco.getEstado().isEmpty()) {
            endereco.setEstado(enderecoEncontrado.getUf());
        }

        cliente.setDataCadastro(LocalDateTime.now());

        clienteRepository.save(cliente);

        if(cliente.getEmail() != null) {
            sendGridEmailer(cliente.getEmail());
        }

        return Optional.of(cliente);
    }

    public List<Cliente> clienteList() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Optional<Cliente> encontrarPorId(Long id) {
        return clienteRepository.findById(id);

    }

    public Optional<Cliente> atualizarCliente(ClienteDTO cliente, Long id)  {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);


        if (clienteEncontrado.isPresent()) {
            if(cliente.getNome() != null && !cliente.getNome().isEmpty()) {
                clienteEncontrado.get().setNome(cliente.getNome());
            }

            if(cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                clienteEncontrado.get().setEmail(cliente.getEmail());
            }

            if(cliente.getCelular() !=null && !cliente.getCelular().isEmpty()) {
                clienteEncontrado.get().setCelular(cliente.getCelular());
            }

            if(cliente.getCpf() !=null && !cliente.getCpf().isEmpty()) {
                clienteEncontrado.get().setCpf(cliente.getCpf());
            }

            clienteRepository.save(clienteEncontrado.get());

        }

        return clienteEncontrado;
    }



    public Optional<Cliente> deletarCliente(Long id) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);

        clienteEncontrado.ifPresent(cliente -> clienteRepository.delete(cliente));

        return clienteEncontrado;
    }



    public void sendGridEmailer(String email) throws IOException {
        Email from = new Email(applicationEmail);

        Email to = new Email(email);

        String subject = "Bem vindo ao AdaBurger";
        Content content = new Content("text/html", "Obrigada por se cadastrar em nosso sistema" +
                "<strong>AdaBurger</strong>");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendgridKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        log.info("---Enviando E-mail: response code " + response.getStatusCode() + "---");

    }

}
