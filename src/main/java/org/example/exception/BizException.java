package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * All business logic exceptions should use this. Need to clearly define the error code and error message
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException {
    private int errCode;//错误码
    private String errMsg;//错误信息
}
