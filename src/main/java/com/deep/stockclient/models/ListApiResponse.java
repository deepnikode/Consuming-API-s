package com.deep.stockclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ListApiResponse
{

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Map<String, List<Assignment>> data;
}
