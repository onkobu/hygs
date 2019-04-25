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
	 * Type is irrelevant, technical notification like errors during enqueue.
	 */
	TECHNICAL;
}
