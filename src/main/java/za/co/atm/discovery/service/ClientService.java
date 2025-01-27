package za.co.atm.discovery.service;

import org.springframework.stereotype.Service;
import za.co.atm.discovery.dto.ClientDTO;
import za.co.atm.discovery.entity.Client;
import za.co.atm.discovery.exceptionhandling.ATMException;
import za.co.atm.discovery.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO getClientDTO(String clientId) {

        Optional<Client> aClient = Optional.ofNullable(clientRepository.findById(Long.valueOf(clientId))
                .orElseThrow(()
                        -> new ATMException(
                        "No Client with clientId = " + clientId)));

        return aClient.map(client -> ClientDTO.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .id(Long.valueOf(client.getId()))
                .title(client.getTitle())
                .build()).orElse(null);
    }

}

