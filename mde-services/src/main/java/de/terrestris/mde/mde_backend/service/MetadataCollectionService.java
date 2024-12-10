package de.terrestris.mde.mde_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.terrestris.mde.mde_backend.jpa.ClientMetadataRepository;
import de.terrestris.mde.mde_backend.jpa.IsoMetadataRepository;
import de.terrestris.mde.mde_backend.jpa.TechnicalMetadataRepository;
import de.terrestris.mde.mde_backend.model.ClientMetadata;
import de.terrestris.mde.mde_backend.model.IsoMetadata;
import de.terrestris.mde.mde_backend.model.TechnicalMetadata;
import de.terrestris.mde.mde_backend.model.dto.MetadataCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MetadataCollectionService {

    @Autowired
    private ClientMetadataRepository clientMetadataRepository;

    @Autowired
    private TechnicalMetadataRepository technicalMetadataRepository;

    @Autowired
    private IsoMetadataRepository isoMetadataRepository;

    @Autowired
    @Lazy
    ObjectMapper objectMapper;

    @PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(returnObject.orElse(null), 'READ')")
    @Transactional(readOnly = true)
    public Optional<MetadataCollection> findOneByMetadataId(String metadataId) {
        MetadataCollection metadataCollection = new MetadataCollection();

        ClientMetadata clientMetadata = clientMetadataRepository.findByMetadataId(metadataId)
            .orElseThrow(() -> new NoSuchElementException("ClientMetadata not found for metadataId: " + metadataId));
        TechnicalMetadata technicalMetadata = technicalMetadataRepository.findByMetadataId(metadataId)
            .orElseThrow(() -> new NoSuchElementException("TechnicalMetadata not found for metadataId: " + metadataId));
        IsoMetadata isoMetadata = isoMetadataRepository.findByMetadataId(metadataId)
            .orElseThrow(() -> new NoSuchElementException("IsoMetadata not found for metadataId: " + metadataId));

        metadataCollection.setClientMetadata(clientMetadata.getData());
        metadataCollection.setTechnicalMetadata(technicalMetadata.getData());
        metadataCollection.setIsoMetadata(isoMetadata.getData());

        return  Optional.of(metadataCollection);
    }

}
