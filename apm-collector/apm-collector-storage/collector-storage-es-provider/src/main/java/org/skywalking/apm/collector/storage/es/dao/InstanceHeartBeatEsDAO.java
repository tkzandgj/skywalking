/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.storage.es.dao;

import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.skywalking.apm.collector.core.UnexpectedException;
import org.skywalking.apm.collector.storage.base.dao.IPersistenceDAO;
import org.skywalking.apm.collector.storage.dao.IInstanceHeartBeatDAO;
import org.skywalking.apm.collector.storage.es.base.dao.EsDAO;
import org.skywalking.apm.collector.storage.table.register.Instance;
import org.skywalking.apm.collector.storage.table.register.InstanceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peng-yongsheng
 */
public class InstanceHeartBeatEsDAO extends EsDAO implements IInstanceHeartBeatDAO, IPersistenceDAO<IndexRequestBuilder, UpdateRequestBuilder, Instance> {

    private final Logger logger = LoggerFactory.getLogger(InstanceHeartBeatEsDAO.class);

    @Override public Instance get(String id) {
        GetResponse getResponse = getClient().prepareGet(InstanceTable.TABLE, id).get();
        if (getResponse.isExists()) {
            Instance instance = new Instance(id);
            Map<String, Object> source = getResponse.getSource();
            instance.setInstanceId((Integer)source.get(InstanceTable.COLUMN_INSTANCE_ID));
            instance.setHeartBeatTime((Long)source.get(InstanceTable.COLUMN_HEARTBEAT_TIME));
            logger.debug("getId: {} is exists", id);
            return instance;
        } else {
            logger.debug("getId: {} is not exists", id);
            return null;
        }
    }

    @Override public IndexRequestBuilder prepareBatchInsert(Instance data) {
        throw new UnexpectedException("There is no need to merge stream data with database data.");
    }

    @Override public UpdateRequestBuilder prepareBatchUpdate(Instance data) {
        Map<String, Object> source = new HashMap<>();
        source.put(InstanceTable.COLUMN_HEARTBEAT_TIME, data.getHeartBeatTime());
        return getClient().prepareUpdate(InstanceTable.TABLE, data.getId()).setDoc(source);
    }
}