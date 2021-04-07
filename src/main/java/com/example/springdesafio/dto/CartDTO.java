package com.example.springdesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private List<TicketDTO> tickets = new ArrayList<>();
    private int total;

    public void addTicket(TicketDTO ticket){
        tickets.add(ticket);
        calculateTotal();
    }

    private void calculateTotal(){
        int totalAux = 0;
        for(TicketDTO ticket : this.tickets){ totalAux += ticket.getTotal(); }
        this.total = totalAux;
    }
}
