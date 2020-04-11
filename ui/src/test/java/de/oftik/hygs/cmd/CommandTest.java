package de.oftik.hygs.cmd;

import static de.oftik.kehys.testi.UnexpectedCall.unexpectedCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.oftik.hygs.query.Table;
import de.oftik.keyhs.kersantti.Column;
import mockit.Mocked;
import mockit.Verifications;

public class CommandTest {
	public static class TestCommand implements Command {

		@Override
		public CommandTarget target() {
			return unexpectedCall();
		}

		@Override
		public PreparedStatement prepare(Connection conn) throws SQLException {
			return unexpectedCall();
		}

		@Override
		public Notification toNotification(List<Long> generatedKeys) {
			return unexpectedCall();
		}
	}

	public static final class TestColumn implements Column<Object> {
		private final String name;

		private TestColumn(String name) {
			super();
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}
	}

	@Test
	public void renderInsert(@Mocked Connection conn) throws Exception {
		new TestCommand().insert(conn, Table.prj_company, new TestColumn("id"), new TestColumn("value1"),
				new TestColumn("value2"), new TestColumn("value3"));
		new Verifications() {
			{
				String s;
				conn.prepareStatement(s = withCapture(), anyInt);
				assertThat(s, equalTo("INSERT INTO prj_company (id,value1,value2,value3) VALUES (?,?,?,?)"));
			}
		};
	}

	@Test
	public void renderUpdateSingleColumn(@Mocked Connection conn) throws Exception {
		new TestCommand().update(conn, Table.prj_company, new TestColumn("id"), new TestColumn("value"));
		new Verifications() {
			{
				String s;
				conn.prepareStatement(s = withCapture(), anyInt);
				assertThat(s, equalTo("UPDATE prj_company SET value=? WHERE id=?"));
			}
		};
	}

	@Test
	public void renderUpdate(@Mocked Connection conn) throws Exception {
		new TestCommand().update(conn, Table.prj_company, new TestColumn("id"), new TestColumn("value1"),
				new TestColumn("value2"), new TestColumn("value3"));
		new Verifications() {
			{
				String s;
				conn.prepareStatement(s = withCapture(), anyInt);
				assertThat(s, equalTo("UPDATE prj_company SET value1=?,value2=?,value3=? WHERE id=?"));
			}
		};
	}
}
