package com.andjela.diplomski.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private String paymentLinkUrl;
    private String paymentLinkId;
}
