package com.chenning.springbootlearn.handerJobDemon.service.impl;

import com.chenning.springbootlearn.demonBuild.mapper.CardMapper;
import com.chenning.springbootlearn.demonBuild.model.Card;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.handerJobDemon.DemonModel;
import com.chenning.springbootlearn.handerJobDemon.service.CardInfoGetter;
import com.chenning.springbootlearn.handerJobDemon.vo.CardVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 17:01
 **/
public class CardInfoGetterImpl implements CardInfoGetter {
    @Autowired
    private CardMapper mapper;
    @Override
    public void initCardInfo(DemonModel demonModel) {
        User user=demonModel.getUser();
        Card card = mapper.selectById(user.getUserId());
        CardVo vo=new CardVo();
        vo.setCard(card);
        vo.setCode("添加属性");
        demonModel.setCardVo(vo);
    }
}
