package org.msa.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemDTO {
    @NotBlank(message = "ID는 필수 입력값 입니다.")
    @Size(max=10, message = "ID는 10자리까지 입력 가능합니다.")
    private String id;


    private String itemType;

    private String name;
    private String description;

    @Positive
    private long count;

    private String regDts;
    private String updDts;
}
