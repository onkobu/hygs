package de.oftik.hygs.ui.trash;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;

abstract class AbstractBinding<I extends de.oftik.kehys.kersantti.Identifiable, T extends Table<I>>
		implements Identifiable<I, T> {
	private final I identifiable;
	private final EntitySource<I, T> source;

	AbstractBinding(I identifiable, T table, Column<I> deleteCol) {
		this.source = new EntitySourceFixture<I, T>(table, deleteCol);
		this.identifiable = identifiable;
	}

	@Override
	public final EntitySource<I, T> getSource() {
		return source;
	}

	@Override
	public final String getId() {
		return identifiable.getId();
	}

	@Override
	public final I unwrap() {
		return identifiable;
	}

	public final I getIdentifiable() {
		return identifiable;
	}

}
