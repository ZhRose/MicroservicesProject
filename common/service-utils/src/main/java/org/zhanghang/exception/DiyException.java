package org.zhanghang.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiyException extends  RuntimeException{
    private Integer code;
    private String  msg;
}
