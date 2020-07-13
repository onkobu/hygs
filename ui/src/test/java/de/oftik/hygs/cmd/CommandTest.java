package de.oftik.hygs.cmd;

import static de.oftik.kehys.testi.UnexpectedCall.unexpectedCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.oftik.hygs.contract.Identifiable;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;
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
		public Notification toNotification(Connection conn, List<String> generatedKeys) {
			return unexpectedCall();
		}
	}

	public static final class TestColumn implements Column<Identifiable> {
		private final String name;

		private TestColumn(String name) {
			super();
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public void map(Identifiable t, ResultSet rs) throws SQLException {

		}

		@Override
		public void map(Identifiable t, int idx, PreparedStatement stmt) throws SQLException {

		}

		@Override
		public ColumnType type() {
			return null;
		}
	}

	static class TestTable implements Table<Identifiable> {
		static final TestTable TABLE = new TestTable();

		@Override
		public String name() {
			return getClass().getSimpleName();
		}

		@Override
		public Column<Identifiable> getPkColumn() {
			return unexpectedCall();
		}

		@Override
		public Column<Identifiable>[] columns() {
			return unexpectedCall();
		}

		@Override
		public Collection<Constraint> constraints() {
			return unexpectedCall();
		}
	}

	@Test
	public void renderInsert(@Mocked Connection conn) throws Exception {
		new TestCommand().insert(conn, TestTable.TABLE, new TestColumn("id"), new TestColumn("value1"),
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
		new TestCommand().update(conn, TestTable.TABLE, new TestColumn("id"), new TestColumn("value"));
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
		new TestCommand().update(conn, TestTable.TABLE, new TestColumn("id"), new TestColumn("value1"),
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
