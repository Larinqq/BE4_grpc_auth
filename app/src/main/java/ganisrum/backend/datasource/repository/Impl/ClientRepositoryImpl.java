package ganisrum.backend.datasource.repository.Impl;

import ganisrum.backend.datasource.mapper.AddressEntityMapper;
import ganisrum.backend.datasource.mapper.ClientEntityMapper;
import ganisrum.backend.datasource.model.AddressEntity;
import ganisrum.backend.datasource.model.ClientEntity;
import ganisrum.backend.datasource.repository.ClientRepository;
import ganisrum.backend.datasource.repository.jpa.ClientRepositoryJpa;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientRepositoryJpa repository;
    private final ClientEntityMapper clientEntityMapper;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Client save(Client client) {
        ClientEntity savedClient = repository.save(clientEntityMapper.toEntity(client));
        return clientEntityMapper.toDomain(savedClient);
    }

    @Override
    public void delete(UUID id) {
        findIfExistById(id);
        repository.deleteById(id);
    }

    @Override
    public Address changeAddress(UUID clientId, Address address) {
        ClientEntity client = findIfExistById(clientId);
        AddressEntity newAddress = addressEntityMapper.toEntity(address);
        newAddress.setId(client.getAddress().getId());
        client.setAddress(newAddress);
        ClientEntity savedClient = repository.save(client);
        return addressEntityMapper.toDomain(savedClient.getAddress());

    }

    private ClientEntity findIfExistById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Клиент с id: " + id + " не существует"));
    }


    @Override
    public List<Client> findByNameSurname(String clientName, String clientSurname) {

        String cleanName = cleanParam(clientName);
        String cleanSurname = cleanParam(clientSurname);

        Specification<ClientEntity> specification = Specification.anyOf();

        if (cleanName != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("clientName"), cleanName));
        }

        if (cleanSurname != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("clientSurname"), cleanSurname));
        }

        List<ClientEntity> clientEntities = repository.findAll(specification);

        return clientEntities.stream()
                .map(clientEntityMapper::toDomain)
                .toList();
    }

    private String cleanParam(String value) {
        if (value == null) return null;
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

    @Override
    public Page<Client> findAllWithPagination(PageRequest pageRequest) {
        Page<ClientEntity> entities = repository.findAllBy(pageRequest);
        return entities.
                map(clientEntityMapper::toDomain);

    }
}
