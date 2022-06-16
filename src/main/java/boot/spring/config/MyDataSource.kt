package boot.spring.config

import com.alibaba.druid.pool.DruidDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

/**
 * 配置数据源，指定mapper包的扫描路径和SessionFactory对象
 */
@Configuration
@MapperScan(value = ["boot.spring.mapper"], sqlSessionFactoryRef = "sqlSessionFactory")
open class MyDataSource {
    @Value("\${spring.datasource.ssm.url}")
    private val url: String? = null

    @Value("\${spring.datasource.ssm.username}")
    private val username: String? = null

    @Value("\${spring.datasource.ssm.password}")
    private val password: String? = null

    @Value("\${spring.datasource.ssm.driver-class-name}")
    private val driverClassName: String? = null

    @Bean
    open fun dataSource(): DataSource {
        val datasource = DruidDataSource()
        datasource.url = url
        datasource.username = username
        datasource.password = password
        datasource.driverClassName = driverClassName
        return datasource
    }

    @Bean
    @Throws(Exception::class)
    open fun sqlSessionFactory(): SqlSessionFactory {
        // 设置mapper的xml文件路径
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource())
        val resources = PathMatchingResourcePatternResolver()
            .getResources("classpath:mapper/*.xml")
        factoryBean.setMapperLocations(resources)
        // 设置mybatis-config.xml的路径
        val config = PathMatchingResourcePatternResolver()
            .getResource("classpath:mybatis-config.xml")
        factoryBean.setConfigLocation(config)
        return factoryBean.getObject()
    }

    @Bean
    open fun primaryTransactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }

    @Bean
    @Throws(Exception::class)
    open fun sqlSessionTemplate(): SqlSessionTemplate {
        // 使用上面配置的Factory
        return SqlSessionTemplate(sqlSessionFactory())
    }
}