package za.co.mafsoft.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Address {
    @NotNull
    @NotEmpty
    @NotBlank
    private String street;
    @NotNull
    @NotEmpty
    @NotBlank
    private String areaCode;
    @NotNull
    @NotEmpty
    @NotBlank
    private String suburb;
    @NotNull
    @NotEmpty
    @NotBlank
    private String province;
    @Valid
    private GeoLocation location;
}
