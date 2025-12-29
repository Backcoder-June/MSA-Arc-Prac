package org.msa.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.item.dto.ItemDTO;
import org.msa.item.dto.ResponseDTO;
import org.msa.item.dto.constant.ItemType;
import org.msa.item.exception.ApiException;
import org.msa.item.service.ItemService;
import org.msa.item.valid.ItemTypeValid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "vi/item")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @RequestMapping(value = "/add/{itemType}", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> add(@RequestBody @Valid ItemDTO itemDTO, @ItemTypeValid @PathVariable String itemType) throws Exception{
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();

        log.debug("path vari item type = {}",  itemType);
        log.debug("request add item id = {}",  itemDTO.getId());

        itemDTO.setItemType(itemType);
        itemService.insertItem(itemDTO);

        responseBuilder.code("200").message("success");
        return ResponseEntity.ok(responseBuilder.build());
    }
}