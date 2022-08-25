package hometoogether.hometoogether.domain.pose.algorithm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.dto.KakaoPosePhotoRes;
import hometoogether.hometoogether.domain.pose.dto.kakaoApiType.Annotation;
import hometoogether.hometoogether.domain.pose.dto.kakaoApiType.Person;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SimilarityAlgorithm {

    private final ObjectMapper objectMapper;

    public Double runPhotoSimilarity(Pose pose1, Pose pose2) throws JsonProcessingException {
        KakaoPosePhotoRes poseA = objectMapper.readValue(pose1.getPoseDetail(), KakaoPosePhotoRes.class);
        KakaoPosePhotoRes poseB = objectMapper.readValue(pose2.getPoseDetail(), KakaoPosePhotoRes.class);
        return estimateSimilarity(poseA.getKeypoints(), poseB.getKeypoints());
    }

    public Double runVideoSimilarity(Pose pose1, Pose pose2) throws JsonProcessingException {
        List<Annotation> poseA = objectMapper.readValue(pose1.getPoseDetail(), new TypeReference<List<Annotation>>() {});
        List<Annotation> poseB = objectMapper.readValue(pose2.getPoseDetail(), new TypeReference<List<Annotation>>() {});
        return DTWDistance(poseA, poseB);
    }

    public double estimateSimilarity(float[] pose1, float[] pose2){

        ArrayList<ArrayList<Double>> vectorInfo1 = vectorize(pose1);
        ArrayList<ArrayList<Double>> vectorInfo2 = vectorize(pose2);

        ArrayList<Double> vectorPose1XY = vectorInfo1.get(0);
        ArrayList<Double> vectorMinMax1 = vectorInfo1.get(1);
        ArrayList<Double> vectorPose1Scores = vectorInfo1.get(2);

        ArrayList<Double> vectorPose2XY = vectorInfo2.get(0);
        ArrayList<Double> vectorMinMax2 = vectorInfo2.get(1);
        ArrayList<Double> vectorPose2Scores = vectorInfo2.get(2);

        vectorPose1XY = scaleAndTranslate(vectorPose1XY, vectorMinMax1);
        vectorPose2XY = scaleAndTranslate(vectorPose2XY, vectorMinMax2);

        vectorPose1XY = L2Normalize(vectorPose1XY);
        vectorPose2XY = L2Normalize(vectorPose2XY);

//        double cosineSimilarity = cosineSimilarity(vectorPose1XY, vectorPose2XY);

//        double cosineDistance = cosineDistanceMatching(vectorPose1XY, vectorPose2XY);

        double weightedDistance = weightedDistanceMatching(vectorPose1XY, vectorPose2XY, vectorPose2Scores);

        return weightedDistance;
    }

    public ArrayList<ArrayList<Double>> vectorize(float[] pose) {

        ArrayList<ArrayList<Double>> vectorInfo = new ArrayList<>();
        ArrayList<Double> vectorPoseXY = new ArrayList<>();
        ArrayList<Double> vectorMinMax = new ArrayList<>();
        ArrayList<Double> vectorPoseScores = new ArrayList<>();

        double x_min = 10000;
        double y_min = 10000;
        double xy_max = -1;
        double score_sum = 0;

        for(int i = 0; i< pose.length; i++){
            double item = pose[i];
            if (i%3 != 2){
                if (i%3 == 0) {
                    x_min = Math.min(item, x_min);
                }
                else {
                    y_min = Math.min(item, y_min);
                }
                xy_max = Math.max(item, xy_max);
                vectorPoseXY.add(item);
            }
            else{
                score_sum += item;
                vectorPoseScores.add(item);
            }
        }
        vectorPoseScores.add(score_sum);

        vectorMinMax.add(x_min);
        vectorMinMax.add(y_min);
        vectorMinMax.add(xy_max);

        vectorInfo.add(vectorPoseXY);
        vectorInfo.add(vectorMinMax);
        vectorInfo.add(vectorPoseScores);

        return vectorInfo;
    }

    public ArrayList<Double> scaleAndTranslate(ArrayList<Double> vectorPoseXY, ArrayList<Double> vectorMinMax) {

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

    public double DTWDistance(List<Annotation> keypointsListA, List<Annotation> keypointsListB){

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
                Person[] objectsA = keypointsListA.get(i - 1).getObjects();
                float[] keypointsA = objectsA[0].getKeypoints();
                Person[] objectsB = keypointsListB.get(j - 1).getObjects();
                float[] keypoints = objectsB[0].getKeypoints();
                cost = estimateSimilarity(keypointsA , keypoints);
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
