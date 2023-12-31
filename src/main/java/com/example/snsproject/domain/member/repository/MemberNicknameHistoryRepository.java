package com.example.snsproject.domain.member.repository;

import com.example.snsproject.domain.member.entity.MemberNicknameHistory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "MemberNicknameHistory";

  private final RowMapper<MemberNicknameHistory> rowMapper = (rs, rowNum) ->
      MemberNicknameHistory.builder()
      .id(rs.getLong("id"))
      .memberId(rs.getLong("memberId"))
      .nickName(rs.getString("nickName"))
      .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
      .build();

  public MemberNicknameHistory save(final MemberNicknameHistory memberNicknameHistory) {
    if (memberNicknameHistory.getId() == null) {
      return insert(memberNicknameHistory);
    }
    throw new UnsupportedOperationException("갱신을 지원하지 않습니다.");
  }

  public List<MemberNicknameHistory> findAllByMemberId(final long memberId) {
    final String sql = String.format("select * from %s where memberId = :memberId", TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId);

    return namedParameterJdbcTemplate.query(sql, params, rowMapper);
  }

  private MemberNicknameHistory insert(final MemberNicknameHistory memberNicknameHistory) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    SqlParameterSource param = new BeanPropertySqlParameterSource(memberNicknameHistory);
    long id = jdbcInsert.execute(param);
    return MemberNicknameHistory.builder()
        .id(id)
        .memberId(memberNicknameHistory.getMemberId())
        .nickName(memberNicknameHistory.getNickName())
        .createdAt(memberNicknameHistory.getCreatedAt())
        .build();
  }

}
