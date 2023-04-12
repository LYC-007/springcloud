package com.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源配置 
 * 提示:如果@Bean后面不指定id,那么默认以方法名字为id
 */
@Configuration
@MapperScan(basePackages = "com.datasource.mapper", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MysqlMasterDatabaseConfig {

	@Bean("masterDataSource")
	/// 根据application.properteis系统配置文件中,对应属性的前缀,指明使用其对应的数据
	@ConfigurationProperties(prefix = "spring.datasource.database-master")
	public DataSource mysqlDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("masterSqlSessionFactory")
	@DependsOn("masterDataSource")
	public SqlSessionFactory masterSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(mysqlDataSource());
		return factoryBean.getObject();
	}

	/**
	 * DefaultSqlSession和SqlSessionTemplate都实现了SqlSession,但我们
	 * 注入线程安全的SqlSessionTemplate,而不使用默认的线程不安全的DefaultSqlSession
	 */
	@Bean
	@DependsOn("masterSqlSessionFactory")
	//@DependsOn注解可以定义在类和方法上，意思是我这个组件要依赖于另一个组件，也就是说被依赖的组件会比该组件先注册到IOC容器中。
	public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory masterSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(masterSqlSessionFactory);
	}

	/** 事务管理器名称 */
	public static final String TX_MANAGER_MASTER = "masterTransactionManager";

	/**
	 * 事务管理器
	 */
	@Bean(TX_MANAGER_MASTER)
	@DependsOn("masterDataSource")
	public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
