package bean;

public class Result implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int code; // 1成功， 0失败
	private String msg;
	private Object data;

	/**
	 *	成功结果对象 
	 */
	public static Result success(String msg) {
		return new Result(1, msg);
	}

	/**
	 *	成功结果对象 
	 */
	public static Result success(String msg, Object data) {
		return new Result(1, msg, data);
	}

	/**
	 *	失败结果对象 
	 */
	public static Result failure(String msg) {
		return new Result(0, msg);
	}

	/**
	 *	失败结果对象 
	 */
	public static Result failure(String msg, Object data) {
		return new Result(0, msg, data);
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
