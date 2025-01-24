package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
public class postPet {
    @JsonProperty
    private int id;

    @JsonProperty
    private postPetCategory category;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<String> photoUrls;

    @JsonProperty
    private List<postPetTags> tags;

    @JsonProperty
    private String status;

}
