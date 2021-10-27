package com.emirates.config.logging;

import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
@Slf4j
public class LoogingAspect {

	@Around("execution(* com.emirates.controller.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();
		Object output;
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().toString();
		ObjectMapper mapper = new ObjectMapper();
		String inputParams = this.getInputArgs(joinPoint, mapper);
		log.info("{} :: {}() - Entry", className, methodName);
		logRequest(inputParams);
		try {
			output = joinPoint.proceed();
			logResponse(output, mapper);
		} finally {
			long elapsedTime = System.currentTimeMillis() - start;
			log.info("{} -> Method execution time: {} milliseconds.", joinPoint.getSignature().getName(),
					elapsedTime);
			log.info("{}.{}() - Exit", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName());
		}
		return output;
	}

	private void logRequest(String inputParams) {
		log.info(
				"---------------------------------------------------------------- REQUEST START ----------------------------------------------------------------");
		log.info(inputParams);
		log.info(
				"---------------------------------------------------------------- REQUEST END    ----------------------------------------------------------------");

	}

	private void logResponse(Object output, ObjectMapper mapper) {
		log.info(
				"---------------------------------------------------------------- RESPONSE START ----------------------------------------------------------------");
		log.info(getResponseObject(output, mapper));
		log.info(
				"---------------------------------------------------------------- RESPONSE END    ----------------------------------------------------------------");
	}

	private String getResponseObject(Object output, ObjectMapper mapper) {
		try {
			if (output instanceof Flux) {
				return mapper.writeValueAsString(
						((Flux<?>) output).toStream().collect(Collectors.toList()));
			}
			if (output instanceof Mono) {
				return mapper.writeValueAsString(((Mono<?>) output).block());
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "Error in Fetching Response Object.";
	}

	private String getInputArgs(ProceedingJoinPoint pjt, ObjectMapper mapper) {
		Object[] array = pjt.getArgs();
		CodeSignature signature = (CodeSignature) pjt.getSignature();

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int i = 0;
		String[] parameterNames = signature.getParameterNames();
		int maxArgs = parameterNames.length;
		for (String name : signature.getParameterNames()) {
			sb.append("[").append(name).append(":");
			try {
				sb.append(mapper.writeValueAsString(array[i])).append("]");
				if (i != maxArgs - 1) {
					sb.append(",");
				}
			} catch (Exception e) {
				sb.append("],");
			}
			i++;
		}
		return sb.append("}").toString();
	}
}
