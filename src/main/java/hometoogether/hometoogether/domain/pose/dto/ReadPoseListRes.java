package hometoogether.hometoogether.domain.pose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadPoseListRes {
    private List<String> poseFileNameList;
}
