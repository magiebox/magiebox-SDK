package com.tszj.chain.sdk.entities.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class View implements Serializable {

    private String status;
    private String message;
    private int code;
    private Object data;
}
