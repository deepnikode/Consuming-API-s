package com.deep.stockclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Setter

    public class Users
    {

        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("employee_code")
        private String employee_code;

        @JsonProperty("role")
        private String role;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("email")
        private String email;

        @JsonProperty("personal_email")
        private String personalEmail;

        @JsonProperty("pan_number")
        private String panNumber;

        @JsonProperty("adhar_number")
        private String adharNumber;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("alternate_phone")
        private String alternatePhone;

        @JsonProperty("current_address")
        private String currentAddress;

        @JsonProperty("permanent_address")
        private String permanentAddress;

        @JsonProperty("designation")
        private String designation;

        @JsonProperty("default_salary")
        private double defaultSalary;

        @JsonProperty("joining_date")
        private LocalDateTime joiningDate;

        @JsonProperty("date_of_birth")
        private LocalDateTime dateOfBirth;

        @JsonProperty("email_verified_at")
        private LocalDateTime emailVerifiedAt;

        @JsonProperty("is_active")
        private boolean isActive;

        @JsonProperty("colour_pallate")
        private String colourPalette;

        @JsonProperty("image")
        private String image;

        @JsonProperty("pan_document")
        private String panDocument;

        @JsonProperty("adhar_document")
        private String adharDocument;

        @JsonProperty("api_token")
        private String apiToken;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;

        @JsonProperty("updated_at")
        private LocalDateTime updatedAt;

                    // GETTER-SETTER OF IS_ACTIVE
                    public boolean isActive() {
                        return isActive;
                    }

                    public void setActive(boolean active) {
                        isActive = active;
                    }
    }
