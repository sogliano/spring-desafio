package com.example.springdesafio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDTO {
    private String description;
    private String message;
    private Integer code;

    public StatusDTO(String description, String message){
        this.description = description;
        this.message = message;
    }

    public StatusDTO(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
