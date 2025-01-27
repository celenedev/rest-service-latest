package za.co.atm.discovery.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.co.atm.discovery.dto.ClientDTO;
import za.co.atm.discovery.entity.Client;
import za.co.atm.discovery.exceptionhandling.ATMException;
import za.co.atm.discovery.repository.ClientRepository;

import java.util.Optional;

@DataJpaTest
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void testSuccessfulGetClientDTO() {

        Client client = new Client(1, "Miss", "Name", "Surname", null, null);

        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientDTO dto = clientService.getClientDTO("1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Surname", dto.getSurname());
        Assertions.assertEquals("Name", dto.getName());
        Assertions.assertEquals("Miss", dto.getTitle());
    }

    @Test
    void testExceptionGetClientDTO() {

        Exception exception = Assertions.assertThrows(ATMException.class, () -> clientService.getClientDTO("9"));

        Assertions.assertEquals("No Client with clientId = 9", exception.getMessage());

    }
}
