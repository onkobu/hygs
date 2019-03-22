package de.oftik.hygs.ui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.bind.JAXBException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import de.oftik.hygs.ui.prjmon.ProjectMonthsExporter;
import de.oftik.kehys.keijukainen.function.EitherOr;
import de.oftik.kehys.keijukainen.function.Pair;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class ExportPanel extends JPanel {
	private final ApplicationContext context;
	private final JTextArea resultArea = new JTextArea(10, 40);

	public ExportPanel(ApplicationContext context) {
		this.context = context;
		resultArea.setEditable(false);
		setLayout(new GridBagLayout());
		createUI();
	}

	private void createUI() {
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		add(ComponentFactory.createButton(I18N.EXPORT_PRJ_MONTHS, this::exportProjectMonths), gbc.end());

		add(new JScrollPane(resultArea),
				gbc.nextRow().remainderX().remainderY().weightx(1.0).weighty(1.0).fillBoth().end());
	}

	private void exportProjectMonths(ActionEvent evt) {
		resultArea.setText("");
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		if (chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) {
			return;
		}
		final File file = chooser.getSelectedFile();
		ProjectMonthsExporter exporter = new ProjectMonthsExporter(context);
		try {
			final Pair<QueryStatistics, List<ExportError>> statsOrErrs = exporter.marshal(file);
			EitherOr.with(statsOrErrs).either((stats) -> {
				resultArea.append(I18N.MSG_EXPORTED_STATISTICS.message(stats.getRowCount(),
						stats.getTotalMillies() / 1000.0, stats.getDurationMin(), stats.getDurationMax()));
				resultArea.append(I18N.MSG_EXPORTED_XML.message(file));
			}).or((errs) -> {
				statsOrErrs.right().stream().map(ExportError::toString).forEach(resultArea::append);
				resultArea.setCaretPosition(resultArea.getDocument().getLength());
			});
		} catch (IOException | JAXBException | XMLStreamException | FactoryConfigurationError e) {
			resultArea.append(Exceptions.renderStackTrace(e));
		}
	}
}
