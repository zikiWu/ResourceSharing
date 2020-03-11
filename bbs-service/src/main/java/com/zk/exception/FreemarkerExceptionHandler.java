package com.zk.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(FreemarkerExceptionHandler.class);

	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		logger.error("[FreemarkerExceptionHandler][Freemarker Error: " + te.getMessage() + "]");
		try {
			out.write("<meta http-equiv=\"Refresh\" content=\"0;url=/page/error/freemarker_error.html\">");
			//throw new ViewException(ViewException.BASE_FREEMARKER_EXCEPTION,"Freemarker 解析错误");
		} catch (IOException e) {
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}
}