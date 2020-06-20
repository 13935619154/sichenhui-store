package cn.tedu.store.service.ex;

/**
 * 商品库存不足的异常
 */
public class ProductOutOfStockException extends ServiceException {

	private static final long serialVersionUID = 5474881212831659811L;

	public ProductOutOfStockException() {
		super();
	}

	public ProductOutOfStockException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductOutOfStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductOutOfStockException(String message) {
		super(message);
	}

	public ProductOutOfStockException(Throwable cause) {
		super(cause);
	}

}
