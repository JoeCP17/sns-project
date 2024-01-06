package com.example.snsproject.domain.post.repository;

import com.example.snsproject.util.PageHelper;
import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

  private static RowMapper<Post> POST_ROW_MAPPER = (rs, rowNum) ->
      new Post(
          rs.getLong("id"),
          rs.getLong("memberId"),
          rs.getString("contents"),
          rs.getDate("createdDate").toLocalDate(),
          rs.getTimestamp("createdAt").toLocalDateTime()
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

  @Transactional
  public void bulkInsert(final List<Post> posts) {
    String sql = String.format(
        "insert into %s (memberId, contents, createdDate, createdAt)"
            + "values (:memberId, :contents, :createdDate, :createdAt)",
        TABLE);

    SqlParameterSource[] params = posts.stream()
        .map(BeanPropertySqlParameterSource::new)
        .toArray(SqlParameterSource[]::new);

    namedParameterJdbcTemplate.batchUpdate(sql, params);
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

  public Page<Post> findAllByMemberId(final Long memberId, final Pageable pageable) {
    String sql = String.format(
        "select * "
            + "from %s "
            + "where memberId = :memberId "
            + "order by %s "
            + "limit :size "
            + "offset :offset "
        , TABLE, PageHelper.orderBy(pageable.getSort()));

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("memberId", memberId);
    params.addValue("size", pageable.getPageSize());
    params.addValue("offset", pageable.getOffset());

    List<Post> posts = namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);
    return new PageImpl<>(posts, pageable, getCount(memberId));
  }

  private Long getCount(final Long memberId) {
    String sql = String.format(
        "select count(id) "
            + "from %s "
            + "where memberId = :memberId",
        TABLE);

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("memberId", memberId);

    return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
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
