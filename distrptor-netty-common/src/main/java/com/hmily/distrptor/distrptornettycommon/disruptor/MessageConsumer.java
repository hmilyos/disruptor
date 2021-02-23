package com.hmily.distrptor.distrptornettycommon.disruptor;


import com.hmily.distrptor.distrptornettycommon.dto.TranslatorDataWapper;
import com.lmax.disruptor.WorkHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWapper> {

    protected String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

}
