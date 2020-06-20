package cn.tedu.store.service.ex;

/**
 * 购物车中商品的数量超出了限制的异常
 */
public class ProductNumLimitException extends ServiceException {

	private static final long serialVersionUID = -1234855985966283069L;

	public ProductNumLimitException() {
		super();
	}

	public ProductNumLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductNumLimitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNumLimitException(String message) {
		super(message);
	}

	public ProductNumLimitException(Throwable cause) {
		super(cause);
	}

}
