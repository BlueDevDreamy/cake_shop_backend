package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.request.PaymentLinkResponse;
import com.andjela.diplomski.service.OrderService;
import com.andjela.diplomski.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final OrderService orderService;
    private final UserService userService;

    //rzp_test_kTsRSaDC8hwztC
    @Value("${razorpay.key}")
    private String razorpaykey;

    //LieoD1s9mxMIv569PcgRDMcU
    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @PostMapping("/{id}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long id,
                                                                 @RequestHeader("Authorization") String jwt) throws RazorpayException {
        OrderDto orderDto = orderService.getOrderById(id);
        try {
            // Instantiate a Razorpay client with your key ID and secret
            RazorpayClient razorpay = new RazorpayClient("rzp_test_kTsRSaDC8hwztX", "LieoD1s9mxMIv569PcgRDMcU");//ORIG
//		      RazorpayClient razorpay = new RazorpayClient(razorpaykey, razorpaySecret);

            // Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", orderDto.getTotalPrice() * 100);
            paymentLinkRequest.put("currency", "INR");
//		      paymentLinkRequest.put("expire_by",1691097057);
//		      paymentLinkRequest.put("reference_id",order.getId().toString());


            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name", orderDto.getUser().getFirstName() + " " + orderDto.getUser().getLastName());
            customer.put("contact", orderDto.getUser().getPhoneNumber());
            customer.put("email", orderDto.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable", true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url", "https://shopwithzosh.vercel.app/payment/" + id);
            paymentLinkRequest.put("callback_method", "get");

            // Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);

            PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);

            //orderDto.setOrderId(fetchedPayment.get("order_id"));
            //orderRepository.save(order);
            orderService.placeOrder(id);

            // Print the payment link ID and URL
            System.out.println("Payment link ID: " + paymentLinkId);
            System.out.println("Payment link URL: " + paymentLinkUrl);
            System.out.println("Order Id : " + fetchedPayment.get("order_id") + fetchedPayment);

            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.ACCEPTED);

        } catch (RazorpayException e) {

            System.out.println("Error creating payment link: " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }


//		order_id
    }

    @GetMapping
    public ResponseEntity<String> redirect(@RequestParam(name = "payment_id") String paymentId, @RequestParam("order_id") Long orderId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_kTsRSaDC8hwztX", "LieoD1s9mxMIv569PcgRDMcU");
        OrderDto orderDto = orderService.getOrderById(orderId);

        try {
            Payment payment = razorpay.payments.fetch(paymentId);
            System.out.println("payment details --- " + payment + payment.get("status"));

            if (payment.get("status").equals("captured")) {
                System.out.println("payment details --- " + payment + payment.get("status"));

//                orderDto.getPaymentDetails().setPaymentId(paymentId);
//                orderDto.getPaymentDetails().setPaymentStatus("COMPLETED");
                orderDto.setOrderStatus("PLACED");
//			order.setOrderItems(order.getOrderItems());
//                System.out.println(orderDto.getPaymentDetails().getPaymentStatus() + "payment status ");
                //orderRepository.save(order);
                orderService.placeOrder(orderId);
            }
            String res = "Your order get placed";
            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("errrr payment -------- ");
            new RedirectView("https://shopwithzosh.vercel.app/payment/failed");
            throw new RazorpayException(e.getMessage());
        }

    }
}
