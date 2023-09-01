package com.metrodata.PaymentService.service;


import com.metrodata.PaymentService.entity.Payment;
import com.metrodata.PaymentService.model.PaymentMode;
import com.metrodata.PaymentService.model.PaymentRequest;
import com.metrodata.PaymentService.model.PaymentResponse;
import com.metrodata.PaymentService.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class PaymentService {

    private PaymentRepository paymentRepository;

    public List<PaymentResponse> getAll(){
        log.info("Get all data transaction");
        return paymentRepository.findAll()
                .stream()
                .map(payment -> {
                    return paymentResponseMap(payment);
                }).collect(Collectors.toList());
    }

    public PaymentResponse getById(long id){
        log.info("Get Detail Transaction with id {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction with id " + id + " not found"));

        return paymentResponseMap(payment);
    }

    public PaymentResponse doPayment(PaymentRequest paymentRequest){
        log.info("Payment Request for user with data : {}", paymentRequest);
            Payment payment = Payment.builder()
                    .amount(paymentRequest.getAmount())
                    .mode(paymentRequest.getMode().name())
                    .orderId(paymentRequest.getOrderId())
                    .date(Instant.now())
                    .status("SUCCESS")
                    .build();
            paymentRepository.save(payment);
            log.info("Payment Completed with Id : {}", payment.getId());
            return paymentResponseMap(payment);
    }

    public PaymentResponse paymentResponseMap(Payment payment){
        PaymentResponse paymentResponse = new PaymentResponse();
        BeanUtils.copyProperties(payment,paymentResponse);
        paymentResponse.setMode(PaymentMode.valueOf(payment.getMode()));
        return paymentResponse;
    }

}
