package com.marketplace.accounts;

import com.marketplace.accounts.domain.Provider;
import com.marketplace.accounts.repository.ProviderRepository;
import com.marketplace.accounts.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {

    @Mock
    ProviderRepository repository;

    @InjectMocks
    ProviderService service;

    @Test
    void registerSavesProvider() {
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Provider provider = service.register("Joe's Garage", List.of("oil-change"), 40.0, -70.0);
        assertThat(provider.getName()).isEqualTo("Joe's Garage");
        verify(repository).save(any());
    }
}
