package com.credit.card.app.model;

import com.credit.card.app.validation.Luhn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardInfo {
    @NotNull(message = "{name.required}")
    @NotBlank(message = "{name.required}")
    private String name;
    @Luhn
    private String cardNumber;
    private double creditlimit;
    private double balance=0;
    @Null
    private boolean active=false;
    @Null
    private LocalDateTime createdDateTime = LocalDateTime.now();
    @Null
    private LocalDateTime updatedDateTime = LocalDateTime.now();
    private String createdBy;
    private String updatedBy;

}
