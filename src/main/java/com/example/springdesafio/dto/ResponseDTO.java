package com.example.springdesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private TicketDTO ticket;
    private StatusDTO status;
}
