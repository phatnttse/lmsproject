package lms.be.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lms.be.dtos.PaymentDTO;
import lms.be.dtos.TestDTO;
import lms.be.dtos.TransactionDTO;
import lms.be.models.Transaction;
import lms.be.services.transaction.ITransactionService;
import lms.be.utils.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ITransactionService transactionService;

    @PostMapping("/create_payment")
    public String createPayment(@Valid  @RequestBody PaymentDTO paymentDTO) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = paymentDTO.getAmount() * 100;
        String bankCode = paymentDTO.getBankCode();

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = paymentDTO.getLanguage();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl
                + "?fee_id="+ paymentDTO.getFeeId() + "&user_id="+ paymentDTO.getUserId());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    @GetMapping("callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response) throws Exception {
        try {
            String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
            String amountStr = queryParams.get("vnp_Amount");
            String userIdStr = queryParams.get("user_id");
            String feeIdStr = queryParams.get("fee_id");
            String transactionDate = queryParams.get("vnp_PayDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");


            if (amountStr != null && userIdStr != null && feeIdStr != null){

                if ("00".equals(vnp_ResponseCode)){
                    TransactionDTO transactionDTO = TransactionDTO.builder()
                            .feeId(Integer.parseInt(feeIdStr))
                            .studentId(Integer.parseInt(userIdStr))
                            .amount(Integer.parseInt(amountStr))
                            .transactionDate(dateFormat.parse(transactionDate))
                            .build();

                    Transaction transaction = transactionService.createTransaction(transactionDTO);
                    response.sendRedirect("http://localhost:4200/fees?payment_success=true");
                }else {
                    response.sendRedirect("http://localhost:4200/fees?payment_success=false");
                }

            }

        }catch (Exception e){
            response.sendRedirect("http://localhost:4200/fees?payment_success=false");
            System.out.println(e.getMessage());
        }
    }



}
