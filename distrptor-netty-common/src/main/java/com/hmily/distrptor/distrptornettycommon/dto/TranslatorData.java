package com.hmily.distrptor.distrptornettycommon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
public class TranslatorData implements Serializable {
	
	private static final long serialVersionUID = 8763561286199081881L;

	private String id;
	private String name;
	//传输消息体内容
	private String message;

	
}
