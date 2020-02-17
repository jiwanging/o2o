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
	 * ��CommonsMultipartFileת��ΪFile����
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
	 * ��������ͼ ������������ͼƬ�����·��
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();//��ȡΨһ������ļ���
		String extension = getFileExtension(fileName);//��ȡ��չ��
		makeDirPath(targetAddr);//��ָ��·���ϴ���Ŀ¼
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
		return relativeAddr;//����ͼƬ�����·�� �������ݿ���д洢 ���·�����ܻ���������
	}
	
	/**
	 * ��������ͼ ������������ͼƬ�����·��
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(InputStream ImgInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();//��ȡΨһ������ļ���
		String extension = getFileExtension(fileName);//��ȡ��չ��
		makeDirPath(targetAddr);//��ָ��·���ϴ���Ŀ¼
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
		return relativeAddr;//����ͼƬ�����·�� �������ݿ���д洢 ���·�����ܻ���������
	}


	/**
	 * ����Ŀ��·�����û�ָ����Ŀ¼�������漰����Ŀ¼ ��/home/work/felix/xxx.jpg
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
	 * ��ȡ�����ļ�������չ��
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * ����������ļ� ��ǰ������Сʱ��������+��λ�����
	 * @return
	 */
	public static String getRandomFileName() {
		
		int randNum = random.nextInt(89999)+10000;//��ȡ�������λ��
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+randNum;
	}
	
	/**
	 * storePath���ļ���·������Ŀ¼��·����
	 * ���storePath���ļ�·����ɾ�����ļ�
	 * ���storePath��Ŀ¼·����ɾ����Ŀ¼�µ������ļ�
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
