package com.metrodata.PaymentService.controller;

import com.metrodata.PaymentService.model.PaymentRequest;
import com.metrodata.PaymentService.model.PaymentResponse;
import com.metrodata.PaymentService.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAll(){
        return new ResponseEntity<>(paymentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(paymentService.getById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(paymentService.doPayment(paymentRequest),HttpStatus.CREATED);
    }

}
