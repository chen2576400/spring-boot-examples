package com.chenning.springbootlearn.demonBuild.mapper;

import com.chenning.springbootlearn.demonBuild.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-02-02
 */
//@Repository需要在Spring中配置扫描地址，然后生成Dao层的Bean才能被注入到Service层中。
//@Mapper不需要配置扫描地址，通过xml里面的namespace里面的接口地址，生成了Bean后注入到Service层中。
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
