package com.example.snsproject.domain.post.repository;

import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.entity.Post;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PostRepository {

  private static final String TABLE = "post";
  private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (rs, rowNum) ->
      new DailyPostCount(
          rs.getLong("memberId"),
          rs.getDate("createDate").toLocalDate(),
          rs.getLong("count(id)")
      );

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public PostRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Transactional
  public Post save(final Post post) {
    if (post.getId() == null) {
      return insert(post);
    }

    throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
  }

  // TODO : 쿼리 성능개선 문제 해결해보기
  @Transactional(readOnly = true)
  public List<DailyPostCount> groupByCreatedDate(
      final DailyPostCountRequest dailyPostCountRequest) {
    String sql = String.format(
        "select memberId, createDate, count(id) "
            + "from %s "
            + "where memberId = :memberId and createDate between :startDate and :endDate"
            + " group by memberId, createDate",
        TABLE);

    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
        dailyPostCountRequest);

    return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
  }

  private Post insert(final Post post) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    SqlParameterSource params = new BeanPropertySqlParameterSource(post);
    long id = jdbcInsert.executeAndReturnKey(params).longValue();

    return Post.builder()
        .id(id)
        .memberId(post.getMemberId())
        .contents(post.getContents())
        .createdDate(post.getCreatedDate())
        .createdAt(post.getCreatedAt())
        .build();
  }

}
