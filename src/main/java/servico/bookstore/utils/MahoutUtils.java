package servico.bookstore.utils;

import dominio.Review;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.mahout.cf.taste.impl.model.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.*;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;

/**
 * Utility class for handling Mahout-based recommendations.
 */
public class MahoutUtils {

    /**
     * Creates a Mahout DataModel from a list of Reviews.
     *
     * @param reviews List of Review objects
     * @return DataModel instance ready for use in Mahout
     */
    public static DataModel createDataModelFromReviews(List<Review> reviews) {
        FastByIDMap<PreferenceArray> preferencesMap = new FastByIDMap<>();

        reviews.stream()
                .collect(Collectors.groupingBy(review -> review.getCustomer().getId()))
                .forEach((userId, userReviews) -> {
                    PreferenceArray preferenceArray = new GenericUserPreferenceArray(userReviews.size());
                    for (int i = 0; i < userReviews.size(); i++) {
                        Review review = userReviews.get(i);
                        preferenceArray.setUserID(i, userId);
                        preferenceArray.setItemID(i, review.getBook().getId());
                        preferenceArray.setValue(i, (float) review.getRating());
                    }
                    preferencesMap.put(userId, preferenceArray);
                });

        return new GenericDataModel(preferencesMap);
    }

    /**
     * Generates user-based recommendations.
     *
     * @param reviews   List of Review objects
     * @param userId    ID of the user to generate recommendations for
     * @param numRecs   Number of recommendations to generate
     * @return List of RecommendedItem objects
     */
    public static List<RecommendedItem> recommendUserBased(List<Review> reviews, int userId, int numRecs) {
        try {
            DataModel dataModel = createDataModelFromReviews(reviews);
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
            Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            return recommender.recommend(userId, numRecs);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Generates item-based recommendations.
     *
     * @param reviews   List of Review objects
     * @param userId    ID of the user to generate recommendations for
     * @param numRecs   Number of recommendations to generate
     * @return List of RecommendedItem objects
     */
    public static List<RecommendedItem> recommendItemBased(List<Review> reviews, int userId, int numRecs) {
        try {
            DataModel dataModel = createDataModelFromReviews(reviews);
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            Recommender recommender = new GenericItemBasedRecommender(dataModel, similarity);
            return recommender.recommend(userId, numRecs);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
