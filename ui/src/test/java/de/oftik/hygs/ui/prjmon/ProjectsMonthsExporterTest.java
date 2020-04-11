package de.oftik.hygs.ui.prjmon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import de.oftik.hygs.query.prjmon.ProjectMonth;
import de.oftik.hygs.query.prjmon.ProjectMonthsDAO;
import de.oftik.hygs.ui.ExportError;

public class ProjectsMonthsExporterTest {
	@Test
	public void writeEmptyXML() throws Exception {
		final ProjectMonthsDAOFixture dao = new ProjectMonthsDAOFixture(Collections.emptyList());
		final StringWriter sw = new StringWriter();
		final ArrayList<ExportError> errs = new ArrayList<>();
		new ProjectMonthsExporter(dao).marshal("UTF-8", errs, sw);

		assertThat(sw.toString(), equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<prj-mons></prj-mons>\r\n"));
		assertThat(errs, Matchers.empty());
	}

	@Test
	public void writeSingleEmptyEntity() throws Exception {
		final int id = 17;
		final String name = "prjName";
		final int months = 7;
		final ProjectMonthsDAOFixture dao = new ProjectMonthsDAOFixture(
				Arrays.asList(new ProjectMonth(id, name, months)));
		final StringWriter sw = new StringWriter();
		final ArrayList<ExportError> errs = new ArrayList<>();
		new ProjectMonthsExporter(dao).marshal("UTF-8", errs, sw);

		assertThat(sw.toString(),
				equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<prj-mons><projectMonth id=\"" + id
						+ "\" projectName=\"" + name + "\" months=\"" + months + "\"></projectMonth>\r\n"
						+ "</prj-mons>\r\n"));
		assertThat(errs, Matchers.empty());
	}

	@Test
	public void writeSingleEntity() throws Exception {
		final ProjectMonthsDAOFixture dao = new ProjectMonthsDAOFixture(
				Arrays.asList(new ProjectMonth(70L, "heal the world", 16)));
		final StringWriter sw = new StringWriter();
		final ArrayList<ExportError> errs = new ArrayList<>();
		new ProjectMonthsExporter(dao).marshal("UTF-8", errs, sw);

		assertThat(sw.toString(), equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<prj-mons><projectMonth id=\"70\" projectName=\"heal the world\" months=\"16\"></projectMonth>\r\n"
				+ "</prj-mons>\r\n"));
		assertThat(errs, Matchers.empty());
	}

	static class ProjectMonthsDAOFixture extends ProjectMonthsDAO {
		private final List<ProjectMonth> entities;

		ProjectMonthsDAOFixture(List<ProjectMonth> entities) {
			super(null);
			this.entities = entities;
		}

		@Override
		public void consumeAll(Consumer<ProjectMonth> consumer) throws SQLException {
			entities.stream().forEach(consumer);
		}
	}
}
