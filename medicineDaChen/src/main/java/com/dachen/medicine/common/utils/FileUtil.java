package com.dachen.medicine.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.dachen.medicine.app.MedicineApplication;
import com.dachen.medicine.entity.User;

public class FileUtil {

	/*
	 * private static final int TYPE_IMAGE = 1; private static final int
	 * TYPE_ADUIO = 2; private static final int TYPE_VIDEO = 3;
	 *//**
	 * {@link #TYPE_IMAGE}<br/>
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	/*
	 * private static String getPublicFilePath(int type) { String fileDir =
	 * null; String fileSuffix = null; switch (type) { case TYPE_ADUIO: fileDir
	 * = DApplication.getUniqueInstance().mVoicesDir; fileSuffix = ".mp3";
	 * break; case TYPE_VIDEO: fileDir =
	 * DApplication.getUniqueInstance().mVideosDir; fileSuffix = ".mp4"; break;
	 * case TYPE_IMAGE: fileDir = DApplication.getUniqueInstance().mPicturesDir;
	 * fileSuffix = ".jpg"; break; } if (fileDir == null) { return null; } File
	 * file = new File(fileDir); if (!file.exists()) { if (!file.mkdirs()) {
	 * return null; } } return fileDir + File.separator +
	 * UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix; }
	 *//**
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	/*
	 * private static String getPrivateFilePath(int type, String userId) {
	 * String fileDir = null; String fileSuffix = null; switch (type) { case
	 * TYPE_ADUIO: fileDir = DApplication.getUniqueInstance().mAppDir +
	 * File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC;
	 * fileSuffix = ".mp3"; break; case TYPE_VIDEO: fileDir =
	 * DApplication.getUniqueInstance().mAppDir + File.separator + userId +
	 * File.separator + Environment.DIRECTORY_MOVIES; fileSuffix = ".mp4";
	 * break; } if (fileDir == null) { return null; } File file = new
	 * File(fileDir); if (!file.exists()) { if (!file.mkdirs()) { return null; }
	 * } return fileDir + File.separator +
	 * UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix; }
	 * 
	 * public static String getRandomImageFilePath() { return
	 * getPublicFilePath(TYPE_IMAGE); }
	 * 
	 * public static String getRandomAudioFilePath() { User user =
	 * DApplication.getUniqueInstance().mLoginUser; if (user != null &&
	 * !TextUtils.isEmpty(user.getUserId())) { return
	 * getPrivateFilePath(TYPE_ADUIO, user.getUserId()); } else { return
	 * getPublicFilePath(TYPE_ADUIO); } }
	 * 
	 * public static String getRandomAudioAmrFilePath() { User user =
	 * DApplication.getUniqueInstance().mLoginUser; String filePath = null; if
	 * (user != null && !TextUtils.isEmpty(user.getUserId())) { filePath =
	 * getPrivateFilePath(TYPE_ADUIO, user.getUserId()); } else { filePath =
	 * getPublicFilePath(TYPE_ADUIO); } if (!TextUtils.isEmpty(filePath)) {
	 * return filePath.replace(".mp3", ".amr"); } else { return null; } }
	 * 
	 * public static String getRandomVideoFilePath() { User user =
	 * DApplication.getUniqueInstance().mLoginUser; if (user != null &&
	 * !TextUtils.isEmpty(user.getUserId())) { return
	 * getPrivateFilePath(TYPE_VIDEO, user.getUserId()); } else { return
	 * getPublicFilePath(TYPE_VIDEO); } }
	 * 
	 * //
	 * ///////////////////////////////////////////////////////////////////////
	 * ////////////////////////////
	 * 
	 * public static void createFileDir(String fileDir) { File fd = new
	 * File(fileDir); if (!fd.exists()) { fd.mkdirs(); } }
	 *//**
	 * 
	 * @param fullName
	 */
	/*
	 * public static void delFile(String fullName) { File file = new
	 * File(fullName); if (file.exists()) { if (file.isFile()) { try {
	 * file.delete(); } catch (Exception e) { e.printStackTrace(); } } } }
	 *//**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 /sdcard/data/
	 */
	/*
	 * public static void delAllFile(String path) { File file = new File(path);
	 * if (!file.exists()) { return; } if (!file.isDirectory()) { return; }
	 * String[] tempList = file.list(); File temp = null; for (int i = 0; i <
	 * tempList.length; i++) { System.out.println(path + tempList[i]); if
	 * (path.endsWith(File.separator)) { temp = new File(path + tempList[i]); }
	 * else { temp = new File(path + File.separator + tempList[i]); } if
	 * (temp.isFile()) { temp.delete(); } if (temp.isDirectory()) {
	 * delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件 delFolder(path + "/"
	 * + tempList[i]); // 再删除空文件夹 } } }
	 *//**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如/sdcard/data/
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	/*
	 * public static void delFolder(String folderPath) { try {
	 * delAllFile(folderPath); // 删除完里面所有内容 String filePath = folderPath;
	 * filePath = filePath.toString(); java.io.File myFilePath = new
	 * java.io.File(filePath); myFilePath.delete(); // 删除空文件夹 } catch (Exception
	 * e) { System.out.println("删除文件夹操作出错"); e.printStackTrace(); } }
	 */
	private static final int TYPE_IMAGE = 1;
	private static final int TYPE_ADUIO = 2;
	private static final int TYPE_VIDEO = 3;
	public static File compressImageToFile(String filePath, int q)
			throws FileNotFoundException {

		Bitmap bm = getSmallBitmap(filePath);
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		File outputFile = new File(getAlbumDir(), fileName);
		FileOutputStream out = new FileOutputStream(outputFile);
		bm.compress(Bitmap.CompressFormat.JPEG, q, out);
		File file = new File(outputFile.getPath());
		return file;
	}
	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 *
	 * @return
	 */
	public static String getAlbumName() {
		return "patient";
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	/**
	 * 获取保存图片的目录
	 *
	 * @return
	 */
	public static File getAlbumDir() {
		File dir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}
	/**
	 * {@link #TYPE_IMAGE}<br/>
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	private static String getPublicFilePath(int type) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			fileDir = MedicineApplication.getApplication().mVoicesDir;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			fileDir = MedicineApplication.getApplication().mVideosDir;
			fileSuffix = ".mp4";
			break;
		case TYPE_IMAGE:
			fileDir = MedicineApplication.getApplication().mPicturesDir;
			fileSuffix = ".jpg";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	/**
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	private static String getPrivateFilePath(int type, String userId) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			fileDir = MedicineApplication.getApplication().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			fileDir = MedicineApplication.getApplication().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES;
			fileSuffix = ".mp4";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	public static String getRandomImageFilePath() {
		return getPublicFilePath(TYPE_IMAGE);
	}

	public static String getRandomAudioFilePath() {
		User user = MedicineApplication.getApplication().mLoginUser;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			return getPrivateFilePath(TYPE_ADUIO, user.getUserId());
		} else {
			return getPublicFilePath(TYPE_ADUIO);
		}
	}

	public static String getRandomAudioAmrFilePath() {
		User user =  MedicineApplication.getApplication().mLoginUser;
		String filePath = null;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			filePath = getPrivateFilePath(TYPE_ADUIO, user.getUserId());
		} else {
			filePath = getPublicFilePath(TYPE_ADUIO);
		}
		if (!TextUtils.isEmpty(filePath)) {
			return filePath.replace(".mp3", ".amr");
		} else {
			return null;
		}
	}

	public static String getRandomVideoFilePath() {
		User user = MedicineApplication.getApplication().mLoginUser;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			return getPrivateFilePath(TYPE_VIDEO, user.getUserId());
		} else {
			return getPublicFilePath(TYPE_VIDEO);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	public static void createFileDir(String fileDir) {
		File fd = new File(fileDir);
		if (!fd.exists()) {
			fd.mkdirs();
		}
	}

	/**
	 * 
	 * @param fullName
	 */
	public static void delFile(String fullName) {
		File file = new File(fullName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 /sdcard/data/
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			System.out.println(path + tempList[i]);
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]); // 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如/sdcard/data/
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}
}
