package cn.tedu.store.service.ex;

/**
 * 购物车数据列表为空的异常
 */
public class CartListEmptyException extends ServiceException {

	private static final long serialVersionUID = 3879388040144722368L;

	public CartListEmptyException() {
		super();
	}

	public CartListEmptyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CartListEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CartListEmptyException(String message) {
		super(message);
	}

	public CartListEmptyException(Throwable cause) {
		super(cause);
	}

}
