package demo;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class LoggerExample {

	// @formatter:off
	private static final org.apache.commons.logging.Log commonsLoggingLog =
			org.apache.commons.logging.LogFactory.getLog(LoggerExample.class);

	private static final org.slf4j.Logger slf4Jlog =
			org.slf4j.LoggerFactory.getLogger(LoggerExample.class);

	private static final org.jboss.logging.Logger jbossLog =
			org.jboss.logging.Logger.getLogger(LoggerExample.class);

	private static final java.util.logging.Logger javaUtilLog =
			java.util.logging.Logger.getLogger(LoggerExample.class.getName());

	private static final org.apache.log4j.Logger log4jLog =
			org.apache.log4j.Logger.getLogger(LoggerExample.class);

	private static final org.apache.juli.logging.Log tomcatJuliLog =
			org.apache.juli.logging.LogFactory.getLog(LoggerExample.class);
	// @formatter:on

	@PostConstruct
	public void logSomething() {
		commonsLoggingLog.info("Via CommonsLogging");
		slf4Jlog.info("Via SLF4J");
		jbossLog.info("Via JBoss");
		javaUtilLog.info("Via Java Util");
		log4jLog.info("Via LOG4J");
		tomcatJuliLog.info("Via JULI");
		commonsLoggingLog.error("Something bad", new IOException("Comms Down"));
	}

}
