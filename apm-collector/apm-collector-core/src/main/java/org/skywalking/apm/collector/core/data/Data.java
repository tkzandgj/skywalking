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

package org.skywalking.apm.collector.core.data;

/**
 * @author peng-yongsheng
 */
public abstract class Data extends AbstractHashMessage {
    private String[] dataStrings;
    private Long[] dataLongs;
    private Double[] dataDoubles;
    private Integer[] dataIntegers;
    private Boolean[] dataBooleans;
    private byte[][] dataBytes;

    public Data(String id, Column[] stringColumns, Column[] longColumns, Column[] doubleColumns,
        Column[] integerColumns, Column[] booleanColumns, Column[] byteColumns) {
        super(id);
        this.dataStrings[0] = id;
        this.dataStrings = new String[stringColumns.length];
        this.dataLongs = new Long[longColumns.length];
        this.dataDoubles = new Double[doubleColumns.length];
        this.dataIntegers = new Integer[integerColumns.length];
        this.dataBooleans = new Boolean[booleanColumns.length];
        this.dataBytes = new byte[byteColumns.length][];
    }

    protected void setDataString(int position, String value) {
        dataStrings[position] = value;
    }

    protected void setDataLong(int position, Long value) {
        dataLongs[position] = value;
    }

    protected void setDataDouble(int position, Double value) {
        dataDoubles[position] = value;
    }

    protected void setDataInteger(int position, Integer value) {
        dataIntegers[position] = value;
    }

    protected void setDataBoolean(int position, Boolean value) {
        dataBooleans[position] = value;
    }

    protected void setDataBytes(int position, byte[] dataBytes) {
        this.dataBytes[position] = dataBytes;
    }

    protected String getDataString(int position) {
        return dataStrings[position];
    }

    protected Long getDataLong(int position) {
        return dataLongs[position];
    }

    protected Double getDataDouble(int position) {
        return dataDoubles[position];
    }

    protected Integer getDataInteger(int position) {
        return dataIntegers[position];
    }

    protected Boolean getDataBoolean(int position) {
        return dataBooleans[position];
    }

    protected byte[] getDataBytes(int position) {
        return dataBytes[position];
    }

    public String getId() {
        return dataStrings[0];
    }

    @Override public String toString() {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append("string: [");
        for (String dataString : dataStrings) {
            dataStr.append(dataString).append(",");
        }
        dataStr.append("], longs: [");
        for (Long dataLong : dataLongs) {
            dataStr.append(dataLong).append(",");
        }
        dataStr.append("], double: [");
        for (Double dataDouble : dataDoubles) {
            dataStr.append(dataDouble).append(",");
        }
        dataStr.append("], integer: [");
        for (Integer dataInteger : dataIntegers) {
            dataStr.append(dataInteger).append(",");
        }
        dataStr.append("], boolean: [");
        for (Boolean dataBoolean : dataBooleans) {
            dataStr.append(dataBoolean).append(",");
        }
        return dataStr.toString();
    }
}