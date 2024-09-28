// package com.rnd.springbootgraphql.exception;
//
// import org.springframework.http.HttpStatusCode;
// import org.springframework.http.ProblemDetail;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
// @ControllerAdvice
// public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//  @ExceptionHandler(value = {InvalidTokenException.class})
//  protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
//
//    return ResponseEntity.badRequest()
//        .body(prepareCustomErrorResponse(ex.getMessage(), HttpStatusCode.valueOf(401)));
//  }
//
//  private ProblemDetail prepareCustomErrorResponse(String message, HttpStatusCode httpStatusCode)
// {
//    return ProblemDetail.forStatusAndDetail(httpStatusCode, message);
//  }
// }
