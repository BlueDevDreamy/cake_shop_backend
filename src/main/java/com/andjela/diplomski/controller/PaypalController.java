package com.andjela.diplomski.controller;

import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.repository.OrderRepository;
import com.andjela.diplomski.service.paypal.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaypalController {

    private final PaypalService paypalService;
    private final OrderRepository orderRepository;


    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestParam Integer total,
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
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/success")
    public void paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpServletResponse response) {
        try {
            Payment payment = paypalService.exceutePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // Pretpostavljamo da imate način da dobijete Order iz paymentId-a
                Order order = orderRepository.findByPaymentId(paymentId);
                System.out.println(order);
                if (order != null) {
                    order.setPaid(true);
                    order.setPaymentDate(LocalDateTime.now());
                    order.setPaymentMethod("PayPal");
                    order.setTransactionId(paymentId);
                    orderRepository.save(order);
                }
                response.sendRedirect("http://localhost:3000/payment-success?paymentId=" + paymentId + "&PayerID=" + payerId);
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
//    @GetMapping("/success")
//    public void paymentSuccess(
//            @RequestParam("paymentId") String paymentId,
//            @RequestParam("PayerID") String payerId,
//            HttpServletResponse response) {
//        try {
//            Payment payment = paypalService.exceutePayment(paymentId, payerId);
//            if (payment.getState().equals("approved")) {
//                // Preusmeravanje na frontend sa odgovarajućim parametrima
//                response.sendRedirect("http://localhost:3000/payment-success?paymentId=" + paymentId + "&PayerID=" + payerId);
//            } else {
//                response.sendRedirect("http://localhost:3000/payment-failure");
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//            try {
//                response.sendRedirect("http://localhost:3000/payment-failure");
//            } catch (IOException ioException) {
//                log.error(ioException.getMessage());
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }


//    @GetMapping("/success")
//    public ResponseEntity<String> paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//        try {
//            Payment payment = paypalService.exceutePayment(paymentId, payerId);
//            if (payment.getState().equals("approved")) {
//                return new ResponseEntity<>("Payment Successful", HttpStatus.OK);
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//        }
//        return new ResponseEntity<>("Payment failed", HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return new ResponseEntity<>("Payment Canceled", HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<String> paymentError() {
        return new ResponseEntity<>("Payment Error", HttpStatus.OK);
    }
//    @PostMapping("/create")
//    public RedirectView createPaypal() {
//        try {
//            String cancelUrl = "https://localhost:8080/payments/cancel";
//            String successUrl = "https://localhost:8080/payments/success";
//
//            Payment payment = paypalService.createPayment(
//                    100.0,
//                    "RSD",
//                    "paypal",
//                    "sale",
//                    "Payment Description",
//                    cancelUrl,
//                    successUrl
//            );
//            for (Links link : payment.getLinks()) {
//                if (link.getRel().equals("approval_url")) {
//                    return new RedirectView(link.getHref());
//                }
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//        }
//        return new RedirectView("/payments/error");
//    }
//
//    @GetMapping("/success")
//    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) {
//        try {
//            Payment payment = paypalService.exceutePayment(paymentId, payerId);
//            if (payment.getState().equals("approved")) {
//                return "Payment Successful";
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//        }
//        return "Payment Successful";
//    }
//
//    @GetMapping("/cancel")
//    public String paymentCancel() {
//        return "Payment Canceled";
//    }
//    @GetMapping("/cancel")
//    public String paymentError() {
//        return "Payment Error";
//    }
}
