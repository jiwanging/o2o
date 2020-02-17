package com.felix.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class  ImageUtil{
	
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class) ;
	private static String basePath = Thread.currentThread().
			getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random random = new Random();
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("E://thomas.jpg"))
		.size(200,200).watermark(Positions.BOTTOM_RIGHT,
				ImageIO.read(new File(basePath + "/watermaker.jpg")),0.25f)
		.outputQuality(0.8f).toFile("E://thomas1.jpg");
	}
	
	/**
	 * 将CommonsMultipartFile转换为File对象
	 * @param cfile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cfile) {
		File newFile = new File(cfile.getOriginalFilename());
		try {
			cfile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 处理缩略图 并返回新生成图片的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();//获取唯一的随机文件名
		String extension = getFileExtension(fileName);//获取扩展名
		makeDirPath(targetAddr);//在指定路径上创建目录
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("curren relativeAddr: " + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("curren complete Addr is : " + dest);
		try {
			Thumbnails.of(thumbnailInputStream).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath + "/watermaker.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
			
		}catch(IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;//返回图片的相对路径 用于数据库进行存储 相对路径不受机器的限制
	}
	
	/**
	 * 处理详情图 并返回新生成图片的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(InputStream ImgInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();//获取唯一的随机文件名
		String extension = getFileExtension(fileName);//获取扩展名
		makeDirPath(targetAddr);//在指定路径上创建目录
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("curren relativeAddr: " + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("curren complete Addr is : " + dest);
		try {
			Thumbnails.of(ImgInputStream).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath + "/watermaker.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
			
		}catch(IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;//返回图片的相对路径 用于数据库进行存储 相对路径不受机器的限制
	}


	/**
	 * 创建目标路径（用户指定的目录）上所涉及到的目录 即/home/work/felix/xxx.jpg
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机的文件 当前年月日小时分钟秒钟+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		
		int randNum = random.nextInt(89999)+10000;//获取随机的五位数
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+randNum;
	}
	
	/**
	 * storePath是文件的路径还是目录的路径，
	 * 如果storePath是文件路径则删除该文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
	
}
