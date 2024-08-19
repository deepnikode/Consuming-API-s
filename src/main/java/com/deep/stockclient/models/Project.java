package com.deep.stockclient.models;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Project {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

