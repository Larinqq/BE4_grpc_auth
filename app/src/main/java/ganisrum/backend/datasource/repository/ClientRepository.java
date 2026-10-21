package ganisrum.backend.datasource.repository;

import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {

    Client save(Client client);

    void delete(UUID id);

    Address changeAddress(UUID clientId, Address address);

    List<Client> findByNameSurname(String clientName, String clientSurname);

    Page<Client> findAllWithPagination(PageRequest pageRequest);
}
