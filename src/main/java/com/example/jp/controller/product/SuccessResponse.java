package com.example.jp.controller.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuccessResponse {
    private int code;
    private String message;
}
