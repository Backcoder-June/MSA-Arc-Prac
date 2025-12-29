package org.msa.authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.authentication.domain.Account;
import org.msa.authentication.dto.AccountDTO;
import org.msa.authentication.dto.ResponseDTO;
import org.msa.authentication.service.AccountService;
import org.msa.authentication.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "vi/account")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> join(@RequestBody AccountDTO accountDTO) throws Exception {
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
        Account account = accountService.selectAccount(accountDTO);
        if (account != null) {
            responseBuilder.code("100").message("account already exist");
        }else{
            accountService.saveAccount(accountDTO, null); // 처음 token 값 null 설정
            responseBuilder.code("200").message("account successfully created without Token at First");
        }

        log.debug("회원 가입자 = {}", accountDTO.getAccountId());
        return ResponseEntity.ok(responseBuilder.build());
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> token(@Valid @RequestBody AccountDTO accountDTO) throws Exception {
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
        Account account = accountService.selectAccount(accountDTO);

        if (account == null) {
            responseBuilder.code("101").message("account not found"); // 없는 회원인데 TOKEN 요청
        }else{
            String token = getToken(accountDTO);
            accountService.saveAccount(accountDTO, token);
            responseBuilder.code("200").message("account successfully created with Token!");
            responseBuilder.token(token);
        }

        log.debug("token account id = {}", accountDTO.getToken());
        return ResponseEntity.ok(responseBuilder.build());
    }

    private String getToken(AccountDTO accountDTO) throws Exception {
        return JWTUtil.generateToken(accountDTO);
    }
}
