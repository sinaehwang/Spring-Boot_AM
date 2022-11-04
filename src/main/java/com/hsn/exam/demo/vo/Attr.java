package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attr { //부가정보테이블
    private int id;
    private String regDate;
    private String updateDate;
    private String expireDate;
    private String relTypeCode;
    private int relId;
    private String typeCode;
    private String type2Code;
    private String value;
}
