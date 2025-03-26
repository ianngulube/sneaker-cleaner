package za.co.mafsoft.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeoLocation {
    @NotNull
    @NotEmpty
    @NotBlank
    private String latitude;
    @NotNull
    @NotEmpty
    @NotBlank
    private String longitude;
}
