package com.zhs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/18 17:04
 * @package: com.zhs.utils
 * @description:  文件转换工具类
 */
public class FileUtils {
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);


    /**
     * 附件上传
     * @author yzl
     * @param request
     * @param uploadfile 上传文件
     * @param filespath 文件上传的绝对路径
     * @return
     */
    public static Map<String,Object> fileupload(HttpServletRequest request, MultipartFile uploadfile, String filespath) {
        Map<String,Object> map = new HashMap<String, Object>();//初始化返回集合
        //获取服务器指定绝对路径
        //String filespath = request.getSession().getServletContext().getRealPath("/WEB-INF/files");
        //String filespath = "C:\\environment\\files\\images";
        //String filespath = "C:\\environment";
        //判断是否已存在/WEB-INF/files目录，不存在则创建
        File file = new File(filespath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        //取当前日期,在files文件夹下创建按日期分类文件夹/WEB-INF/files/2015-10-14
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDay = sdf.format(new Date(System.currentTimeMillis()));
        String daypath = filespath+"\\"+curDay+"\\";
        File file2 = new File(daypath);
        if (!file2.exists() && !file2.isDirectory()) {
            file2.mkdir();
        }
        /** 解析附件 */
        //获取文本类型
        String contentType = uploadfile.getContentType();
        //获取文件名
        String oldFileName = uploadfile.getOriginalFilename();
        //获取文件后缀名
        //获取文件后缀名
        String ext = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toUpperCase();
        //命名规则：原文件名+日期+系统毫秒数+后缀
        String newFileName = oldFileName.substring(0,oldFileName.lastIndexOf("."))+curDay+"_"+System.currentTimeMillis()+"."+ext;
        String newPath = daypath+newFileName;
        try {
            uploadfile.transferTo(new File(newPath));
            LOGGER.info("###################################################################文件上传成功！上传服务器绝对路径："+newPath);
        } catch (Exception e) {
            LOGGER.error("###################################################################上传失败！");
            e.printStackTrace();
        }
        map.put("filename", newFileName.substring(0,newFileName.lastIndexOf(".")));
        map.put("url", newPath);
        map.put("contentType", contentType);
        map.put("fullfilename", newFileName);
        return map;
    }

    /**
     * 创建空文件
     *
     * @param fullFileName 完整文件名
     * @throws IOException
     */
    public static boolean newFile(String fullFileName) throws IOException {
        return newFile(fullFileName, null);
    }

    /**
     * 创建文件并写入字符串
     *
     * @param fullFileName 完整文件名
     * @param content 内容
     * @throws IOException
     */
    public static boolean newFile(String fullFileName, String content) throws IOException {
        File file = new File(fullFileName);
        if (file.exists()) {
            return false;
        } else {
            makeDirectory(file.getParentFile());
            file.createNewFile();
        }
        return writeFile(file, content);
    }

    /**
     * @param file
     * @param content
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    private static boolean writeFile(File file, String content) throws IOException {
        if (null == content) {
            return true;
        } else {
            FileWriter fw = null;
            PrintWriter pw = null;
            boolean result = false;
            try {
                fw = new FileWriter(file);
                pw = new PrintWriter(fw);
                pw.print(content);
                result = true;
            } finally {
                finallyColse(pw, fw);
                return result;
            }
        }
    }

    /**
     * 写入字符串到文件
     *
     * @param fullFileName 完整文件名
     * @param content 内容
     * @param append 是否追加
     * @throws IOException
     */
    public static void writeFile(String fullFileName, String content, boolean append) throws IOException {
        File file = new File(fullFileName);
        if (append) {
            writeFile(file, content);
        } else {
            delete(fullFileName);
            newFile(fullFileName, content);
        }
    }

    /**
     * 读取文件，把文件内容转换为字符串（读取超大文件要慎重）
     *
     * @param fullFileName
     * @return 文件内容字符串
     * @throws IOException
     */
    public static String readFile(String fullFileName) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader(fullFileName);
            File myfile = new File(fullFileName);
            int fileLen = (int) myfile.length();
            char[] buf = new char[fileLen];
            int len = fr.read(buf);
            String msg = new String(buf, 0, len);
            return msg;
        } finally {
            finallyColse(fr);
        }
    }

    /**
     * 按行读取文件<br>
     * 一行对应集合中一条记录
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static List<String> readFileByLine(String path) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            List<String> list = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        } finally {
            finallyColse(br, fr);
        }
    }

    /**
     * 按UTF-8编码读取内容 〈功能详细描述〉
     *
     * @param path 文件路径
     * @return 文件内容的UTF8格式
     * @throws IOException
     */
    public static String readFileByUTF8(String path) throws IOException {
        StringBuffer content = new StringBuffer();
        File f = new File(path);
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(f), "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\r\n");
            }
        } finally {
            finallyColse(bufferedReader, inputStreamReader);
        }
        return content.toString();
    }

    /**
     * 按utf-8编码保存文件 〈功能详细描述〉
     *
     * @param path
     * @param content
     * @throws IOException
     */
    public static void saveFileByUTF8(String path, String content) throws IOException {
        File myfile = new File(path);
        myfile.delete();
        myfile.createNewFile();

        FileOutputStream fos = null;
        Writer w = null;
        try {
            fos = new FileOutputStream(myfile.getAbsoluteFile());
            w = new OutputStreamWriter(fos, "UTF-8");
            w.write(content);
        } finally {
            finallyColse(w, fos);
        }
    }

    /**
     * 新建目录 (支持创建多层目录) 〈功能详细描述〉
     *
     * @param path
     * @throws IOException
     */
    public static void newFolder(String path) throws IOException {
        File mydir = new File(path);
        if (!mydir.exists())
            mydir.mkdirs();
    }

    /**
     * 字符串转换到流
     *
     * @param srcStr 源字符串
     * @return 输入流
     */
    public static InputStream string2Stream(final String srcStr) {
        InputStream is = null;
        if (StringUtils.isEmpty(srcStr)) {
            is = new ByteArrayInputStream(srcStr.getBytes());
        }
        return is;
    }

    /**
     * 流转换到字符串
     *
     * @param srcStream 源输入流
     * @return 字符串
     * @throws IOException
     */
    public static String stream2String(InputStream srcStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(srcStream));
        StringBuffer buf = new StringBuffer();
        String line = null;
        while (null != (line = br.readLine())) {
            buf.append(line).append("\r\n");
        }
        return buf.toString();
    }

    /**
     * 把文件内容转换为流
     *
     * @param fullFileName 完整文件名
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getStreamInFile(final String fullFileName) throws IOException {
        return new FileInputStream(fullFileName);
    }

    /**
     * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定， 主要是方法有些信息输出，这些信息输出是否保留还在考
     *
     * 虑中。</b>
     *
     * @param file 需要修改最后访问时间的文件。
     * @since 0.1
     */
    public static void touch(File file) {
        long currentTime = System.currentTimeMillis();
        if (!file.exists()) {
            System.err.println("file not found:" + file.getName());
            System.err.println("Create a new file:" + file.getName());
            try {
                if (file.createNewFile()) {
                    // System.out.println("Succeeded!");
                } else {
                    // System.err.println("Create file failed!");
                }
            } catch (IOException ex) {
                LOGGER.error("修改文件最后修改时间失败", ex);
            }
        }
        boolean result = file.setLastModified(currentTime);
        if (!result) {
            // System.err.println("touch failed: " + file.getName());
        }
    }

    /**
     * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定， 主要是方法有些信息输出，这些信息输出是否保留还在考
     *
     * 虑中。</b>
     *
     * @param fileName 需要修改最后访问时间的文件的文件名。
     * @since 0.1
     */
    public static void touch(String fileName) {
        File file = new File(fileName);
        touch(file);
    }

    /**
     * 判断指定的文件是否存在。
     *
     * @param fullFileName 要判断的文件的文件名
     * @return 存在时返回true，否则返回false。
     */
    public static boolean isFileExist(String fullFileName) {
        return isFileExist(new File(fullFileName));
    }

    /**
     * 判断指定的文件是否存在。
     *
     * @param file 要判断的文件
     * @return 存在时返回true，否则返回false。
     */
    public static boolean isFileExist(File file) {
        return file.exists();
    }

    /**
     * 判断指定的文件是否为文件。
     *
     * @param fullFileName 要判断的文件的文件名
     * @return 是文件时返回true，不是文件时返回false。
     */
    public static boolean isFile(String fullFileName) {
        return isFile(new File(fullFileName));
    }

    /**
     * 判断指定的文件是否为文件。
     *
     * @param file 要判断的文件
     * @return 是文件时返回true，不是文件时返回false。
     */
    public static boolean isFile(File file) {
        return file.isFile();
    }

    /**
     * 判断指定的文件是否为目录（文件夹）。
     *
     * @param fullFileName 要判断的文件的文件名
     * @return 是文件夹时返回true，不是文件夹时返回false。
     */
    public static boolean isDirectory(String fullFileName) {
        return isDirectory(new File(fullFileName));
    }

    /**
     * 判断指定的文件是否为目录（文件夹）。
     *
     * @param file 要判断的文件
     * @return 是文件夹时返回true，不是文件夹时返回false。
     */
    public static boolean isDirectory(File file) {
        return file.isDirectory();
    }

    /**
     * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
     *
     * @param directory 要创建的目录
     * @return 完全创建成功时返回true，否则返回false。
     * @since 0.1
     */
    public static boolean makeDirectory(File directory) {
        return directory.mkdirs();
    }

    /**
     * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考 虑中。</b>
     *
     * @param files 需要修改最后访问时间的文件数组。
     * @since 0.1
     */
    public static void touch(File[] files) {
        for (int i = 0; i < files.length; i++) {
            touch(files[i]);
        }
    }

    /**
     * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
     *
     * @param fullFileName 要创建的目录的目录名
     * @return 完全创建成功时返回true，否则返回false。
     * @since 0.1
     */
    public static boolean makeDirectory(String fullFileName) {
        File file = new File(fullFileName);
        return makeDirectory(file);
    }

    /**
     * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。 另外这个方法不会迭代删除，即不会删除子目录及其内容。
     *
     * @param directory 要清空的目录
     * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
     * @since 0.1
     */
    public static boolean emptyDirectory(File directory) {
        boolean result = false;
        File[] entries = directory.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (!entries[i].delete()) {
                return result;
            }
        }
        return true;
    }

    /**
     * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。 另外这个方法不会迭代删除，即不会删除子目录及其内容。
     *
     * @param fullDirectoryName 要清空的目录的目录名
     * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
     * @since 0.1
     */
    public static boolean emptyDirectory(String fullDirectoryName) {
        File directory = new File(fullDirectoryName);
        return emptyDirectory(directory);
    }

    /**
     * 将文件内容转换成字符串返回
     *
     * @param fullFileName 读取的文件
     * @return 字符串
     * @throws FileNotFoundException
     */
    public static String readByLines(String fullFileName) throws FileNotFoundException {
        return readByLines(new FileInputStream(fullFileName));
    }

    /**
     * 将文件内容转换成字符串返回
     *
     * @param file 读取的文件
     * @return 字符串
     * @throws FileNotFoundException
     */
    public static String readByLines(File file) throws FileNotFoundException {
        return readByLines(new FileInputStream(file));
    }

    /**
     * 将字节流转换成字符串返回
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String readByLines(InputStream is) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            finallyColse(reader);
        }
        return sb.toString();
    }

    /**
     * 将文件一行一行的读成List返回
     *
     * @param fullFileName 需要读取的文件
     * @return 文件的一行就是一个List的Item的返回
     * @throws FileNotFoundException
     */
    public static List<String> readToList(String fullFileName) throws FileNotFoundException {
        return readToList(fullFileName, null);
    }

    /**
     * 将文件按照一定的编码方式一行一行的读成List返回
     *
     * @param fullFileName 需要读取的文件
     * @param encodType 字符编码
     * @return 文件的一行就是一个List的Item的返回
     * @throws FileNotFoundException
     */
    public static List<String> readToList(String fullFileName, String encodType) throws FileNotFoundException {
        return readToList(new FileInputStream(fullFileName), encodType);
    }

    /**
     * 将文件一行一行的读成List返回
     *
     * @param file 需要读取的文件
     * @return 文件的一行就是一个List的Item的返回
     * @throws FileNotFoundException
     */
    public static List<String> readToList(File file) throws FileNotFoundException {
        return readToList(file, null);
    }

    /**
     * 将文件按照一定的编码方式一行一行的读成List返回
     *
     * @param file 需要读取的文件
     * @param encodType 字符编码
     * @return 文件的一行就是一个List的Item的返回
     * @throws FileNotFoundException
     */
    public static List<String> readToList(File file, String encodType) throws FileNotFoundException {
        return readToList(new FileInputStream(file), encodType);
    }

    /**
     * 将文件按照一定的编码方式一行一行的读成List返回
     *
     * @param is 输入流
     * @param encodType 字符编码
     * @return 文件的一行就是一个List的Item的返回
     */
    public static List<String> readToList(InputStream is, String encodType) {
        BufferedReader reader = null;
        List<String> list = new ArrayList<String>();
        try {
            if (null != encodType) {
                reader = new BufferedReader(new InputStreamReader(is, encodType));
            } else {
                reader = new BufferedReader(new InputStreamReader(is));
            }
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if (!(tempString.charAt(0) >= 'a' && tempString.charAt(0) <= 'z'))
                    tempString = tempString.substring(1);
                list.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            finallyColse(reader);
        }
        return list;
    }

    /**
     * 将指定的字符串内容以指定的方式写入到指定的文件中
     *
     * @param file 需要写人的文件
     * @param content 需要写入的内容
     * @param flag 是否追加写入
     */
    public static void writeFile(File file, String content, Boolean flag) {
        FileWriter writer = null;
        try {
            if (!file.exists())
                file.createNewFile();
            writer = new FileWriter(file, flag);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            finallyColse(writer);
        }
    }

    /**
     * 将指定的字符串内容以指定的方式及编码写入到指定的文件中
     *
     * @param file 需要写人的文件
     * @param content 需要写入的内容
     * @param flag 是否追加写入
     * @param encodType 文件编码
     */
    public static void writeFile(File file, String content, Boolean flag, String encodType) {
        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            fos = new FileOutputStream(file, flag);
            writer = new BufferedWriter(new OutputStreamWriter(fos, encodType));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyColse(writer, fos);
        }
    }

    /**
     * 读取文件，内容存放为byte数组
     *
     * @param fullFileName 完整文件名
     * @return byte数组
     */
    public static byte[] readFile2ByteArray(String fullFileName) {
        return readFile2ByteArray(new File(fullFileName));
    }

    /**
     * 读取文件，内容存放为byte数组
     *
     * @param file 待处理文件
     * @return byte数组
     */
    public static byte[] readFile2ByteArray(File file) {
        byte[] byteArray = null;
        if (isFileExist(file) && isFile(file)) {
            FileInputStream fis = null;
            FileChannel fileChannel = null;
            try {
                fis = new FileInputStream(file);
                int fileSize = fis.available();
                fileChannel = fis.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
                if (-1 != fileChannel.read(byteBuffer)) {
                    byteArray = byteBuffer.array();
                }
            } catch (Exception ex) {
                LOGGER.error("读取文件为byte数组失败", ex);
            } finally {
                finallyColse(fileChannel, fis);
            }
        }
        return byteArray;
    }

    /**
     * 拷贝文件夹
     *
     * @param oldPath 源目录
     * @param newPath 目标目录
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = null;
                    FileOutputStream output = null;
                    try {
                        input = new FileInputStream(temp);
                        output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                        byte[] b = new byte[1024 * 5];
                        int len;
                        while ((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }
                        output.flush();
                    } finally {
                        finallyColse(output, input);
                    }
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件重命名
     *
     * @param oldName 源文件名
     * @param newName 新文件名
     */
    public static void rename(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        oldFile.renameTo(newFile);
    }

    /**
     * 将一个文件列表文件中所有文件拷贝到指定目录中
     *
     * @param listFile 包含需要拷贝的文件的列表的文件，每个文件写在一行
     * @param targetFloder 目标目录
     */
    public static void copyFilesFromList(String listFile, String targetFloder) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(listFile));
            String tempString = null;
            while (null != (tempString = reader.readLine())) {
                copyFile(tempString, targetFloder);
            }
        } catch (IOException ex) {
            LOGGER.error("拷贝发生错误", ex);
        } finally {
            finallyColse(reader);
        }
    }

    /**
     * 拷贝文件
     *
     * @param oldPath 源文件
     * @param newPath 目标文件
     */
    public static void copyFile(String oldPath, String newPath) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            File temp = new File(oldPath);
            input = new FileInputStream(temp);
            output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyColse(output, input);
        }
    }

    /**
     * 删除文件列表
     *
     * @param files 需要删除的文件/文件夹列表
     * @return 删除成功true，否则返回false
     */
    public static boolean deleteFiles(List<String> files) {
        boolean flag = true;
        for (String file : files) {
            flag = delete(file);
            if(!flag){
                break;
            }

        }
        return flag;
    }

    /**
     * 删除文件或文件夹
     *
     * @param fullFileName 要删除的文件名或文件夹名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fullFileName) {
        File file = new File(fullFileName);
        if (file.exists()) {
            if (file.isFile()) {
                return deleteFile(fullFileName);
            } else {
                return deleteDirectory(fullFileName);
            }
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile())
            return file.delete();
        return false;
    }

    /**
     * 删除文件
     *
     * @param file 要删除的文件
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除目录 <br>
     * 删除操作，删除目录下内容及目录本身
     *
     * @param dir 要删除的目录路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        return deleteDirectory(dir, true);
    }

    /**
     * 删除目录
     *
     * @param dir 要删除的目录路径
     * @param delete 是否为删除操作<li>true--删除操作，删除目录下内容及目录本身 <li>
     *            false--清空操作，只删除目录下内容，保留目录本身
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir, boolean delete) {
        File dirFile = new File(dir);
        return deleteDirectory(dirFile, delete);
    }

    /**
     * 删除指定目录 <br>
     * 删除操作，删除目录下内容及目录本身 <li>
     *
     * @param dir 要删除的目录
     * @return 删除成功时返回true，否则返回false。
     */
    public static boolean deleteDirectory(File dir) {
        return deleteDirectory(dir, true);
    }

    /**
     * 删除指定目录及其中的所有内容。
     *
     * @param dir 要删除的目录
     * @param delete 是否为删除操作<li>true--删除操作，删除目录下内容及目录本身 <li>
     *            false--清空操作，只删除目录下内容，保留目录本身
     * @return 删除成功时返回true，否则返回false。
     * @since 0.1
     */
    public static boolean deleteDirectory(File dir, boolean delete) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir + " is not a directory. ");
        }
        File[] entries = dir.listFiles();
        for (File file : entries) {
            if (file.isDirectory()) {
                if (!deleteDirectory(file)) {
                    return false;
                }
            } else {
                if (!deleteFile(file)) {
                    return false;
                }
            }
        }
        if (delete) {
            return dir.delete();
        } else {
            return true;
        }
    }

    /**
     * 返回文件的URL地址。
     *
     * @param file 文件
     * @return 文件对应的的URL地址
     * @throws MalformedURLException
     */
    public static URL getURL(File file) throws MalformedURLException {
        if (null == file) {
            return null;
        } else {
            return file.toURI().toURL();
        }
    }

    /**
     * 从文件路径得到文件名。
     *
     * @param filePath 文件的路径，可以是相对路径也可以是绝对路径
     * @return 对应的文件名
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * 从文件名得到文件绝对路径。
     *
     * @param fileName 文件名
     * @return 对应的文件路径
     * @since 0.4
     */
    public static String getFilePath(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }

    /**
     * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
     * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
     *
     * @param filePath 转换前的路径
     * @return 转换后的路径
     */
    public static String toUNIXpath(String filePath) {
        return filePath.replace('\\', '/');
    }

    /**
     * 从文件名得到UNIX风格的文件绝对路径。
     *
     * @param fileName 文件名
     * @return 对应的UNIX风格的文件路径
     * @since 0.4
     * @see #toUNIXpath(String filePath) toUNIXpath
     */
    public static String getUNIXfilePath(String fileName) {
        File file = new File(fileName);
        return toUNIXpath(file.getAbsolutePath());
    }

    /**
     * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * @param fileName 文件名
     * @return 文件名中的类型部分
     * @since 0.5
     */
    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * @param file 文件
     * @return 文件名中的类型部分
     * @since 0.5
     */
    public static String getFileType(File file) {
        return getTypePart(file.getName());
    }

    /**
     * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
     *
     * @param fileName 文件名
     * @return 文件名中的名字部分
     * @since 0.5
     */
    public static String getNamePart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                } else {
                    return fileName.substring(0, point);
                }
            } else {
                return fileName.substring(secondPoint + 1, point);
            }
        } else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
     *
     * @param fileName 文件名
     * @return 父路径，不存在或者已经是父目录时返回""
     */
    public static String getPathPart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return "";
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                return "";
            } else {
                return fileName.substring(0, secondPoint);
            }
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
     */
    public static int getPathIndex(String fileName) {
        int point = fileName.indexOf('/');
        if (point == -1) {
            point = fileName.indexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
     */
    public static int getPathIndex(String fileName, int fromIndex) {
        int point = fileName.indexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.indexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
     */
    public static int getPathLsatIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
     */
    public static int getPathLsatIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 将文件名中的类型部分去掉。
     *
     * @param filename 文件名
     * @return 去掉类型部分的结果
     * @since 0.5
     */
    public static String trimType(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    /**
     * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
     *
     * @param pathName 目录名
     * @param fileName 文件名
     * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
     * @since 0.5
     */
    public static String getSubpath(String pathName, String fileName) {
        int index = fileName.indexOf(pathName);
        if (index != -1) {
            return fileName.substring(index + pathName.length() + 1);
        } else {
            return fileName;
        }
    }

    /**
     * 最终关闭资源
     *
     * @param closeables 可关闭对象列表
     */
    private static void finallyColse(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (null != closeable) {
                try {
                    closeable.close();
                } catch (Exception ex) {
                    LOGGER.warn("关闭资源发成异常", ex);
                }
            }
        }
    }
}



