package autotests.payloads;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetStoreOrder {

    @JsonProperty
    private int id;

    @JsonProperty
    private int petId;
    @JsonProperty
    private int quantity;
    @JsonProperty
    private String shipDate;

    @JsonProperty
    private String status;

    @JsonProperty
    private boolean complete;


}
