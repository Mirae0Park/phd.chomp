package com.phd.chomp.service;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); // 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension; // UUID로 받은 값과 원래 파일이름의 확장자를 조합해서 저장될 파일 이름을 만듦
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        fos.write(fileData);
        fos.close();

        return savedFileName;

    }

    public String saveThumbnail(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        int thumbnailWidth = 800; // 원하는 가로 크기
        int thumbnailHeight = 800; // 원하는 세로 크기

        UUID uuid = UUID.randomUUID(); // 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String thumbnailFilename = uuid.toString() + extension; // UUID로 받은 값과 원래 파일이름의 확장자를 조합해서 저장될 파일 이름을 만듦
        String fileUploadFullUrl = uploadPath + "/" + thumbnailFilename;

        // 썸네일 생성
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(fileData));
        BufferedImage thumbnail = Thumbnails.of(originalImage)
                .outputFormat("png") // PNG 형식으로 저장
                .forceSize(thumbnailWidth, thumbnailHeight) // 이미지의 가로와 세로를 강제로 조정
                .asBufferedImage();

        // 썸네일을 파일로 저장
        try (FileOutputStream fos = new FileOutputStream(fileUploadFullUrl)) {
            ImageIO.write(thumbnail, "png", fos);
        }

        return thumbnailFilename;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
