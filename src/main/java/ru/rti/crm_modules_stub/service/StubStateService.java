package ru.rti.crm_modules_stub.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class StubStateService {
    @Value("${ordering.package.validation.stub.isEnabled}")
    private boolean opvStubIsEnabled;

    @Value("${crm.contact.management.stub.isEnabled}")
    private boolean ccmStubIsEnabled;

    @Value("${ordering.resource.reservation.stub.isEnabled}")
    private boolean orrStubIsEnabled;
}
