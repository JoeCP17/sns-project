package com.example.snsproject.domain.member.repository;

import com.example.snsproject.domain.member.entity.Member;
import java.sql.ResultSet;
import java.util.Optional;
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
public class MemberRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "member";

  public Optional<Member> findById(final Long id) {
    /**
     * select *
     * from Member
     * where id = :id
     */
    final String sql = "select * from " + TABLE + " where id = :id";
    MapSqlParameterSource param = new MapSqlParameterSource();
    param.addValue("id", id);

    RowMapper<Member> rowMapper = (ResultSet rs, int rowNum) -> Member.builder()
        .id(rs.getLong("id"))
        .email(rs.getString("email"))
        .nickName(rs.getString("nickName"))
        .birthDay(rs.getDate("birthDay").toLocalDate())
        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
        .build();

    Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);

    return Optional.ofNullable(member);
  }
  public Member save(final Member member) {
    /**
     * 1. member의 id를 보고 갱신 또는 삽입을 결정한다.
     * 반환값은 member의 아이디 값을 받아서 반환한다.
     */
    if (member.getId() == null) {
      return insert(member);
    }
    return update(member);
  }

  private Member insert(final Member member) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName("Member")
        .usingGeneratedKeyColumns("id");

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    long id = jdbcInsert.execute(param);
    return Member.builder()
        .id(id)
        .email(member.getEmail())
        .nickName(member.getNickName())
        .birthDay(member.getBirthDay())
        .createdAt(member.getCreatedAt())
        .build();
  }

  private Member update(final Member member) {
    final String sql = String.format("update %s set email = :email, nickname = :nickname, birthday = :birthday"
        + " where id = :id", TABLE);

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    namedParameterJdbcTemplate.update(sql, param);
    return member;
  }

}
