package hometoogether.hometoogether.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.transaction.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@Transactional
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    void S3_이미지_업로드() throws IOException {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.png", "image/png", new FileInputStream("C:\\Users\\rlacksgus\\Desktop\\1.jpg"));

        //when
        String s3fileName = fileService.upload(multipartFile);

        //then
        Assertions.assertThat(s3fileName.contains("originalFileName.png"));
    }

    @Test
    void S3_동영상_업로드() throws IOException {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.mp4", "video/mp4", new FileInputStream("C:\\Users\\rlacksgus\\Desktop\\1번자세.mp4"));

        //when
        String s3fileName = fileService.upload(multipartFile);

        //then
        Assertions.assertThat(s3fileName.contains("originalFileName.mp4"));
    }
}
