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

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ExportError;

public class ProjectMonthsExporter {
	private ProjectMonthsDAO dao;

	public ProjectMonthsExporter(ApplicationContext context) {
		this(new ProjectMonthsDAO(context));
	}

	ProjectMonthsExporter(ProjectMonthsDAO dao) {
		super();
		this.dao = dao;
	}

	public List<ExportError> marshal(File f)
			throws IOException, JAXBException, XMLStreamException, FactoryConfigurationError {
		final String xmlCharEnc = "UTF-8";
		final List<ExportError> allErrors = new ArrayList<>();
		try (FileWriter fw = new FileWriter(f); BufferedWriter bw = new BufferedWriter(fw);) {
			marshal(xmlCharEnc, allErrors, bw);
		} catch (SQLException e) {
			allErrors.add(new ExportError(e));
		}
		return allErrors;
	}

	void marshal(final String xmlCharEnc, final List<ExportError> allErrors, Writer bw)
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
		dao.consumeAll((pm) -> {
			try {
				marshaller.marshal(pm, xsw);
				xsw.writeCharacters("\r\n");
			} catch (JAXBException | XMLStreamException e) {
				allErrors.add(new ExportError(e, pm.getId()));
			}
		});
		xsw.writeEndElement();
		xsw.writeEndDocument();
		xsw.writeCharacters("\r\n");
		xsw.flush();
		xsw.close();
	}
}
