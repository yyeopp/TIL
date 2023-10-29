package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;

    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); //트랜잭션 시작
            bizLogic(con, fromId, toId, money);
            con.commit(); // 성공 시 커밋
        } catch (Exception e) {
            con.rollback(); // 실패 시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }

    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 해당 커넥션이 풀로 돌아가는 것을 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);

            }
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException{
        Member fromMember = memberRepository.findById(con,fromId);
        Member toMember = memberRepository.findById(con,toId);

        memberRepository.update(con,fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con,toId, toMember.getMoney() + money);

    }
    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
