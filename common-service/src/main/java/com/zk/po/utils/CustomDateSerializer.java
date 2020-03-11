package com.zk.po.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zk.po.enums.DateTimePatternEnum;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description 自定义返回JSON 数据格中日期格式化处理
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, 
			JsonGenerator jsonGenerator,
			SerializerProvider provider)
			throws IOException, JsonProcessingException {
			SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
			jsonGenerator.writeString(DateUtil.friendly_time(sdf.format(value)));
	}
}
