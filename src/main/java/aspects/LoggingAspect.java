package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	@Around("execution(* *(..)) && @annotation(aspects.annotations.Loggable)")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable{
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		
		Object methodReturn = null;
		
		String methodName = joinPoint.getSignature().getName();
		String logMessage = "Invoked method " + methodName;
		logMessage += "\n Args: ";
		for(Object arg:joinPoint.getArgs()) {
			logMessage += arg.toString() + " ";
		}
		
		logger.info(logMessage);
		
		try {
			methodReturn = joinPoint.proceed();
		} catch (Throwable e) {
			logger.error("Method " + methodName + "throws exception", e);
			throw e;
		}
		
		String methodReturnStr = methodReturn == null ? "null" : methodReturn.toString();
		
		logger.info("Method " + methodName + " return " + methodReturnStr);
		
		return methodReturn;
	}
}
