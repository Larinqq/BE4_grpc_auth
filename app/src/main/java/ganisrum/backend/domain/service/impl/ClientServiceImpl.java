package ganisrum.backend.domain.service.impl;

import ganisrum.backend.datasource.repository.ClientRepository;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Client;
import ganisrum.backend.domain.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public Client add(Client client) {
        return repository.save(client);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public Address changeAddress(UUID clientId, Address address) {

        return repository.changeAddress(clientId, address);
    }

    @Override
    @Transactional
    public List<Client> searchClientByClaims(String clientName, String clientSurname) {

        return repository.findByNameSurname(clientName, clientSurname);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> searchWithPaginatition(Integer limit, Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        return repository.findAllWithPagination(pageRequest);
    }
}
