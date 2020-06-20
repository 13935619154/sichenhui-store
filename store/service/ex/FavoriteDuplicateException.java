package cn.tedu.store.service.ex;

/**
 * 收藏数据已经存在的异常
 */
public class FavoriteDuplicateException extends ServiceException {

	private static final long serialVersionUID = 4816998119047010636L;

	public FavoriteDuplicateException() {
		super();
	}

	public FavoriteDuplicateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FavoriteDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FavoriteDuplicateException(String message) {
		super(message);
	}

	public FavoriteDuplicateException(Throwable cause) {
		super(cause);
	}

}
