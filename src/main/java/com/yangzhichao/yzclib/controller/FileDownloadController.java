package com.yangzhichao.yzclib.controller;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/1/31
 * @class <code>FileDownloadController</code>
 * @see
 * @since JDK1.8
 */
@RestController

public class FileDownloadController {



        @RequestMapping("/download")
        public String download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
            String fileName = "IMG_7225.JPG";
            if (fileName != null){
/*
                TarUtils.archive(packPath, packPath + ApiConstant.DOT + ApiConstant.FILE_SUFFIX_TAR);
*/
                String realPath = "C:\\Users\\yangzhch\\Pictures";

                File file = new File(realPath,fileName);
                fileName = new String(file.getName().getBytes("utf-8"));
                String suffixNmae = fileName.substring(fileName.lastIndexOf("."));
                String name = UUID.randomUUID().toString();
                fileName = name + suffixNmae;
                if (file.exists()){
                    response.setContentType("application/force-download");
                    response.addHeader("Content-Disposition","attachment;fileName="+fileName);
                    byte[] buffer = new byte[1024];
                    FileInputStream fis = null;
                    BufferedInputStream bis = null;
                    try{
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        OutputStream os = response.getOutputStream();
                        int i = bis.read(buffer);
                        while(i != -1){
                            os.write(buffer,0,i);
                            i = bis.read(buffer);
                        }
                        System.out.println("success");
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (bis != null){
                            try{
                                bis.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        if (fis != null){
                            try{
                                fis.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            System.out.println(fileName);
            return fileName;

        }
    }
