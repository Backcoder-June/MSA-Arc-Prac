package org.msa.authentication.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNT")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "ACCOUNT_ID", length = 30)
    private String accountId;

    @Column(name = "PASS_WD", length = 100)
    private String password;

    @Column(name = "TOKEN", length = 500)
    private String token;
}
