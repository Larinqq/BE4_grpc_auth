package ahaidewi.authserver.grpc.exception;


import io.grpc.Status;
import io.grpc.StatusException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class GlobalGrpcExceptionHandler implements GrpcExceptionHandler {

  @Override
  public @Nullable StatusException handleException(@NonNull Throwable exception) {
    return switch (exception) {
      case IllegalArgumentException illegalArgumentException -> Status.INVALID_ARGUMENT
          .withDescription(illegalArgumentException.getMessage())
          .asException();
      case EntityAlreadyExistsException entityAlreadyExistsException -> Status.ALREADY_EXISTS
          .withDescription(entityAlreadyExistsException.getMessage())
          .asException();
      case NoSuchElementException noSuchElementException -> Status.NOT_FOUND
          .withDescription(noSuchElementException.getMessage())
          .asException();
      case BadCredentialsException badCredentialsException -> Status.UNAUTHENTICATED
          .withDescription(badCredentialsException.getMessage())
          .asException();
      default -> Status.INTERNAL
          .withDescription("Internal server error")
          .asException();
    };

  }
}

//Успех (Success)0 OK: Операция завершилась успешно. Аналог 200 OK в HTTP.
// Ошибки на стороне клиента (Client-Side Errors)
// 1 CANCELLED: Операция отменена клиентом или вызывающей стороной.
// 3 INVALID_ARGUMENT: Клиент передал невалидные аргументы. Ошибка в самих данных.
// 5 NOT_FOUND: Запрашиваемый ресурс не найден. Аналог 404 Not Found.
// 6 ALREADY_EXISTS: Ресурс, который клиент пытается создать, уже существует. Аналог 409 Conflict.
// 7 PERMISSION_DENIED: У клиента нет прав на выполнение этого действия. Аутентификация пройдена, но доступ ограничен.
// 11 OUT_OF_RANGE: Запрос вышел за допустимые пределы (например, смещение в файле за пределами его размера).
// 16 UNAUTHENTICATED: Клиент не прошел аутентификацию. Отсутствуют или неверны токены/ключи доступа. Аналог 401 Unauthorized.
// Ошибки на стороне сервера (Server-Side Errors)
// 2 UNKNOWN: Неизвестная ошибка сервера. Обычно возникает, если код приложения выбросил исключение, которое не было обработано gRPC.
// 8 RESOURCE_EXHAUSTED: Исчерпаны ресурсы. Либо у сервера закончилась память/место, либо сработал лимит на количество запросов (Rate Limit).
// 12 UNIMPLEMENTED: Метод или сервис не реализованы или не включены на сервере. Аналог 501 Not Implemented.
// 13 INTERNAL: Внутренняя ошибка системы. Означает поломку базовой инфраструктуры сервера.
// 15 DATA_LOSS: Необратимая потеря или повреждение данных во время выполнения операции.
// Ошибки сети и тайм-аутов (Network & Timeout Errors)
// 4 DEADLINE_EXCEEDED: Время ожидания операции истекло до её завершения. Аналог 504 Gateway Timeout.
// 9 FAILED_PRECONDITION: Операция отклонена, так как система находится в неверном состоянии (например, попытка удалить непустую директорию).
// 10 ABORTED: Операция прервана из-за конфликта параллельных транзакций (стоит попробовать еще раз).
// 14 UNAVAILABLE: Сервис временно недоступен. Сервер перегружен, перезапускается или лежит сеть.
