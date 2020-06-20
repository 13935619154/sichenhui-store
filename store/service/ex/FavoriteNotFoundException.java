package cn.tedu.store.service.ex;

/**
 * 收藏数据不存在的异常
 */
public class FavoriteNotFoundException extends ServiceException {

	private static final long serialVersionUID = -7821037704326605495L;

	public FavoriteNotFoundException() {
		super();
	}

	public FavoriteNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FavoriteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FavoriteNotFoundException(String message) {
		super(message);
	}

	public FavoriteNotFoundException(Throwable cause) {
		super(cause);
	}

}
