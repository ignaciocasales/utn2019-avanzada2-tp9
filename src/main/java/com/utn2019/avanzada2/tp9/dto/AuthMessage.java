package com.utn2019.avanzada2.tp9.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthMessage {
    private String jwt;
}
