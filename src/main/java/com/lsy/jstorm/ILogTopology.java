package com.lsy.jstorm;

/**
 * Created by lisiyu on 2016/11/5.
 */
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;

import java.io.IOException;
import java.util.Properties;

public interface ILogTopology {
    public void start(Properties properties) throws AlreadyAliveException, InvalidTopologyException, InterruptedException, IOException ;
}
