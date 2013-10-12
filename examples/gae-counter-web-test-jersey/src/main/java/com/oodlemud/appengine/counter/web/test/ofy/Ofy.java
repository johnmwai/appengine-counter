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
package com.oodlemud.appengine.counter.web.test.ofy;

import com.googlecode.objectify.impl.ObjectifyImpl;

/**
 * Our basic data access interface. Extends the basic Objectify interface to add
 * our custom logic.
 */
public class Ofy extends ObjectifyImpl<Ofy>
{
	/** */
	public Ofy(OfyFactory base)
	{
		super(base);
	}

	/** More wrappers, fun */
	@Override
	public OfyLoader load()
	{
		return new OfyLoader(this);
	}
}