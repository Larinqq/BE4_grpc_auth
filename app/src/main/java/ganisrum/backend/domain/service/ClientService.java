package ganisrum.backend.domain.service;

import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Client;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client add(Client client);

    void delete(UUID id);

    Address changeAddress(UUID clientId, Address address);

    List<Client> searchClientByClaims(String clientName, String clientSurname);

    Page<Client> searchWithPaginatition(Integer limit, Integer offset);
}
