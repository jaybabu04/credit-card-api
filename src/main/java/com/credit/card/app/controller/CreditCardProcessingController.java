package com.credit.card.app.controller;

import com.credit.card.app.model.CardInfo;
import com.credit.card.app.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit-card")
public class CreditCardProcessingController {

    @Autowired
    CreditCardService creditCardService;

    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save new card details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error" )
    })
    public ResponseEntity save(@Valid  @RequestBody CardInfo cardInfo){
        creditCardService.save(cardInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch all card's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public List<CardInfo> getAllCardInfo(){
        List<CardInfo> cardInfoList = creditCardService.getAllCardInfo();
        return cardInfoList;
    }

}
