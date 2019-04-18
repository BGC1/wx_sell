package com.example.wx_sell;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    @Bean(value = "druidDataSource",initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.druid")
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        //Lists.newArrayList() 相当于new ArrayList()
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }

    //配置过滤的数据
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(5);
        statFilter.setMergeSql(true);
        return statFilter;
    }

    //配置访问路径
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        //localhost：8888/sell/druid
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }

}
