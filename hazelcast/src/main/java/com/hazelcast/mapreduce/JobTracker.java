/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.mapreduce;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.mapreduce.process.ProcessJob;

/**
 * <p>
 * The JobTracker interface is used to create instances of {@link Job}s or {@link ProcessJob}s depending
 * on the given data structure / data source.
 * </p>
 * <p>
 * The underlying created instance of the {@link Job} / {@link ProcessJob} depends on whether it is used for a
 * {@link com.hazelcast.client.HazelcastClient} or a {@link com.hazelcast.core.Hazelcast} member node.
 * </p>
 * <p>
 * The default usage is same for both cases and looks similar to the following example:<br>
 * <p/>
 * <pre>
 * HazelcastInstance hz = getHazelcastInstance();
 * IMap map = hz.getMap( getMapName() );
 * JobTracker tracker = hz.getJobTracker( "default" );
 * Job job = tracker.newJob( KeyValueSource.fromMap( map ) );
 * </pre>
 * </p>
 * <p>
 * The created instance of JobTracker is fully threadsafe and can be used concurrently and multiple times.<br>
 * <b>Caution: Do not use the JobTracker with data structures of other {@link com.hazelcast.core.HazelcastInstance}
 * instances than the one used for creation of the JobTracker. Unexpected results may happen!</b>
 * </p>
 */
public interface JobTracker
        extends DistributedObject {

    /**
     * Builds a {@link Job} instance for the given {@link KeyValueSource} instance. The returning
     * implementation is depending on the {@link com.hazelcast.core.HazelcastInstance} that was creating the JobTracker.<br>
     * <b>Caution: Do not use the JobTracker with data structures of other {@link com.hazelcast.core.HazelcastInstance} instances
     * than the one used for creation of the JobTracker. Unexpected results may happen!</b>
     *
     * @param source data source the created Job should work on
     * @return instance of the Job bound to the given KeyValueSource
     */
    <K, V> Job<K, V> newJob(KeyValueSource<K, V> source);

    /**
     * Builds a complex {@link ProcessJob} instance for the given {@link KeyValueSource} instance. The returning
     * implementation is depending on the {@link com.hazelcast.core.HazelcastInstance} that was creating the JobTracker.<br>
     * <b>Caution: Do not use the JobTracker with data structures of other {@link com.hazelcast.core.HazelcastInstance} instances
     * than the one used for creation of the JobTracker. Unexpected results may happen!</b>
     *
     * @param source data source the created Job should work on
     * @return instance of the ProcessJob bound to the given KeyValueSource
     */
    // This feature is moved to Hazelcast 3.3
    //<K, V> ProcessJob<K, V> newProcessJob(KeyValueSource<K, V> source);

    /**
     * Returns an instance of {@link com.hazelcast.mapreduce.JobProcessInformation} to find out the state and
     * statistics of a running task or null if given job id is not available because job is already finished or
     * not yet submitted.<br/>
     * It even returns null if not requested on the job issuing cluster member or client since those values are
     * not distributed to all clusters for traffic reasons.
     *
     * @param jobId the job id of the requested job
     * @return instance of the jobs process information or null if job id is not available
     */
    JobProcessInformation getJobProcessInformation(String jobId);

}
