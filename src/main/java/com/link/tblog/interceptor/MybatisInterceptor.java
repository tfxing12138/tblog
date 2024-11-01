package com.link.tblog.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.link.tblog.utils.MyClassUtils;
import com.link.tblog.utils.SnowflakeIdGeneratorUtil;
import com.link.tblog.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器，自动注入创建人、修改人
 *
 * @Author pj
 * @Date 2019-01-19
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {

    /**
     * 解析表名
     * @param mappedStatement
     * @param invocation
     * @return
     */
    private String parseTableNameFromSql(MappedStatement mappedStatement, Invocation invocation) {

        BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
        String sql = boundSql.getSql();
        if (StringUtils.isNotBlank(sql)) {
            // 简单的解析 SQL 语句中的表名，这里只是示例，实际情况可能更复杂
            // 假设表名位于 FROM 关键字之后的第一个单词
            String[] tokens = sql.split("\\s+");
            for (int i = 0; i < tokens.length; i++) {
                if ("FROM".equalsIgnoreCase(tokens[i]) && i + 1 < tokens.length) {
                    return tokens[i + 1];
                }
                if ("UPDATE".equalsIgnoreCase(tokens[i]) && i + 1 < tokens.length) {
                    return tokens[i + 1];
                }
                if ("INTO".equalsIgnoreCase(tokens[i]) && i + 1 < tokens.length) {
                    return tokens[i + 1];
                }
            }
        }
        return null;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        log.debug("------sqlId------" + sqlId);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        log.debug("------sqlCommandType------" + sqlCommandType);

        if (parameter == null) {
            return invocation.proceed();
        }
        if (SqlCommandType.INSERT == sqlCommandType) {
            Field[] fields = MyClassUtils.getAllFields(parameter);
            for (Field field : fields) {
                try {
                    if("id".equalsIgnoreCase(field.getName())) {
                        field.setAccessible(true);
                        Object local_createBy = field.get(parameter);
                        field.setAccessible(false);
                        if (local_createBy == null) {
                            field.setAccessible(true);
                            field.set(parameter, UuidUtils.generateId());
                            field.setAccessible(false);
                        }
                    }

                    if ("insertedBy".equals(field.getName()) || "updatedBy".equals(field.getName())) {
                        field.setAccessible(true);
                        Object local_createBy = field.get(parameter);
                        field.setAccessible(false);
                        if (local_createBy == null || local_createBy.equals("")) {
                            field.setAccessible(true);
                            field.set(parameter, "SYS");
                            field.setAccessible(false);
                        }
                    }

                    // 注入创建时间
                    if ("insertTime".equals(field.getName())) {
                        field.setAccessible(true);
                        Object local_createDate = field.get(parameter);
                        field.setAccessible(false);
                        if (local_createDate == null || local_createDate.equals("")) {
                            field.setAccessible(true);
                            field.set(parameter, new Date());
                            field.setAccessible(false);
                        }
                    }
                    if ("updateTime".equals(field.getName())) {
                        field.setAccessible(true);
                        Object local_createDate = field.get(parameter);
                        field.setAccessible(false);
                        if (local_createDate == null || local_createDate.equals("")) {
                            field.setAccessible(true);
                            field.set(parameter, new Date());
                            field.setAccessible(false);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        if (SqlCommandType.UPDATE == sqlCommandType) {
            Field[] fields = null;
            if (parameter instanceof ParamMap) {
                ParamMap<?> p = (ParamMap<?>) parameter;
                if (p.containsKey("et")) {
                    parameter = p.get("et");
                } else {
                    parameter = p.get("param1");
                }
                if (parameter == null) {
                    return invocation.proceed();
                }
                fields = MyClassUtils.getAllFields(parameter);
            } else {
                fields = MyClassUtils.getAllFields(parameter);
            }
            for (Field field : fields) {
                try {
                    if ("updatedBy".equals(field.getName())) {
                        field.setAccessible(true);
                        field.set(parameter, "SYS");
                        field.setAccessible(false);
                    }
                    if ("updateTime".equals(field.getName())) {
                        field.setAccessible(true);
                        field.set(parameter, new Date());
                        field.setAccessible(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
