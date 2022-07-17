package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.*;
import hometoogether.hometoogether.domain.pose.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PoseService {

    private final KeypointsRepository keypointsRepository;
    private final ChallengePoseRepository challengePoseRepository;
    private final TrialPoseRepository trialPoseRepository;

//    public String test(String url) throws IOException {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Authorization", "KakaoAK 19a4097fe8917a985bb1a7acc9ce2fb1");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
////        String url = "https://preview.clipartkorea.co.kr/2016/05/27/ti325057081.jpg";
////        String url = "https://preview.clipartkorea.co.kr/2015/03/20/tip034z15020088.jpg";
////        String url = "http://preview.clipartkorea.co.kr/2016/05/27/ti325057171.jpg";
////        String url = "https://media.istockphoto.com/photos/looking-at-camera-front-view-full-length-one-person-of-2029-years-old-picture-id1182145935";
//
//        params.add("image_url", url);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://cv-api.kakaobrain.com/pose",
//                HttpMethod.POST,
//                entity,
//                String.class
//                );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonbody = response.getBody();
//        PoseInfo poseInfo = objectMapper.readValue(jsonbody, PoseInfo.class);
//        poseInfoRepository.save(poseInfo);
//
//        Pose pose = Pose.builder()
//                .url(url)
//                .poseInfo(poseInfo)
//                .build();
//        poseRepository.save(pose);
//
//        System.out.println("response = " + response);
//        return jsonbody;
//    }

    @Transactional
    @Async
    public void estimatePosePhoto(Long pose_id, String url, String pose_type) throws ParseException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK 19a4097fe8917a985bb1a7acc9ce2fb1");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("image_url", "http://221.143.144.143:80/"+url);
//        params.add("image_url", "http://58.122.7.167:9000/"+url);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://cv-api.kakaobrain.com/pose",
                HttpMethod.POST,
                entity,
                String.class
        );

        JSONParser jsonParse = new JSONParser();
        JSONArray jsonArr = (JSONArray) jsonParse.parse(response.getBody());
        JSONObject jsonObj = (JSONObject) jsonArr.get(0);
        JSONArray JSON_keypoints = (JSONArray) jsonObj.get("keypoints");
        List<Double> keypoints = (List<Double>) JSON_keypoints;
        Keypoints kp = Keypoints.builder()
                .keypoints(keypoints)
                .build();
        keypointsRepository.save(kp);

        if (pose_type == "challenge")
        {
            ChallengePose challengePose = challengePoseRepository.getById(pose_id);
            challengePose.addKeypoints(kp);
            challengePoseRepository.save(challengePose);
        }
        else if (pose_type == "trial"){
            TrialPose trialPose = trialPoseRepository.getById(pose_id);
            trialPose.addKeypoints(kp);
            trialPoseRepository.save(trialPose);
        }
        return;
    }

    @Transactional
    @Async
    public void estimatePoseVideo(Long pose_id, String url, String pose_type) throws ParseException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK e77f96acc7076a928b06d2fa9a480474");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("video_url", "http://221.143.144.143:80/"+url);
