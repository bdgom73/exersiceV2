package app.workout.Service.FileUpload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Slf4j
@Component
@PropertySource("classpath:variable/files.properties")
public class FileUpload {

    @Value("${FILE_CLASSPATH}")
    private String CLASSPATH;

    /**
     * 아바타 이미지 파일 저장
     * @param file MultipartFile
     * */
    public String saveAvatar(MultipartFile file){
        String path = "/avatars";
        return fileSave(file, path);
    }


    /**
     * 운동 이미지 파일 저장
     * @param file MultipartFile
     * */
    public String saveWorkoutImage(MultipartFile file){
        String path = "/workouts";
        return fileSave(file, path);
    }

    /**
     * 파일저장
     * */
    private String fileSave(MultipartFile file, String path) {
        // 파일 이름 설정
        long date = System.currentTimeMillis();  // 현재시간 가져오기
        String rs = date + file.getName(); // 파일 이름

        // 실제 저장위치 설정
        StringBuilder sb = new StringBuilder();
        String ext = takeExtension(file.getOriginalFilename());  // 확장자 가져오기
        sb.append(CLASSPATH).append(path).append("/").append(date).append(rs).append(".").append(ext);
        String safe_pathname = sb.toString(); // 실제 저장될 위치

        // 저장될 위치 설정
        sb = new StringBuilder();
        sb.append(path).append("/").append(date).append(rs).append(".").append(ext);
        try {
            // 파일저장
            String save_pathname = sb.toString();
            file.transferTo(new File(safe_pathname));
            return save_pathname;
        }catch (Exception e) {
            log.error("FILE EXCEPTION e", e);
            return null;
        }
    }

    /**
     * 확장자 가져오기
     * @param fileName ex) file.png
     * @return if the filename is null, return 'png'
     * */
    public String takeExtension(String fileName){
        if(fileName == null) return "png";
        return fileName.split("[.]")[1];
    }

    /**
     * @return CLASSPATH
     * */
    public String getClasspath(){
        return CLASSPATH;
    }


}
