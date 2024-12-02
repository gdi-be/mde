package de.terrestris.mde.mde_backend.model;

import de.terrestris.mde.mde_backend.model.json.JsonClientMetadata;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client_metadata")
@Data
public class ClientMetadata extends BaseMetadata {

  @Column
  @Type(JsonBinaryType.class)
  @ToString.Exclude
  private List<JsonClientMetadata> data;

}
