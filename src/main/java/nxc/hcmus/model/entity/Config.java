package nxc.hcmus.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    @JsonProperty("available_id")
    private Long availableId;

    public Long getAvailableId() {
        return availableId;
    }

    public void setAvailableId(Long availableId) {
        this.availableId = availableId;
    }

    public Config(Long availableId) {
        this.availableId = availableId;
    }

    public Config() {

    }
}
