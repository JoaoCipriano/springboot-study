package com.joaolucas.study.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        long endTime;
        String invokingLog;
        String executedLog;

        String urlWithParameters = getURLWithParameters(request);

        invokingLog = formatInvokingLog(urlWithParameters);
        log.info(invokingLog);

        filterChain.doFilter(request, response);

        endTime = System.currentTimeMillis() - startTime;
        executedLog = formatExecutedLog(urlWithParameters, endTime);
        log.info(executedLog);
    }

    private String getURLWithParameters(HttpServletRequest request) {
        var url = new StringBuilder();

        url.append(request.getMethod());
        url.append(" ");
        url.append(request.getRequestURL().toString());
        if (!request.getParameterMap().isEmpty()) {
            url.append("?");
            url.append(getParameters(request));
        }
        return url.toString();
    }

    private String getParameters(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();

        var params = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (!params.isEmpty())
                params.append("&");

            params.append(entry.getKey());
            params.append("=");
            params.append(String.join("", entry.getValue()));
        }
        return params.toString();
    }

    private String formatInvokingLog(String urlWithParameters) {
        return "Invoking: " + urlWithParameters;
    }

    private String formatExecutedLog(String urlWithParameters, long time) {
        return "Executed(" + time + " ms): " + urlWithParameters;
    }
}
