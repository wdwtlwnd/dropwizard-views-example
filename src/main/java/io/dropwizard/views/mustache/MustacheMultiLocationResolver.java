/**
 * Copyright (c) 2016 Elysian Software Limited.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dropwizard.views.mustache;

import com.github.mustachejava.MustacheResolver;

import java.io.Reader;
import java.util.Set;

/**
 * Multi-location resolver for Mustache.
 *
 * Created by mlp on 26/04/16.
 */
public class MustacheMultiLocationResolver implements MustacheResolver {

	private final Set<MustacheResolver> resolvers;
	private MustacheResolver lastResolver;

	public MustacheMultiLocationResolver(Set<MustacheResolver> resolvers) {
		this.resolvers = resolvers;
	}

	@Override
	public Reader getReader(String resourceName) {
		Reader reader = null;

		if (lastResolver != null) {
			reader = lastResolver.getReader(resourceName);
		}

		if (reader == null) {
			for (MustacheResolver resolver : resolvers) {
				reader = resolver.getReader(resourceName);
				if (reader != null) {
					lastResolver = resolver;
					break;
				}
			}
		}

		return reader;
	}

}
