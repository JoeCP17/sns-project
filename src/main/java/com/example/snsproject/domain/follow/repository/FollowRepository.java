package com.example.snsproject.domain.follow.repository;

import com.example.snsproject.domain.follow.entity.Follow;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class FollowRepository {

  private static final String TABLE = "follow";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public FollowRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  private static final RowMapper<Follow> ROW_MAPPER = (ResultSet rs, int rowNum) -> Follow.builder()
      .id(rs.getLong("id"))
      .fromMemberId(rs.getLong("fromMemberId"))
      .toMemberId(rs.getLong("toMemberId"))
      .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
      .build();

  public List<Follow> findAllByFromMemberId(final Long fromMemberId) {
    final String sql = String.format("select * from %s where fromMemberId =?", TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);

    return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
  }

  public Follow save(final Follow follow) {
    if (follow.getId() == null) {
      return insert(follow);
    }

    throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
  }

  private Follow insert(final Follow follow) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(follow);
    long id = jdbcInsert.executeAndReturnKey(params).longValue();

    return Follow.builder()
        .id(id)
        .fromMemberId(follow.getFromMemberId())
        .toMemberId(follow.getToMemberId())
        .createdAt(follow.getCreatedAt())
        .build();
  }

}
