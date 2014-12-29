package org.motechproject.nyvrs.service;

import org.motechproject.nyvrs.domain.ClientRegistration;

import java.util.List;

public interface ClientRegistrationService {

    void add(ClientRegistration clientRegistration);

    ClientRegistration findClientRegistrationByNumber(String number);

    List<ClientRegistration> getAll();

    void delete(ClientRegistration clientRegistration);

    void update(ClientRegistration clientRegistration);

    ClientRegistration getById(Long clientRegistrationId);

}
