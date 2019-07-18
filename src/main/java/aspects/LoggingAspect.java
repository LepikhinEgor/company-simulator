package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* *(..)) && @annotation(aspects.annotations.Loggable)")
	public void logBefore(JoinPoint joinPoint) {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		
		String logMessage = "Invoked method " + joinPoint.getSignature().getName();
		logMessage += "\n Args: ";
		for(Object arg:joinPoint.getArgs()) {
			logMessage += arg.toString() + " ";
		}
		
		logger.info(logMessage);
	}
}
