package hometoogether.hometoogether.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    public String createFileURL(MultipartFile multipartFile) throws IOException {
        String url = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);
        return url;
    }

    public String createThumbnail(File source) throws IOException, JCodecException {
        String url = UUID.randomUUID().toString();
        Picture picture = FrameGrab.getFrameFromFile(source, 0);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, "jpg", new File(url));
        return url;
    }
}
