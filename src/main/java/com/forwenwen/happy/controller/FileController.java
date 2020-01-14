package com.forwenwen.happy.controller;

import com.forwenwen.happy.pojo.Message;
import com.forwenwen.happy.repository.FileRepository;

import com.sun.xml.internal.ws.handler.MessageUpdatableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    FileRepository fileRepository;

    /**
     * 上传文件接口
     * @param request
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public Message uploadTheme(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file){
        Message message=new Message(request);
        try {
            // 文件上传的路径
            String path=request.getSession().getServletContext().getRealPath("upload");
            File newfile=new File(path);
            if(!newfile.exists()){
                newfile.mkdirs();
                newfile.setWritable(true);
            }
            String fileName = file.getOriginalFilename();
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            StringBuffer stringBuffer=new StringBuffer();
            Long time=System.currentTimeMillis()/1000;
            stringBuffer.append(time);
            stringBuffer.append(".");
            stringBuffer.append(fileExtensionName);
            String savedFileName = stringBuffer.toString();
            File pathfile=new File(path, savedFileName);
            file.transferTo(pathfile);

            com.forwenwen.happy.domain.File tempFile = new com.forwenwen.happy.domain.File();
            tempFile.setFileAddress("upload/" + savedFileName);
            tempFile.setFileSaveName(savedFileName);
            tempFile.setFileUploadName(file.getOriginalFilename());
            fileRepository.save(tempFile);

            message.setSuccess("上传成功","upload" + "/" + file.getOriginalFilename());
        }catch (Exception e){
            message.setError("上传失败");
        }
        return message;
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param fileId
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public Message uploadTheme(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileId") String fileId) throws IOException {
        Message message=new Message(request);

        Optional<com.forwenwen.happy.domain.File> optionalFile =  fileRepository.findById(fileId);
        boolean optionalFileIsOrNotExist = optionalFile.isPresent();
        if(optionalFileIsOrNotExist) {
            com.forwenwen.happy.domain.File downloadingFile = optionalFile.get();
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            try {
                File file = new File(request.getSession().getServletContext().getRealPath("upload") + "\\" + downloadingFile.getFileSaveName());
                in = new BufferedInputStream(new FileInputStream(file));
                out = new BufferedOutputStream(response.getOutputStream());
                response.setContentType(new MimetypesFileTypeMap().getContentType(file));
                response.setHeader("Content-disposition", "attachment;filename=" + downloadingFile.getFileUploadName());
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.flush();
                message.setSuccess("下载成功");
            } catch(IOException e) {
                message.setError("下载失败");
                e.getStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            }
            return message;
        } else {
            message.setError("下载失败");
            return message;
        }
    }

    /**
     * 得到所有的文件
     * @param request
     * @return
     */
    @RequestMapping("/getallfile")
    public Message getAllFile(HttpServletRequest request){
        Message message=new Message(request);
        try {
            List<com.forwenwen.happy.domain.File> fileList = fileRepository.findAll();
            message.setSuccess("获取成功", fileList);
        }catch (Exception e){
            message.setError("获取失败");
        }
        return message;
    }

    @RequestMapping("/deletefile")
    public Message deleteFile(HttpServletRequest request, @RequestParam(value = "fileId") String fileId){
        Message message=new Message(request);
        try {
            fileRepository.deleteById(fileId);
            message.setSuccess("删除成功");
        }catch (Exception e){
            message.setError("删除失败");
        }
        return message;
    }
}
