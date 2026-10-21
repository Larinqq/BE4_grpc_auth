package ganisrum.backend.config;

import ganisrum.backend.authserver.grpc.AuthServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcConfig {

    @Value("${grpc.auth.host}")
    private String authHost;

    @Value("${grpc.auth.port}")
    private int authPort;

    @Bean
    public ManagedChannel authChannel() {
        return ManagedChannelBuilder
                .forAddress(authHost, authPort)
                .keepAliveTime(5, TimeUnit.MINUTES)
                .keepAliveTimeout(20, TimeUnit.SECONDS)
                .usePlaintext()
                .build();
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authStub(
            ManagedChannel channel) {

        return AuthServiceGrpc.newBlockingStub(channel);
    }
}