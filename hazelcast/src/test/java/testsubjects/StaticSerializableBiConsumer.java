/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package testsubjects;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.map.IMap;

import java.io.Serializable;
import java.util.function.BiConsumer;

public class StaticSerializableBiConsumer implements BiConsumer<String, Integer>, Serializable, HazelcastInstanceAware {

    private String targetMapName;
    private transient HazelcastInstance hazelcastInstance;

    public StaticSerializableBiConsumer(String targetMapName) {
        this.targetMapName = targetMapName;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void accept(String key, Integer value) {
        IMap<Object, Object> map = hazelcastInstance.getMap(targetMapName);
        map.put(key, value);
    }

}
