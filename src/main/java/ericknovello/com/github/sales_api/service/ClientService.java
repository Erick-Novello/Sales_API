package ericknovello.com.github.sales_api.service;

import ericknovello.com.github.sales_api.model.Client;
import ericknovello.com.github.sales_api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public static final String CLIENT_NOT_FOUND = "Client not found";

    public Client includeClient(Client client) {
        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        return clientRepository.save(client);
    }

    public void updateClient(Client client) {
        clientRepository.findById(client.getId())
                .map(clientFound -> {
                    client.setId(clientFound.getId());
                    clientRepository.save(client);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public void deleteClient(Integer id) {
        clientRepository.findById(id)
                .map(clientFound -> {
                    clientRepository.delete(clientFound);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public Client findClientById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }

    public List<Client> filterClient(Client client) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> clientFiltration = Example.of(client, exampleMatcher);

        return clientRepository.findAll(clientFiltration);
    }


}
