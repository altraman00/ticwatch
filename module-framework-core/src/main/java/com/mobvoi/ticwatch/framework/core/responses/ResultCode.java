package com.mobvoi.ticwatch.framework.core.responses;

public enum ResultCode {

	SUCCESS(Result.SUCCESS,"成功")
	,ERROR(Result.ERROR,"系统错误")
	,BIND_ERROR(400,"参数不合法")
	,FORBIDDEN(403,"token失效")
	,NO_TOKEN(401,"无token，请重新登录")
	,ERROR_TOKEN(401,"token异常，请重新登陆")

	;

    private Integer code;
	private String msg;


	/**
	 * 拿到Result对象
	 * @return
	 */
	public Result get(){
		return new Result().setCode(this.code).setMsg(this.msg);
	}

	ResultCode(Integer status,String msg){
		this.code = status;
		this.msg = msg;
	}

	public static ResultCode getByCode(Integer status){
		ResultCode tab = null;
		ResultCode[] values = ResultCode.values();
		for (ResultCode value : values) {
			if (status.equals(value.code)) {
				tab = value;
			}
		}
		return tab;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
