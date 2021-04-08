package com.example.springdesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseClientDTO {
    private ClientDTO client;
    private StatusDTO status;
}