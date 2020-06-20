package cn.tedu.store.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileIOException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileStateException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.CartListEmptyException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.FavoriteDuplicateException;
import cn.tedu.store.service.ex.FavoriteNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ProductNotFoundException;
import cn.tedu.store.service.ex.ProductNumLimitException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ServiceException.class, FileUploadException.class})
	public JsonResult<Void> handleException(Throwable e) {
		JsonResult<Void> jsonResult = new JsonResult<Void>(e);

		if (e instanceof UsernameDuplicateException) {
			jsonResult.setState(4000);
		} else if (e instanceof UserNotFoundException) {
			jsonResult.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			jsonResult.setState(4002);
		} else if (e instanceof AddressSizeLimitException) {
			jsonResult.setState(4003);
		} else if (e instanceof AddressNotFoundException) {
			jsonResult.setState(4004);
		} else if (e instanceof AccessDeniedException) {
			jsonResult.setState(4005);
		} else if (e instanceof ProductNotFoundException) {
			jsonResult.setState(4006);
		} else if (e instanceof CartNotFoundException) {
			jsonResult.setState(4007);
		} else if (e instanceof ProductNumLimitException) {
			jsonResult.setState(4008);
		} else if (e instanceof FavoriteDuplicateException) {
			jsonResult.setState(4009);
		} else if (e instanceof FavoriteNotFoundException) {
			jsonResult.setState(4010);
		} else if (e instanceof CartListEmptyException) {
			jsonResult.setState(4011);
		} else if (e instanceof InsertException) {
			jsonResult.setState(5000);
		} else if (e instanceof UpdateException) {
			jsonResult.setState(5001);
		} else if (e instanceof DeleteException) {
			jsonResult.setState(5002);
		} else if (e instanceof FileEmptyException) {
			jsonResult.setState(6000);
		} else if (e instanceof FileSizeException) {
			jsonResult.setState(6001);
		} else if (e instanceof FileTypeException) {
			jsonResult.setState(6002);
		} else if (e instanceof FileStateException) {
			jsonResult.setState(6003);
		} else if (e instanceof FileIOException) {
			jsonResult.setState(6004);
		}

//    	switch (e.getClass().getName()) {
//		case "cn.tedu.store.service.ex.UsernameDuplicateException":
//			jsonResult.setState(4000);
//			break;
//		case "cn.tedu.store.service.ex.UserNotFoundException":
//			jsonResult.setState(4001);
//			break;
//		case "cn.tedu.store.service.ex.PasswordNotMatchException":
//			jsonResult.setState(4002);
//			break;
//		case "cn.tedu.store.service.ex.InsertException":
//			jsonResult.setState(5000);
//			break;
//		case "cn.tedu.store.service.ex.UpdateException":
//			jsonResult.setState(5001);
//			break;
//		}

		return jsonResult;
	}

}
