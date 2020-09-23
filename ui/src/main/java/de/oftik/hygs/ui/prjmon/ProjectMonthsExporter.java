package de.oftik.hygs.ui.prjmon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.oftik.hygs.query.prjmon.ProjectMonth;
import de.oftik.hygs.query.prjmon.ProjectMonthsDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ExportError;
import de.oftik.hygs.ui.TimerConsumerWrapper;
import de.oftik.kehys.keijukainen.function.Pair;
import de.oftik.kehys.kersantti.query.QueryStatistics;

public class ProjectMonthsExporter {
	private ProjectMonthsDAO dao;

	public ProjectMonthsExporter(ApplicationContext context) {
		this(new ProjectMonthsDAO(context));
	}

	ProjectMonthsExporter(ProjectMonthsDAO dao) {
		super();
		this.dao = dao;
	}

	public Pair<QueryStatistics, List<ExportError>> marshal(File f)
			throws IOException, JAXBException, XMLStreamException, FactoryConfigurationError {
		final String xmlCharEnc = "UTF-8";
		final List<ExportError> allErrors = new ArrayList<>();
		QueryStatistics statsTmp;
		try (FileWriter fw = new FileWriter(f); BufferedWriter bw = new BufferedWriter(fw);) {
			statsTmp = marshal(xmlCharEnc, allErrors, bw);
		} catch (SQLException e) {
			statsTmp = null;
			allErrors.add(new ExportError(e));
		}
		final QueryStatistics stats = allErrors.isEmpty() ? statsTmp : null;
		return new Pair<QueryStatistics, List<ExportError>>() {
			@Override
			public QueryStatistics left() {
				return stats;
			}

			@Override
			public List<ExportError> right() {
				return allErrors.isEmpty() ? null : allErrors;
			}
		};
	}

	QueryStatistics marshal(final String xmlCharEnc, final List<ExportError> allErrors, Writer bw)
			throws XMLStreamException, FactoryConfigurationError, JAXBException, PropertyException, SQLException {
		XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(bw);
		final JAXBContext context = JAXBContext.newInstance(ProjectMonths.class);
		final Marshaller marshaller = context.createMarshaller();
		// marshaller.setProperty( Marshaller.JAXB_ENCODING, xmlCharEnc );
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		xsw.writeStartDocument(xmlCharEnc, null);
		xsw.writeCharacters("\r\n");
		xsw.writeStartElement("prj-mons");
//			xsw.setDefaultNamespace("http://meinnamespace.meinefirma.de");
//			xsw.writeDefaultNamespace("http://meinnamespace.meinefirma.de");
//			xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//			xsw.writeAttribute("xsi:schemaLocation", "http://meinnamespace.meinefirma.de Buecher.xsd");
//			xsw.writeCharacters("\r\n");
		final TimerConsumerWrapper<ProjectMonth> tcw = TimerConsumerWrapper.wrap((pm) -> {
			try {
				marshaller.marshal(pm, xsw);
				xsw.writeCharacters("\r\n");
			} catch (JAXBException | XMLStreamException e) {
				allErrors.add(new ExportError(e, pm));
			}
		});
		dao.consumeAll(tcw);
		tcw.stop();
		xsw.writeEndElement();
		xsw.writeEndDocument();
		xsw.writeCharacters("\r\n");
		xsw.flush();
		xsw.close();
		return tcw;
	}
}
