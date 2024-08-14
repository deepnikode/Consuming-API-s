package com.deep.stockclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Users {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email_verified_at")
    private String emailVerifiedAt;

    @JsonProperty("role")
    private String role;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("colour_pallate") // Note the JSON key name
    private String colourPalette;

    @JsonProperty("image")
    private String image;

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
