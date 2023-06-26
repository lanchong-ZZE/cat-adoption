package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Adopt;
import org.apache.ibatis.annotations.Select;

public interface AdoptMapper extends BaseMapper<Adopt> {
    @Select("select * from adopt where userid = #{userid} and adoptstate = 1")
    Adopt getAdpot(Integer userid);
}
