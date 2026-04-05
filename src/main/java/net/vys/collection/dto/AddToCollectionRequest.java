package net.vys.collection.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import net.vys.collection.entities.UserComic.CollectionStatus;

@Schema(description = "Request para añadir un cómic a la colección")
public class AddToCollectionRequest {

    @NotNull(message = "El estado no puede ser nulo")
    @Schema(description = "Estado del cómic en la colección", example = "OWNED", allowableValues = {"OWNED", "WANTED"})
    private CollectionStatus status;

    public AddToCollectionRequest() {}

    public CollectionStatus getStatus() { return status; }
    public void setStatus(CollectionStatus status) { this.status = status; }
}