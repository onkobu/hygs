package de.oftik.hygs.cmd;

public enum NotificationType {
	/**
	 * Caused by an INSERT-operation.
	 */
	INSERT,

	/**
	 * Caused by an UPDATE-operation.
	 */
	UPDATE,

	/**
	 * Caused by a DELETE-operation.
	 */
	DELETE,

	/**
	 * Prepared for deletion.
	 */
	TRASHED,

	/**
	 * Type is irrelevant, technical notification like errors during enqueue.
	 */
	TECHNICAL,

	/**
	 * An inactive/ deleted/ invalid entity was brought back to life.
	 */
	RESURRECT,

	/**
	 * One entity was assigned/ linked to another.
	 */
	ASSIGNED,

	/**
	 * Entities were unlinked.
	 */
	UNASSIGNED;
}
