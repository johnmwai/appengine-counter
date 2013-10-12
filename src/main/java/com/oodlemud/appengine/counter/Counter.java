/**
 * Copyright (C) 2013 Oodlemud Inc. (developers@oodlemud.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oodlemud.appengine.counter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.oodlemud.appengine.counter.data.CounterData.CounterStatus;

/**
 * An immutable object that stores count information.
 * 
 * @author David Fuelling <dfuelling@oodlemud.com>
 */
@Data
@EqualsAndHashCode
@ToString
public class Counter
{
	private final String counterName;
	private final CounterStatus counterStatus;
	private final long count;

	/**
	 * Required-args Constructor. Sets the {@code counterStatus} to
	 * {@link CounterStatus.AVAILABLE} and the {@code count} to zero.
	 * 
	 * @param counterName
	 */
	public Counter(final String counterName)
	{
		this(counterName, CounterStatus.AVAILABLE);
	}

	/**
	 * Required-args Constructor. Sets the {@code count} to zero.
	 * 
	 * @param counterName
	 * @param counterStatus
	 */
	public Counter(final String counterName, final CounterStatus counterStatus)
	{
		this(counterName, counterStatus, 0);
	}

	/**
	 * Required-args Constructor.
	 * 
	 * @param counterName
	 * @param counterStatus
	 * @param count
	 */
	public Counter(final String counterName, final CounterStatus counterStatus, final long count)
	{
		Preconditions.checkArgument(!StringUtils.isBlank(counterName), "CounterName may not be empty, blank, or null!");
		Preconditions.checkNotNull(counterStatus);

		this.counterName = counterName;
		this.counterStatus = counterStatus;
		this.count = count;
	}
}
