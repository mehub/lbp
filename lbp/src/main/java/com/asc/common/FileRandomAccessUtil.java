/**
 * 
 */
package com.asc.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 类描述 .
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 下午3:22:38
 */
public class FileRandomAccessUtil {

	public static void autoCompleteSingleFieldWarp() {
		final int BUFFER_SIZE = 0x300000;// 缓冲区大小为3M
		File f = new File("E:\\a.txt");
		MappedByteBuffer inputBuffer;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "r");
			inputBuffer = raf.getChannel().map(FileChannel.MapMode.READ_ONLY,
					f.length() / 2, f.length() / 2);
			byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容
			long start = System.currentTimeMillis();
			for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
				if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
					for (int i = 0; i < BUFFER_SIZE; i++)
						dst[i] = inputBuffer.get(offset + i);
				} else {
					for (int i = 0; i < inputBuffer.capacity() - offset; i++)
						dst[i] = inputBuffer.get(offset + i);
				}
				int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
						: inputBuffer.capacity() % BUFFER_SIZE;
				System.out.println(new String(dst, 0, length));// new
			}
			long end = System.currentTimeMillis();
			System.out.println("读取文件文件一半内容花费：" + (end - start) + "毫秒");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null)
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
