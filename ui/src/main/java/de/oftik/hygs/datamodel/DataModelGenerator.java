package de.oftik.hygs.datamodel;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import de.oftik.hygs.datamodel.DataModelParser.AbbrContext;
import de.oftik.hygs.datamodel.DataModelParser.ConstraintContext;
import de.oftik.hygs.datamodel.DataModelParser.EntityContext;
import de.oftik.hygs.datamodel.DataModelParser.FieldContext;
import de.oftik.hygs.datamodel.DataModelParser.NamespaceDefContext;
import de.oftik.hygs.datamodel.DataModelParser.TypeContext;

public class DataModelGenerator {
	public static void main(String[] args) throws Exception {
		final var dataModelLexer = new DataModelLexer(CharStreams.fromFileName("src/main/resources/data.model"));
		final var tokens = new CommonTokenStream(dataModelLexer);
		final var parser = new DataModelParser(tokens);
		final var startContext = parser.start();
		final var walker = new ParseTreeWalker();
		final var listener = new DataModelBaseListener() {
			private String abbreviation;
			private String assumedStringType;
			private boolean idWritten;
			private String currentFieldName;
			private String currentEntityName;

			@Override
			public void enterNamespaceDef(NamespaceDefContext ctx) {
				System.out.println("package de.oftik.hygs.query." + ctx.NAME() + ";\n");
				System.out.println(ColumnPrimitives.imports());
			}

			@Override
			public void enterEntity(EntityContext ctx) {
				currentEntityName = firstCharUppercase(ctx.NAME().getText());
				System.out.println(
						"public enum " + currentEntityName + "Column implements Column<" + currentEntityName + "> {");
				idWritten = false;
			}

			@Override
			public void enterField(FieldContext ctx) {
				if (!idWritten) {
					System.out.println(
							"\t" + abbreviation + "_id " + ColumnPrimitives.primaryKeyColumn(currentEntityName));
					idWritten = true;
				}
				System.out.print("\t");
				if (abbreviation != null)
					System.out.print(abbreviation + "_");

				this.currentFieldName = ctx.NAME().getText();
				System.out.print(currentFieldName);
			}

			@Override
			public void enterType(TypeContext ctx) {
				switch (ctx.PRIMITIVE().getText()) {
				case "flag": {
					System.out.print(ColumnPrimitives.flagColumn(currentEntityName, currentFieldName));
				}
					break;
				case "string": {
					assumedStringType = "TEXT";
				}
					break;

				case "integer": {
					System.out.print(ColumnPrimitives.integerColumn(currentEntityName, currentFieldName));
				}
					break;

				}
			}

			@Override
			public void exitConstraint(ConstraintContext ctx) {
				if (ctx.maxConstraint() != null) {
					final var len = Integer.parseInt(ctx.maxConstraint().NUMBER().getText());
					if (len < 255) {
						System.out.print(ColumnPrimitives.varcharColumn(currentEntityName, currentFieldName, len));
						assumedStringType = null;
					} else {
						System.err.println("constraint length exceeds 255/ VARCHAR and is therefore ignored");
					}
				}
			}

			@Override
			public void exitField(FieldContext ctx) {
				if (assumedStringType != null) {
					System.out.print(ColumnPrimitives.textColumn(currentEntityName, currentFieldName));
				}
				System.out.print("\n");
				assumedStringType = null;
				currentFieldName = null;
			}

			@Override
			public void exitEntity(EntityContext ctx) {
				System.out.println(ColumnPrimitives.body(currentEntityName));
				System.out.println("}");
				abbreviation = null;
			}

			@Override
			public void enterAbbr(AbbrContext ctx) {
				abbreviation = ctx.NAME().getText();
			}

		};
		walker.walk(listener, startContext);
	}

	static String firstCharUppercase(final String theName) {
		return theName.substring(0, 1).toUpperCase() + theName.substring(1);
	}

