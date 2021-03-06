package io.instacount.appengine.counter;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.joda.time.DateTime;

import com.google.common.base.Preconditions;
import com.googlecode.objectify.Key;
import io.instacount.appengine.counter.data.CounterShardData;
import io.instacount.appengine.counter.data.CounterShardOperationData;

/**
 * An interface for modeling the result of a mutation (increment or decrement) of a {@link CounterShardData} entity in
 * the Datastore.
 */
public interface CounterOperation
{
	/**
	 * Return a {@link UUID} that identifies this counter shard operation.
	 *
	 * @return
	 */
	UUID getOperationUuid();

	/**
	 * Return the {@link Key} for the {@link CounterShardData} that this counter operation result was effected upon.
	 *
	 * @return
	 */
	Key<CounterShardData> getCounterShardDataKey();

	/**
	 * Get the type of counter operation that was performed.
	 *
	 * @return
	 */
	CounterOperationType getCounterOperationType();

	/**
	 * Return the amount of this mutation result, as a long. Counters may not be incremented or decrements by more than
	 * {@link Long#MAX_VALUE -1}.
	 *
	 * @return
	 */
	long getAppliedAmount();

	/**
	 * Return the {@link DateTime} that this instance was created.
	 *
	 * @return
	 */
	DateTime getCreationDateTime();

	/**
	 * Reflects whether or not a new counter was created before executing this counter operation.
	 * 
	 * @return {@code true} if a new counter was created; {@code false} if the counter already existed.
	 */
	boolean newCounterCreated();

	/**
	 * An enumeration that identifies the type of a {@link CounterOperation}.
	 */
	enum CounterOperationType
	{
		INCREMENT, DECREMENT
	}

	/**
	 * An implementation of {@link CounterOperation} to model a counter operation that consists of 0 or more operations
	 * upon counter shards.
	 */
	@Getter
	@ToString
	@EqualsAndHashCode(of = {
		"operationUuid"
	})
	class Impl implements CounterOperation
	{
		private final UUID operationUuid;

		private final Key<CounterShardData> counterShardDataKey;

		private final CounterOperationType counterOperationType;

		private final long appliedAmount;

		private final DateTime creationDateTime;

		private final boolean newCounterCreated;

		/**
		 * Required-args Constructor.
		 *
		 * @param operationUuid A {@link UUID} that uniquely identifies this counter operation result set.
		 * @param counterShardDataKey A {link Key} of type {@link CounterShardData} that identifies the shard this
		 *            operation was performed upon.
		 * @param counterOperationType The {@link CounterOperationType} of this operation.
		 * @param appliedAmount The appliedAmount of the applied increment or decrement.
		 * @param creationDateTime The {@link DateTime} representing the date-time this increment was effected.
		 * @param newCounterCreated {@code true} if a new counter was created before executing this operation;
		 *            {@code false} if the counter already existed.
		 */
		public Impl(final UUID operationUuid, final Key<CounterShardData> counterShardDataKey,
				final CounterOperationType counterOperationType, final long appliedAmount,
				final DateTime creationDateTime, final boolean newCounterCreated)
		{
			Preconditions.checkNotNull(operationUuid);
			this.operationUuid = operationUuid;

			Preconditions.checkNotNull(counterShardDataKey);
			this.counterShardDataKey = counterShardDataKey;

			Preconditions.checkNotNull(counterOperationType);
			this.counterOperationType = counterOperationType;

			Preconditions.checkArgument(appliedAmount > 0, "Counter operation amounts must be positive!");
			this.appliedAmount = appliedAmount;

			Preconditions.checkNotNull(creationDateTime);
			this.creationDateTime = creationDateTime;

			this.newCounterCreated = newCounterCreated;
		}

		/**
		 * Required-args Constructor.
		 *
		 * @param counterShardOperationData An instance of {@link CounterShardOperationData} to copy from.
		 */
		public Impl(final CounterShardOperationData counterShardOperationData)
		{
			Preconditions.checkNotNull(counterShardOperationData);

			this.operationUuid = UUID.fromString(counterShardOperationData.getId());
			this.counterShardDataKey = counterShardOperationData.getCounterShardDataKey();
			this.counterOperationType = counterShardOperationData.getCounterOperationType();
			this.appliedAmount = counterShardOperationData.getMutationAmount();
			this.creationDateTime = counterShardOperationData.getCreationDateTime();
			this.newCounterCreated = false;
		}

		/**
		 * Copy Constructor.
		 * 
		 * @param counterOperation
		 */
		public Impl(final CounterOperation counterOperation)
		{
			Preconditions.checkNotNull(counterOperation);

			this.operationUuid = counterOperation.getOperationUuid();
			this.counterShardDataKey = counterOperation.getCounterShardDataKey();
			this.counterOperationType = counterOperation.getCounterOperationType();
			this.appliedAmount = counterOperation.getAppliedAmount();
			this.creationDateTime = counterOperation.getCreationDateTime();
			this.newCounterCreated = counterOperation.newCounterCreated();
		}

		@Override
		public boolean newCounterCreated()
		{
			return this.newCounterCreated;
		}
	}
}
