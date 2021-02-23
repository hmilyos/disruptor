package com.hmily.distrptor.distrptornettycommon.dto;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class TranslatorDataWapper {

	private TranslatorData data;
	
	private ChannelHandlerContext ctx;

	
}
