package com.example.demo.src.review;

import com.example.demo.src.review.model.GetMyReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMyReviewRes> getMyReview(int userId){
        String getMyReviewQuery ="select U.userId as userId,nickName,accName,round(((kind + clean + convenience +fitment)/4),1) as star,\n" +
                "       reviewText, reviewImageUrl, Review.createdAt as createdAt\n" +
                "from Review\n" +
                "join ReviewGrade RG on Review.grade = RG.reviewgrade\n" +
                "join ReviewImage RI on Review.reviewId = RI.reviewId\n" +
                "join Accommodation A on Review.accId = A.accId\n" +
                "join User U on Review.userId = U.userId\n" +
                "where U.userId = ?";
        int getUserIdParams = userId;
        return this.jdbcTemplate.query(getMyReviewQuery,
                (rs,rowNum) -> new GetMyReviewRes(
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getString("accName"),
                        rs.getInt("star"),
                        rs.getString("reviewText"),
                        rs.getString("reviewImageUrl"),
                        rs.getString("createdAt")),
                getUserIdParams);
    }
}
