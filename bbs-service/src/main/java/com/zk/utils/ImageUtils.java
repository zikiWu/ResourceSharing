package com.zk.utils;

import com.zk.po.utils.Constants;
import com.zk.po.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.coobird.thumbnailator.Thumbnails;


public class ImageUtils {

	private static final String[] static_ext = {"jpg", "png", "gif", "bmp", "JPG", "PNG", "GIF", "BMP"};
	/**创建缩图
	* @author wzk
	* @date 2019/3/18
	* @param
	*/
	public static String createThumbnail(String topicImage, boolean isFullPath) {

		StringBuilder topicImageSmall = new StringBuilder();
		if (!StringUtils.isEmpty(topicImage)) {
			String realPath = ServerUtils.getRealPath();
			String[] topicImaes = topicImage.split("\\|");
			int smallCount = topicImaes.length;
			if (smallCount > Constants.MAX_FILE_NUM) {
				smallCount = Constants.MAX_FILE_NUM;
			}
			for (int i = 0; i < smallCount; i++) {
				String img = topicImaes[i];
				if (isFullPath) {
					img = img.substring(img.indexOf("upload"));
				}
				String sourcePath = realPath + "/upload/" + img;
				String smallSavePath = sourcePath + "_s.jpg";
				String smallPath = img + "_s.jpg";
				//写入缩图
				try {
					BufferedImage src = ImageIO.read(new File(sourcePath));
					if (src.getWidth() <= Constants.SMALL_IMAGE_WIDTH) {
						continue;
					}
					Thumbnails
							.of(sourcePath)
							.size(Constants.SMALL_IMAGE_WIDTH,
									Constants.SMALL_IMAGE_HEIGHT)
							.toFile(smallSavePath); 
//					CompressPic compressPic = new CompressPic(sourcePath, smallSavePath, Constants.SMALL_IMAGE_WIDTH, Constants.SMALL_IMAGE_HEIGHT);
//					// 缩放图片
//					compressPic.compressPic();
				} catch (Exception e) {
					
				}
				topicImageSmall.append(smallPath).append("|");
			}

		}
		return topicImageSmall.toString();
	}
	/**从html获取图片
	* @author wzk
	* @date 2019/3/18
	* @param
	*/
	public static String getImages(String content) {
		//content = HtmlUtils.htmlUnescape(content);
		StringBuilder sbf = new StringBuilder();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		List<TagNode> nodeList = (List<TagNode>) allNode.getElementListByName("img", true);
		String image = "";
		if (nodeList != null) {
			for (TagNode node : nodeList) {
				image = String.valueOf(node.getAttributeByName("src")).trim();
				if (!image.contains("emotion")
						&& !image.contains("grey.gif")
						&& image.contains(".")
						&& ArrayUtils.contains(static_ext,
								image.substring(image.lastIndexOf(".") + 1))) {
					sbf.append(image + "|");
				}
			}
		}
		if (sbf.length() > 0) {
			sbf.substring(sbf.lastIndexOf("|"));
		}
		return sbf.toString();
	}
	public static void main(String[] args) throws IOException {
		System.out.println(getImages("&lt;p&gt;&lt;img src=&quot;http://localhost:8091/upload/201608/1470326891368.png&quot;/&gt;GOOD&lt;/p&gt;"));
	}
}