	static class ColumnPrimitives {
		static String imports() {
			return "import java.sql.PreparedStatement;\n" + "import java.sql.ResultSet;\n"
					+ "import java.sql.SQLException;\n" + "\n" + "import de.oftik.kehys.kersantti.Column;\n"
					+ "import de.oftik.kehys.kersantti.ColumnType;\n" + "import de.oftik.kehys.kersantti.SqlType;\n";
		}

		public static String textColumn(String currentEntityName, String name) {
			final var methodSuffix = firstCharUppercase(name);
			return "(ColumnType.SQLITE_TEXT) {\n" + "		@Override\n" + "		public void map("
					+ currentEntityName + " t, ResultSet rs) throws SQLException {\n" + "			t.set"
					+ methodSuffix + "(asString(rs));\n" + "		}\n" + "\n" + "		@Override\n"
					+ "		public void map(" + currentEntityName
					+ " t, int idx, PreparedStatement stmt) throws SQLException {\n"
					+ "			stmt.setString(idx, t.get" + methodSuffix + "());\n" + "		}\n" + "	},";
		}

		static String body(String entityName) {
			return ";\n" + "\n" + "	private final ColumnType type;\n" + "\n" + "	" + entityName
					+ "Column(ColumnType type) {\n" + "		this.type = type;\n" + "	}\n" + "\n" + "	@Override\n"
					+ "	public ColumnType type() {\n" + "		return type;\n" + "	}";
		}

		static String primaryKeyColumn(String entity) {
			return "(ColumnType.PK_TYPE) {\n" + "		@Override\n" + "		public void map(" + entity
					+ " t, ResultSet rs) throws SQLException {\n" + "			t.setId(asString(rs));\n" + "		}\n"
					+ "\n" + "		@Override\n" + "		public void map(" + entity
					+ " t, int idx, PreparedStatement stmt) throws SQLException {\n"
					+ "			stmt.setString(idx, t.getId());\n" + "		}\n" + "	},";
		}

		static String integerColumn(String entity, String name) {
			final var methodSuffix = firstCharUppercase(name);
			return "(SqlType.INTEGER.deriveType()) {\n" + "		@Override\n" + "		public void map(" + entity
					+ " t, ResultSet rs) throws SQLException {\n" + "			t.set" + methodSuffix
					+ "(asString(rs));\n" + "		}\n" + "\n" + "		@Override\n" + "		public void map("
					+ entity + " t, int idx, PreparedStatement stmt) throws SQLException {\n"
					+ "			stmt.setString(idx, t.get" + methodSuffix + "());\n" + "		}\n" + "	}";
		}

		static String varcharColumn(String entity, String name, int length) {
			final var methodSuffix = firstCharUppercase(name);
			return "(SqlType.VARCHAR.deriveType(" + length + ")) {\n" + "\n" + "		@Override\n"
					+ "		public void map(" + entity + " t, ResultSet rs) throws SQLException {\n"
					+ "			t.set" + methodSuffix + "(asString(rs));\n" + "		}\n" + "\n" + "		@Override\n"
					+ "		public void map(" + entity + " t, int idx, PreparedStatement stmt) throws SQLException {\n"
					+ "			stmt.setString(idx, t.get" + methodSuffix + "());\n" + "		}\n" + "	},";
		}

		static String flagColumn(String entity, String name) {
			final var methodSuffix = firstCharUppercase(name);
			return "(SqlType.BOOLEAN.deriveType()) {\n" + "		@Override\n" + "		public void map(" + entity
					+ " t, ResultSet rs) throws SQLException {\n" + "			t.set" + methodSuffix
					+ "(Boolean.TRUE.equals(asBoolean(rs)));\n" + "		}\n" + "\n" + "		@Override\n"
					+ "		public void map(" + entity + " t, int idx, PreparedStatement stmt) throws SQLException {\n"
					+ "			stmt.setBoolean(idx, t.is" + methodSuffix + "());\n" + "		}\n" + "	}";
		}
	}
}