//        params.add("video_url", "http://58.122.7.167:9000/"+url);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://cv-api.kakaobrain.com/pose/job",
                HttpMethod.POST,
                entity,
                String.class
        );

        JSONParser jsonParse = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParse.parse(response.getBody());
        String job_id = (String) jsonObj.get("job_id");
        System.out.println("job_id = " + job_id);

        if (pose_type == "challenge")
        {
            System.out.println("I'm challenge\n");
            ChallengePose challengePose = challengePoseRepository.findById(pose_id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + pose_id));
            challengePose.setJob_id(job_id);
            challengePoseRepository.save(challengePose);
        }
        else if (pose_type == "trial"){
            System.out.println("I'm trial\n");
            TrialPose trialPose = trialPoseRepository.findById(pose_id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + pose_id));
            trialPose.setJob_id(job_id);
            trialPoseRepository.save(trialPose);
        }

        try {
            Thread.sleep(60000);
            estimatePoseDetailVideo(job_id, pose_id, pose_type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return;
    }

    @Transactional
    public void estimatePoseDetailVideo(String job_id, Long pose_id, String pose_type) throws ParseException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK e77f96acc7076a928b06d2fa9a480474");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("job_id", job_id);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://cv-api.kakaobrain.com/pose/job/"+job_id,
                HttpMethod.GET,
                entity,
                String.class
        );

        JSONParser jsonParse = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParse.parse(response.getBody());

        String status = (String) jsonObj.get("status");
        while ("success".equals(status) == false){
            Thread.sleep(60000);
            response = rt.exchange(
                    "https://cv-api.kakaobrain.com/pose/job/"+job_id,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            jsonObj = (JSONObject) jsonParse.parse(response.getBody());
            status = (String) jsonObj.get("status");
        }

        JSONArray jsonArr = (JSONArray) jsonObj.get("annotations");

        JSONObject example = (JSONObject) jsonArr.get(5);
        JSONArray example2 = (JSONArray) example.get("objects");
        JSONObject example3 = (JSONObject) example2.get(0);
        JSONArray example4 = (JSONArray) example3.get("keypoints");
        List<Double> example5 = (List<Double>) example4;
        System.out.println("example = " + example5);

        if ("challenge".equals(pose_type))
        {
            ChallengePose challengePose = challengePoseRepository.getById(pose_id);
            for (int i=0; i<jsonArr.size(); i++) {
                JSONObject jsonPart = (JSONObject) jsonArr.get(i);
                JSONArray objectArr = (JSONArray) jsonPart.get("objects");
                System.out.println("i = " + i);
                if (objectArr.size() != 0){
                    JSONObject object = (JSONObject) objectArr.get(0);
                    JSONArray JSON_keypoints = (JSONArray) object.get("keypoints");
                    List<Double> keypoints = (List<Double>) JSON_keypoints;
                    Keypoints kp = Keypoints.builder()
                            .keypoints(keypoints)
                            .build();
                    challengePose.addKeypoints(kp);
                    keypointsRepository.save(kp);
                }
            }
            challengePoseRepository.save(challengePose);
        }
        else if ("trial".equals(pose_type)){
            TrialPose trialPose = trialPoseRepository.getById(pose_id);
            for (int i=0; i<jsonArr.size(); i++) {
                JSONObject jsonPart = (JSONObject) jsonArr.get(i);
                JSONArray objectArr = (JSONArray) jsonPart.get("objects");
                System.out.println("i = " + i);
                if (objectArr.size() != 0){
                    JSONObject object = (JSONObject) objectArr.get(0);
                    JSONArray JSON_keypoints = (JSONArray) object.get("keypoints");
                    List<Double> keypoints = (List<Double>) JSON_keypoints;
                    Keypoints kp = Keypoints.builder()
                            .keypoints(keypoints)
                            .build();
                    trialPose.addKeypoints(kp);
                    keypointsRepository.save(kp);
                }
            }
            trialPoseRepository.save(trialPose);
        }

//        List<Keypoints> keypointsList = new ArrayList<Keypoints>();
//        for (int i=0; i<jsonArr.size(); i++) {
//            JSONObject jsonPart = (JSONObject) jsonArr.get(i);
//            JSONArray objectArr = (JSONArray) jsonPart.get("objects");
//            System.out.println("i = " + i);
//            if (objectArr.size() != 0){
//                JSONObject object = (JSONObject) objectArr.get(0);
//                JSONArray JSON_keypoints = (JSONArray) object.get("keypoints");
//                List<Double> keypoints = (List<Double>) JSON_keypoints;
//                Keypoints kp = Keypoints.builder()
//                        .keypoints(keypoints)
//                        .build();
//                keypointsRepository.save(kp);
//                keypointsList.add(kp);
//            }
//        }

        return;
    }

    public double estimateSimilarity(List<Double> pose1, List<Double> pose2){

//        List<Double> pose1 = poseInfoRepository.getById(6L).getKeypoints();
//        List<Double> pose2 = poseInfoRepository.getById(7L).getKeypoints();

        ArrayList<ArrayList<Double>> vectorInfo1 = vectorize(pose1);
        ArrayList<ArrayList<Double>> vectorInfo2 = vectorize(pose2);

        ArrayList<Double> vectorPose1XY = vectorInfo1.get(0);
        ArrayList<Double> vectorMinMax1 = vectorInfo1.get(1);
        ArrayList<Double> vectorPose1Scores = vectorInfo1.get(2);

        ArrayList<Double> vectorPose2XY = vectorInfo2.get(0);
        ArrayList<Double> vectorMinMax2 = vectorInfo2.get(1);
        ArrayList<Double> vectorPose2Scores = vectorInfo2.get(2);

        vectorPose1XY = sclaeAndtranslate(vectorPose1XY, vectorMinMax1);
        vectorPose2XY = sclaeAndtranslate(vectorPose2XY, vectorMinMax2);

        vectorPose1XY = L2Normalize(vectorPose1XY);
        vectorPose2XY = L2Normalize(vectorPose2XY);

        double cosineSimilarity = cosineSimilarity(vectorPose1XY, vectorPose2XY);

//        double cosineDistance = cosineDistanceMatching(vectorPose1XY, vectorPose2XY);

//        double weightedDistance = weightedDistanceMatching(vectorPose1XY, vectorPose2XY, vectorPose2Scores);

        return cosineSimilarity;
    }

    public ArrayList<ArrayList<Double>> vectorize(List<Double> pose) {

        ArrayList<ArrayList<Double>> vectorInfo = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> vectorposeXY = new ArrayList<>();
        ArrayList<Double> vectorMinMax = new ArrayList<>();
        ArrayList<Double> vectorposeScores = new ArrayList<>();

        double x_min = 10000;
        double y_min = 10000;
        double xy_max = -1;
        double score_sum = 0;

        for(int i = 0; i< pose.size(); i++){
            double item = pose.get(i);
            if (i%3 != 2){
                if (i%3 == 0) {
                    x_min = Math.min(item, x_min);
                }
                else {
                    y_min = Math.min(item, y_min);
                }
                xy_max = Math.max(item, xy_max);
                vectorposeXY.add(item);
            }
            else{
                score_sum += item;
                vectorposeScores.add(item);
            }
        }
        vectorposeScores.add(score_sum);

        vectorMinMax.add(x_min);
        vectorMinMax.add(y_min);
        vectorMinMax.add(xy_max);

        vectorInfo.add(vectorposeXY);
        vectorInfo.add(vectorMinMax);
        vectorInfo.add(vectorposeScores);

        return vectorInfo;
    }

    public ArrayList<Double> sclaeAndtranslate(ArrayList<Double> vectorPoseXY, ArrayList<Double> vectorMinMax) {

        for(int i = 0; i< vectorPoseXY.size(); i++){
            double item = vectorPoseXY.get(i);
            if (i%2 == 0){
                item -= vectorMinMax.get(0);
            }
            else {
                item -= vectorMinMax.get(1);
            }
            item /= vectorMinMax.get(2);
            vectorPoseXY.set(i, item);
        }

        return vectorPoseXY;
    }

    public ArrayList<Double> L2Normalize(ArrayList<Double> vectorPoseXY) {
        double absVectorPoseXY = 0;
        for (Double pos: vectorPoseXY) {
            absVectorPoseXY += Math.pow(pos, 2);
        }
        absVectorPoseXY = Math.sqrt(absVectorPoseXY);
        for (Double pos: vectorPoseXY) {
            pos /= absVectorPoseXY;
        }
        return vectorPoseXY;
    }

    public double cosineSimilarity(ArrayList<Double> vectorPose1XY, ArrayList<Double> vectorPose2XY) {
        double v1DotV2 = 0;
        double absV1 = 0;
        double absV2 = 0;
        double v1 = 0;
        double v2 = 0;

        for(int i = 0; i< vectorPose1XY.size(); i++){
            v1 = vectorPose1XY.get(i);
            v2 = vectorPose2XY.get(i);
            v1DotV2 += v1*v2;
            absV1 += v1*v1;
            absV2 += v2*v2;
        }

        absV1 = Math.sqrt(absV1);
        absV2 = Math.sqrt(absV2);

        return v1DotV2 / (absV1 * absV2);
    }

    public double cosineDistanceMatching(ArrayList<Double> vectorPose1XY, ArrayList<Double> vectorPose2XY) {
        double cosineSimilarity = cosineSimilarity(vectorPose1XY, vectorPose2XY);
        System.out.println("cosineSimilarity = " + cosineSimilarity);
        return Math.sqrt(2*(1-cosineSimilarity));
    }

    public double weightedDistanceMatching(ArrayList<Double> vectorPose1XY, ArrayList<Double> vectorPose2XY, ArrayList<Double> vectorPose2Scores) {

        double summation1 = 1 / vectorPose2Scores.get(vectorPose2Scores.size()-1);
        double summation2 = 0;

        for (int i = 0; i< vectorPose1XY.size(); i++){
            int confIndex = (int) Math.floor(i/2);
            summation2 = vectorPose2Scores.get(confIndex) * Math.abs(vectorPose1XY.get(i) - vectorPose2XY.get(i));
        }

        return summation1 * summation2;
    }

    public double DTWDistance(List<Keypoints> keypointsListA, List<Keypoints> keypointsListB){

        int lengthA = keypointsListA.size();
        int lengthB = keypointsListB.size();
        double[][] DTW = new double[lengthA+1][lengthB+1];

        for (int i = 0; i < lengthA+1; i++){
            for (int j = 0; j < lengthB+1; j++) {
                DTW[i][j] = 10000;
            }
        }
        DTW[0][0] = 0;

        double cost = 0;
        for (int i = 1; i < lengthA+1; i++){
            for (int j = 1; j < lengthB+1; j++) {
                ArrayList<Double> kpListA = new ArrayList<Double>();
                kpListA.addAll(keypointsListA.get(i-1).getKeypoints());
                ArrayList<Double> kpListB = new ArrayList<Double>();
                kpListB.addAll(keypointsListB.get(j-1).getKeypoints());
                cost = estimateSimilarity(kpListA , kpListB);
                if (DTW[i-1][j] == 10000 && DTW[i][j-1] == 10000){
                    DTW[i][j] = (cost + DTW[i-1][j-1]) / 2;
                } else if (DTW[i-1][j-1] == 10000 && DTW[i-1][j] == 10000){
                    DTW[i][j] = (cost + DTW[i][j-1]) / 2;
                } else if (DTW[i-1][j-1] == 10000 && DTW[i][j-1] == 10000){
                    DTW[i][j] = (cost + DTW[i-1][j]) / 2;
                } else {
                    DTW[i][j] = (cost + Math.max(Math.max(DTW[i-1][j], DTW[i][j-1]), DTW[i-1][j-1])) / 2;
                }
            }
        }

        return DTW[lengthA][lengthB];
    }
}