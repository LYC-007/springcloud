package com.datasource.service;


import com.datasource.config.MysqlMasterDatabaseConfig;
import com.datasource.config.MysqlSlaveDatabaseConfig;
import com.datasource.dao.StudentsMapper;
import com.datasource.mapper.UserMapper;
import com.datasource.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.TimeUnit;

@Service
public class TransactionMangerService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentsMapper studentsMapper;
    @Autowired
    @Qualifier(MysqlMasterDatabaseConfig.TX_MANAGER_MASTER)
    private DataSourceTransactionManager masterTxManager;

    @Autowired
    @Qualifier(MysqlSlaveDatabaseConfig.TX_MANAGER_SLAVE)
    private DataSourceTransactionManager slaveTxManager;

    public void SlaveAndMasterInsert() {
        // 操作前的 master status
        DefaultTransactionDefinition masterDef = new DefaultTransactionDefinition();
        masterDef.setName("master-tx");
        masterDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus masterStatus = masterTxManager.getTransaction(masterDef);

        // 操作前的 slave status
        DefaultTransactionDefinition slaveDef = new DefaultTransactionDefinition();
        slaveDef.setName("slave-tx");
        slaveDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus slaveStatus = slaveTxManager.getTransaction(slaveDef);
        try {
            // 业务逻辑
            userMapper.save(UserUtil.createUser());
            studentsMapper.save(UserUtil.createUser());

            // 提交(后定义的status需要先commit)
            slaveTxManager.commit(slaveStatus);
            masterTxManager.commit(masterStatus);


        } catch (Exception e) {
            // 回滚(后定义的status需要先rollback)
            slaveTxManager.rollback(slaveStatus);
            masterTxManager.rollback(masterStatus);
            throw e;
        }

    }
}
