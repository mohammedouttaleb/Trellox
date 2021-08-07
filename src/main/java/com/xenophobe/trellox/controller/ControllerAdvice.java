package com.xenophobe.trellox.controller;


import com.xenophobe.trellox.dto.ErrorResponseDto;
import com.xenophobe.trellox.exception.InvalidCredentialsException;
import com.xenophobe.trellox.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@org.springframework.web.bind.annotation.ControllerAdvice(assignableTypes = UserController.class)
public class ControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDto> handleUserException(
            UserException exception)
    {
        return  ResponseEntity.status(409).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserException(
            InvalidCredentialsException exception)
    {
        return  ResponseEntity.status(401).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleArgumentNotValidException(
            MethodArgumentNotValidException exception)
    {
        return  ResponseEntity.status(400).body(new ErrorResponseDto("MethodArgumentNotValidException", exception.getMessage()));
    }

 /*   @ExceptionHandler(MonitoringDashboardAlreadyExistsException.class)
    public ResponseEntity<MonitoringErrorResponseDto> handleDashboardAlreadyExistsException(
            MonitoringDashboardAlreadyExistsException exception,
            @RequestAttribute(AwsHttpUtils.REQUEST_CONTEXT_ATTRIBUTE) RequestContext requestContext) {
        return handleMonitoringException(requestContext, 409, exception, this::enrichErrorDto);
    }

    @ExceptionHandler(MonitoringMetricAlarmNotFoundException.class)
    public ResponseEntity<MonitoringErrorResponseDto> handleMetricAlarmNotFoundException(
            MonitoringMetricAlarmNotFoundException exception,
            @RequestAttribute(AwsHttpUtils.REQUEST_CONTEXT_ATTRIBUTE) RequestContext requestContext) {
        return handleMonitoringException(requestContext, 409, exception, this::enrichErrorDto);
    }



    private <E extends MonitoringException> ResponseEntity<MonitoringErrorResponseDto> handleMonitoringException(
            RequestContext requestContext, int statusCode, E exception,
            BiConsumer<MonitoringErrorResponseDtoBuilder, E> enrichError) {
        MonitoringErrorResponseDtoBuilder errorDtoBuilder = new MonitoringErrorResponseDtoBuilder()
                .setCode(exception.getCode())
                .setMessage(exception.getMsg())
                .setRequestId(requestContext.requestId())
                .setHostId(requestContext.hostId());
        enrichError.accept(errorDtoBuilder, exception);
        MonitoringErrorResponseDto errorResponseDto = errorDtoBuilder.build();
        LOG.debug("error response {}", errorResponseDto);
        return ResponseEntity
                .status(statusCode)
                .contentType(MediaType.APPLICATION_XML)
                .body(errorResponseDto);
    }
    */
    /*
    private void enrichErrorDto(MonitoringErrorResponseDtoBuilder errorDtoBuilder, MonitoringDashboardNotFoundException exception) {
        errorDtoBuilder.setDashboardName(exception.getDashboardName());
    }

    private void enrichErrorDto(MonitoringErrorResponseDtoBuilder errorDtoBuilder, MonitoringDashboardAlreadyExistsException exception) {
        errorDtoBuilder.setDashboardName(exception.getDashboardName());
    }
    private void enrichErrorDto(MonitoringErrorResponseDtoBuilder errorDtoBuilder, MonitoringMetricAlarmNotFoundException exception) { }
*/
}

