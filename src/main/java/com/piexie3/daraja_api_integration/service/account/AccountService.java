package com.piexie3.daraja_api_integration.service.account;

import com.google.api.client.util.DateTime;
import com.piexie3.daraja_api_integration.dto.*;
import com.piexie3.daraja_api_integration.models.EmailDetails;
import com.piexie3.daraja_api_integration.models.User;
import com.piexie3.daraja_api_integration.repository.auth.UserRepository;
import com.piexie3.daraja_api_integration.service.email.EmailService;
import com.piexie3.daraja_api_integration.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_FOUND_CODE)
                .message(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getSurname())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return AccountUtils.ACCOUNT_EXISTS_MESSAGE;
        }
        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        return user.getFirstName()+" "+user.getLastName()+" "+user.getSurname();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        // to add big decimal is by getting first the current BigDecimal and adding it to another
        user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
        userRepository.save(user);
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_CREDITED_CODE)
                .message(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getSurname())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        // account is there
        // balance is not less tha what to withdraw
        boolean existsByAccountNumber = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!existsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        int availableBalance = user.getAccountBalance().intValue();
        int debitAmount = request.getAmount().intValue();
        if (availableBalance<debitAmount){
            return BankResponse.builder()
                    .code(AccountUtils.INSUFFICIENT_FUND_CODE)
                    .message(AccountUtils.INSUFFICIENT_FUND_MESSAGE)
                    .data(null)
                    .build();
        }
        else {
            user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(user);
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_DEBITED_CODE)
                    .message(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .data(AccountInfo.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountBalance(user.getAccountBalance())
                            .accountName(user.getFirstName()+" "+user.getLastName()+" " +user.getSurname())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        boolean destinationExistsByAccountNumber = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if (!destinationExistsByAccountNumber){
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        User sourceUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User destinationUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        int availableBalance = sourceUser.getAccountBalance().intValue();
        int debitAmount = request.getAmount().intValue();
        if (availableBalance<debitAmount){
            return BankResponse.builder()
                    .code(AccountUtils.INSUFFICIENT_FUND_CODE)
                    .message(AccountUtils.INSUFFICIENT_FUND_MESSAGE)
                    .data(null)
                    .build();
        }
        sourceUser.setAccountBalance(sourceUser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceUser);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formatedDate = dtf.format(now);
        EmailDetails sentMoneyAlert = EmailDetails.builder()
                .subject("You've Sent Money Successfully!")
                .recipient(sourceUser.getEmail())
                .messageBody("""
                        Hi %s,
                        
                        We're excited to let you know that your payment has been sent successfully!
                        
                        Transaction Summary
                        Amount Sent: $%s
                        Balance: $%s
                        Recipient: %s
                        Transaction ID: %s
                        Date: %s
                        If you have any questions or concerns about this transaction, feel free to visit our Help Center or contact our support team.
                        
                        Thank you for using Afripay!
                        
                        Best,
                        The Afripay Team
                        """.formatted(sourceUser.getFirstName(),request.getAmount(),sourceUser.getAccountBalance(),destinationUser.getFirstName(),"12345678", formatedDate)
                )
                .build();
        emailService.sendEmailAlert(sentMoneyAlert);
        destinationUser.setAccountBalance(destinationUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationUser);
        EmailDetails receivedMoneyAlert = EmailDetails.builder()
                .subject("Payment Received - Thank You!")
                .recipient(destinationUser.getEmail())
                .messageBody("""
                        Hi %s,
                        
                        We’re excited to let you know that you’ve received a payment!
                        
                        Transaction Summary
                        Amount Received: $%s
                        Balance: $%s
                        Sender: %s
                        Transaction ID: %s
                        Date: %s
                        You can view the details of this transaction by logging into your Afripay account.
                        
                        Thank you for using Afripay. If you have any questions or concerns regarding this transaction, please don’t hesitate to reach out to our customer support team.
                        
                        Best,
                        The Afripay Team
                        """.formatted(destinationUser.getFirstName(),request.getAmount(),destinationUser.getAccountBalance(),sourceUser.getFirstName(),"12345678", formatedDate)
                )
                .build();
        emailService.sendEmailAlert(receivedMoneyAlert);
        return BankResponse.builder()
                .code(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .message(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .data(null)
                .build();
    }
}
