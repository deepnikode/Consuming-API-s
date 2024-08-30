package com.deep.stockclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ListData {

    @JsonProperty("In progress")
    private List<Assignment> inProgress;

    @JsonProperty("To Do")
    private List<Assignment> toDo;

    @JsonProperty("In Review")
    private List<Assignment> inReview;

    @JsonProperty("Done")
    private List<Assignment> done;
}
