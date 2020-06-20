package cn.tedu.store.service.ex;

/**
 * 商品状态的异常
 */
public class ProductStatusException extends ServiceException {

	private static final long serialVersionUID = -6734809874992293870L;

	public ProductStatusException() {
		super();
	}

	public ProductStatusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductStatusException(String message) {
		super(message);
	}

	public ProductStatusException(Throwable cause) {
		super(cause);
	}

}
