package org.msa.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {
    @NotBlank(message = "ID는 필수입력해라")
    @Size(max = 10, message = "ID 10자리 이하로 해라")
    private String accountId;

    @NotBlank(message = "비번은 필수지")
    private String password;

    private String token;


}
