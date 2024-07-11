package com.andjela.diplomski.controller;

import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.repository.OrderRepository;
import com.andjela.diplomski.service.CartItemService;
import com.andjela.diplomski.service.OrderService;
import com.andjela.diplomski.service.paypal.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaypalController {

    private final PaypalService paypalService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestParam Double total,
                                                             @RequestParam String currency,
                                                             @RequestParam String description) {
        try {
            log.info("Received payment request - Total: {}, Currency: {}, Description: {}", total, currency, description);

            String cancelUrl = "http://localhost:8080/api/payments/cancel";
            String successUrl = "http://localhost:8080/api/payments/success";
            Payment payment = paypalService.createPayment(
                    total,
                    currency,
                    "paypal",
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    Map<String, String> response = new HashMap<>();
                    response.put("redirect_url", link.getHref());
                    response.put("paymentId", payment.getId()); // Vratite paymentId kao deo odgovora
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updatePaymentId")
    public ResponseEntity<Void> updatePaymentId(@RequestParam Long orderId, @RequestParam String paymentId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setPaymentId(paymentId);
            orderRepository.save(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/success")
    public void paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpServletResponse response) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                orderService.updateOrderAfterPayment(paymentId, true, LocalDateTime.now(), "PayPal", paymentId);
                cartItemService.removeAllCartItemsByPayerId(paymentId);
                response.sendRedirect("http://localhost:3000/payments/success?paymentId=" + paymentId + "&PayerID=" + payerId);
            } else {
                response.sendRedirect("http://localhost:3000/payment-failure");
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            try {
                response.sendRedirect("http://localhost:3000/payment-failure");
            } catch (IOException ioException) {
                log.error(ioException.getMessage());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return new ResponseEntity<>("Payment Canceled", HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<String> paymentError() {
        return new ResponseEntity<>("Payment Error", HttpStatus.OK);
    }

}
