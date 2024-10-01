package com.rnd.springbootgraphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

  @Override
  public GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

    return switch (ex) {
      case UserNotFound unf ->
          prepareGraphQLErrorResponse(
              unf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 400));
      case AddressNotFound anf ->
          prepareGraphQLErrorResponse(
              anf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 400));
      case AuthorizationDeniedException ade ->
          prepareGraphQLErrorResponse(
              ade.getMessage(), ErrorType.FORBIDDEN, env, Map.of("statusCode", 403));
      default ->
          prepareGraphQLErrorResponse(
              "Internal server error",
              ErrorType.INTERNAL_ERROR,
              env,
              Map.of("statusCode", HttpStatusCode.valueOf(500)));
    };
  }

  private GraphQLError prepareGraphQLErrorResponse(
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
