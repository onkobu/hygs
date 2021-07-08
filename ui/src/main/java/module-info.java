module hygs.uidi {
	requires transitive java.sql;
	requires java.desktop;
	requires kehys.kersantti;
	requires kehys.keijukainen;
	requires kehys.lippu;
	requires jdatepicker;
	requires jakarta.xml.bind;
	requires org.antlr.antlr4.runtime;

	opens de.oftik.hygs.ui.prjmon;
	opens de.oftik.hygs.query.prjmon;
}