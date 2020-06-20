package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileIOException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileStateException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.UserService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

	@Autowired
	public UserService userService;

	// http://localhost:8080/users/reg?username=void&password=1234&gender=1&phone=13000130000&email=void@163.com
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		System.err.println("UserController.reg()");
		long start = System.currentTimeMillis();
		userService.reg(user);
		long end = System.currentTimeMillis();
		System.err.println("耗时：" + (end - start) + "毫秒。");
		return new JsonResult<>(OK);
	}
	
	// http://localhost:8080/users/login?username=root&password=1234
	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		// 输出日志
		System.err.println("UserController.login()");
		System.err.println("\t参数username=" + username);
		System.err.println("\t参数password=" + password);
	    // 调用业务对象的方法执行登录业务，获取返回值
		User result = userService.login(username, password);
		System.err.println("\t登录结果：" + result);
	    // 将返回结果中的uid和username封装在Session中
		session.setAttribute("uid", result.getUid());
		session.setAttribute("username", result.getUsername());
		System.err.println("\tSession中记录的uid：" + session.getAttribute("uid"));
		System.err.println("\tSession中记录的username：" + session.getAttribute("username"));
	    // 响应“操作成功”和返回结果
		return new JsonResult<>(OK, result);
	}
	
	// 测试之前先登录
	// http://localhost:8080/users/password/change?oldPassword=1234&newPassword=8888
	@RequestMapping("password/change")
	public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
		System.err.println("UserController.changePassword()");
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		System.err.println("\tSession中记录的uid：" + uid);
		System.err.println("\tSession中记录的username：" + username);
		userService.changePassword(uid, username, oldPassword, newPassword);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:8080/users/info/show
	@GetMapping("info/show")
	public JsonResult<User> showInfo(HttpSession session) {
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		User result = userService.showInfo(uid);
		return new JsonResult<>(OK, result);
	}
	
	// http://localhost:8080/users/info/change?phone=aaa&email=bbb&gender=1
	@RequestMapping("info/change")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		userService.changeInfo(uid, username, user);
		return new JsonResult<>(OK);
	}
	
	/**
	 * 上传的头像文件的文件大小上限
	 */
	@Value("${project.avatar.max-size}")
	private long avatarMaxSize;
	@Value("${project.avatar.types}")
	private List<String> avatarTypes;
	
	// 无法直接通过浏览器输入网址来测试控制器层的功能
	@PostMapping("avatar/change")
	public JsonResult<String> changeAvatar(MultipartFile file, 
			HttpSession session) {
		// 输出日志
		System.err.println("UserController.changeAvatar()");
		
		// 判断上传的文件是否为空
		boolean isEmpty = file.isEmpty();
		System.err.println("\tisEmpty=" + isEmpty);
		if (isEmpty) {
			throw new FileEmptyException(
				"上传头像失败！请选择有效的头像文件！");
		}
		
		// 判断上传的文件大小是否超标
		long size = file.getSize();
		System.err.println("\tsize=" + size);
		if (size > avatarMaxSize) {
			throw new FileSizeException(
				"上传头像失败！不允许使用超过" + (avatarMaxSize / 1024) + "KB的图片文件！");
		}
		
		// 判断上传的文件类型是否超标
		String contentType = file.getContentType();
		System.err.println("\tcontentType=" + contentType);
		if (!avatarTypes.contains(contentType)) {
			throw new FileTypeException(
				"上传头像失败！只允许使用以下类型的文件作为头像：" + avatarTypes);
		}
		
		// 将客户端上传的文件保存到哪个文件夹
		String parentName = "upload";
		String parent = session.getServletContext().getRealPath(parentName);
		File parentDir = new File(parent);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		System.err.println("\t保存上传文件的文件夹：" + parent);
		
		// 将客户端上传的文件保存时，使用什么文件名
		String filename = "" + System.currentTimeMillis() 
			+ "-" + System.nanoTime();
		// 获取原始文件名
		String originalFilename = file.getOriginalFilename();
		System.err.println("\toriginalFilename=" + originalFilename);
		// 将客户端上传的文件保存时，使用什么扩展名
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if (beginIndex > 0) {
			suffix = originalFilename.substring(beginIndex);
		}
		// 将客户端上传的文件保存时，使用什么文件全名
		String child = filename + suffix;
		
		// 将客户端上传的文件保存到哪里
		File dest = new File(parent, child);
		// 将客户端上传的文件保存到服务器端指定的位置
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FileStateException("上传头像失败！请检查头像文件是否存在，并稍后再次尝试！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileIOException("上传头像失败！出现读写错误，请稍后再次尝试！");
		}
		
		// 将头像路径记录到数据库中
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		String avatar = "/" + parentName + "/" + child;
		System.err.println("\tavatar=" + avatar);
		userService.changeAvatar(uid, username, avatar);
		
		// 响应“成功”与“头像路径”
		return new JsonResult<>(OK, avatar);
	}

}










