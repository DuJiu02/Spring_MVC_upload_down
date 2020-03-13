package com.hr.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("file")
public class FileController {

	@ExceptionHandler
	public String exceptionHandler(Exception e, HttpServletRequest req) {
		if (e instanceof MaxUploadSizeExceededException) {
			MaxUploadSizeExceededException me = (MaxUploadSizeExceededException) e;
			req.setAttribute("msg", "文件太大了,最大:" + me.getMaxUploadSize());
		} else {
			req.setAttribute("msg", e.getMessage());
		}
		return "/upload.jsp";
	}

	@RequestMapping("upload")
	public String upload(MultipartFile img, HttpServletRequest req) throws Exception {
		System.out.println(img.getContentType());// 获得文件类型
		System.out.println(img.getName());// 获得文件表单的name属性值
		System.out.println(img.getOriginalFilename());// 获得上传文件的名字
		System.out.println(img.getSize());// 获得文件大小,单位:字节

		if (!img.getContentType().contentEquals("image/png") && !img.getContentType().contentEquals("image/jpg")
				&& !img.getContentType().contentEquals("image/jpeg")) {
			throw new Exception("文件类型错误");
		}

		String dir = req.getServletContext().getRealPath("upload");
		String name = UUID.randomUUID().toString();
		String extName = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
		String newFileName = name + extName;
		File to = new File(dir, newFileName);
		try {
			FileUtils.copyInputStreamToFile(img.getInputStream(), to);
		} catch (IOException e) {
			e.printStackTrace();
		}
		req.setAttribute("fileName", newFileName);

		return "/show.jsp";
	}

	@RequestMapping("down")
	public ResponseEntity<byte[]> down(String name, HttpServletRequest req) throws Exception {
		String path = req.getServletContext().getRealPath("upload/" + name);
		File file = new File(path);// 待下载的文件
		System.out.println(path);

		HttpHeaders header = new HttpHeaders();
		// 设置响应的类型为文件流
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 对中文文件名进行编码处理
		String fileName = new String("图片.png".getBytes(), "ISO8859-1");
		header.setContentDispositionFormData("attachment", fileName);
		// 创建响应对象
		ResponseEntity<byte[]> re = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header,
				HttpStatus.OK);
		System.out.println(HttpStatus.OK);
		return re;
	}

}
