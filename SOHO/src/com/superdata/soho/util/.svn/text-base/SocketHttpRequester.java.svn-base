package com.superdata.soho.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.FormFile;

/**
 * 
 * 建立socket连接实现文件上传
 * @author luolei
 * @title SocketHttpRequester.java
 * @package com.yubang.common.util
 * @date 2014-4-7 上午8:38:52
 * @version 1.0.0.1 Copyright Copyright (c) 2014
 */
public class SocketHttpRequester {
	Map<String, String> params;
	FormFile formfile;
	String requestUrl;
	private Context mContext;

	private int RETURN_SUCCESS_CODE = 1;
	private int RETURN_FALSE_CODE = 2;
	private int returnCode;

	public SocketHttpRequester(Context context) {
		mContext = context;
	}

	/**
	 * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能: <FORM METHOD=POST
	 * ACTION="http://192.168.0.117:8080/upload/servlet/UploadServlet"
	 * enctype="multipart/form-data"> <INPUT TYPE="text" NAME="name"> <INPUT
	 * TYPE="text" NAME="id"> <input type="file" name="imagefile"/> <input
	 * type="file" name="zip"/> </FORM>
	 * 
	 * @param path
	 *            上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://
	 *            www.iteye.cn或http://192.168.0.117:8080这样的路径测试)
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param file
	 *            上传文件
	 */
	public int post(String path, Map<String, String> params, FormFile[] files)
			throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		for (FormFile uploadFile : files) {// 得到文件类型数据的总长度
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null) {
				fileDataLength += uploadFile.getFile().length();
			} else {
				fileDataLength += uploadFile.getData().length;
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据,解析上传
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 8080 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		for (FormFile uploadFile : files) {
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile.getInStream() != null) {
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
					outStream.write(buffer, 0, len);
				}
				uploadFile.getInStream().close();
			} else {
				outStream.write(uploadFile.getData(), 0,
						uploadFile.getData().length);
			}
			outStream.write("\r\n".getBytes());
		}
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		String str;
		if (reader.readLine().indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			return RETURN_FALSE_CODE;
		}
		while ((str = reader.readLine()) != null) {
			Log.v("reader.readLine()--", str);
		}
		outStream.flush();
		outStream.close();
		reader.close();
		socket.close();
		return RETURN_SUCCESS_CODE;
	}

	/**
	 * 提交数据到服务器
	 * 
	 * @param path
	 *            上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://
	 *            www.itcast.cn或http://192.168.0.117:8080这样的路径测试)
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param file
	 *            上传文件
	 */
	public int post(String path, Map<String, String> params, FormFile file)
			throws Exception {
		return post(path, params, new FormFile[] { file });
	}

	/**
	 * 文件上传调用开始
	 * 
	 * @param filePath
	 *            文件全路径
	 * @return
	 * @throws Exception
	 */
	public int upoloadFile(String filePath) throws Exception {
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(mContext);
		String guid = mapconfig.get(InterfaceConfig.ID);
		String username = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);
		String userkey = mapconfig.get(InterfaceConfig.PASSWORD);
		if (guid != null) {
			requestUrl = InterfaceConfig.FILE_UPLOAD_URL;//上传文件URL
			String filename = filePath.substring(filePath.lastIndexOf("/") + 1);//截取文件名字
			String filemd5 = MD5.EncodeFile16(filePath);
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);//将文件转换成流
			byte[] Betys = new byte[is.available()];
			int len;
			while ((len = is.read(Betys)) != -1) {
			}
			int filesize = (int) file.length();
			is.close();

			if (userkey.equals("null")) {
				userkey = "";
			}
			// 添加附加附件信息
			params = new HashMap<String, String>();
			params.put("guid", guid);
			params.put("username", username);
			params.put("userkey", userkey);
			params.put("filesize", String.valueOf(filesize));
			params.put("filename", filename);
			params.put("filemd5", filemd5);
			params.put("fileName", file.getName());
			// 上传文件
			formfile = new FormFile(file.getName(), file, "image",
					"application/octet-stream");
			return post(requestUrl, params, formfile);
		} else {
			return RETURN_FALSE_CODE;
		}

	}

}