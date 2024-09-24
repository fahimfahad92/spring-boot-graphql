package com.rnd.springbootgraphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

    return switch (ex) {
      case UserNotFound unf ->
          prepareGraphQLError(
              unf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 400));
      case AddressNotFound anf ->
          prepareGraphQLError(
              anf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 400));
      default ->
          prepareGraphQLError(
              "Internal server error",
              ErrorType.INTERNAL_ERROR,
              env,
              Map.of("statusCode", HttpStatusCode.valueOf(500)));
    };
  }

  private GraphQLError prepareGraphQLError(
      String message,
      ErrorType errorType,
      DataFetchingEnvironment env,
      Map<String, Object> extensions) {
    return GraphqlErrorBuilder.newError()
        .errorType(errorType)
        .message(message)
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .extensions(extensions)
        .build();
  }
}
