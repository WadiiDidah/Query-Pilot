package com.querypilot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QueryRequest(
        @NotBlank(message = "La question est obligatoire")
        @Size(max = 1000, message = "La question est trop longue")
        String question
) {}
