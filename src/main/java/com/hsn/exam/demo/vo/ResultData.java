package com.hsn.exam.demo.vo;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ResultData<DT> {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	@Getter
	private String data1Name;
	@Getter
	private Object data2;
	@Getter
	private String data2Name;


	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null,null);
	}

	public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1,String data1Name) {
		ResultData<DT> rd = new ResultData<DT>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;

		return rd;
	}

	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}

	public boolean isFail() {
		return isSuccess() == false;
	}

	public static <DT> ResultData<DT> newData(ResultData Rd, String data1Name, DT data1) {
		return from(Rd.getResultCode(), Rd.getMsg(), data1,data1Name);
	}

	public void setData2(String dataName, Object data) {
		
		data2Name = dataName;
		
		data2 = data;
		
	}

}