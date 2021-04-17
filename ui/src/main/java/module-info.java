module hygs.uidi {
	requires transitive java.sql;
	requires java.desktop;
	requires kehys.kersantti;
	requires kehys.keijukainen;
	requires java.xml.bind;
	requires kehys.lippu;
	requires jdatepicker;

	opens de.oftik.hygs.ui.prjmon;
	opens de.oftik.hygs.query.prjmon;
}