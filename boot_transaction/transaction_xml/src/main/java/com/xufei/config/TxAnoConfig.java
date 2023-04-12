package com.xufei.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

@Configuration
public class TxAnoConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.database-mysql")
    public DataSource getDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    //如果你不想写transaction.xml文件可以把以下的代码打开

    /*

    @Bean("txManager")
    @DependsOn("dataSource")
    public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //事务拦截器
    @Bean("txAdvice")
    @DependsOn("txManager")
    public 	TransactionInterceptor txAdvice(@Qualifier("txManager")TransactionManager txManager){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        //只读事务，不做更新操作
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        //当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
        //RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap( txMap );
        return new TransactionInterceptor(txManager ,source) ;
    }

    //切面拦截规则 参数会自动从容器中注入
    @Bean
    @DependsOn("txAdvice")
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("txAdvice") TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.xufei.provicer.service.*.*(..))");
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }

 */
}