package com.chenning.springbootlearn.demonBuild.service.impl;

import com.chenning.springbootlearn.demonBuild.model.Card;
import com.chenning.springbootlearn.demonBuild.mapper.CardMapper;
import com.chenning.springbootlearn.demonBuild.service.CardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-02-03
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {
    @Autowired
    CardMapper mapper;

    @Override
    public List<Card> findAllCard() {
        return mapper.selectList(null);
    }
}
