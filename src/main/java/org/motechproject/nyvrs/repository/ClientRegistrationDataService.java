package org.motechproject.nyvrs.repository;


import org.motechproject.mds.annotations.Lookup;
import org.motechproject.mds.annotations.LookupField;
import org.motechproject.mds.service.MotechDataService;
import org.motechproject.nyvrs.domain.ClientRegistration;

public interface ClientRegistrationDataService extends MotechDataService<ClientRegistration> {

    @Lookup
    ClientRegistration findClientRegistrationByNumber(@LookupField(name = "number") String number);

}
