package com.thesis.gama.controller;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Source;
import com.thesis.gama.dto.PaymentOrderSetDTO;
import com.thesis.gama.exceptions.NoDataFoundException;
import com.thesis.gama.service.StripeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/pay/stripe")
@Api(tags = "StripeController")
public class StripeController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Autowired
    StripeService stripeService;
    @Autowired
    Gson gson;
    @Value("${stripe.secret.key}")
    String secretKey;

    @PostMapping(path = "/paymentintent", produces = "application/json")
    public ResponseEntity<String> payment(@RequestBody PaymentOrderSetDTO paymentIntentDto) throws StripeException, NoDataFoundException {

        return new ResponseEntity<String>(gson.toJson(stripeService.paymentIntent(paymentIntentDto)), HttpStatus.OK);
    }


/**
        PaymentIntent paymentIntent = stripeService.paymentIntent(paymentIntentDto);
        StripeResponseDTO paymentResponse = new StripeResponseDTO(paymentIntent.getClientSecret());
        //String paymentStr = paymentIntent.getClientSecret();
        Gson gson = new Gson();
        //return gson.toJson(paymentResponse);
        return new ResponseEntity<String>(gson.toJson(paymentResponse), HttpStatus.OK);
 **/


    /**
    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = stripeService.confirm(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = stripeService.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }
    *
     * @return*/



    //multibanco e mbway, beta
    @PostMapping(path = "/sources", produces = "application/json")
    public ResponseEntity<String> createSource(@RequestBody PaymentOrderSetDTO paymentIntentDto) throws StripeException, NoDataFoundException {

        Stripe.apiKey = secretKey;
        Map<String, Object> sourceParams = new HashMap<>();
        sourceParams.put("type", "multibanco");
        sourceParams.put("currency", paymentIntentDto.getCurrency());
        sourceParams.put("amount", 1000);
        Map<String, Object> redirect = new HashMap<>();
        redirect.put("return_url", "https://google.com");
        sourceParams.put("redirect", redirect);
        Map<String, Object> ownerParams = new HashMap<>();
        ownerParams.put("email", "jenny.rosen@example.com");
        sourceParams.put("owner", ownerParams);
        //preciso de uma api que me gere entidades e referencias e elas sao enviadas neste objeto e quando o user pagar o webhook deteta

        return new ResponseEntity<String>(gson.toJson(Source.create(sourceParams)), HttpStatus.OK);
    }


}
