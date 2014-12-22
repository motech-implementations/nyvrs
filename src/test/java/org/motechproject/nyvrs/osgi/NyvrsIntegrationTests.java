package org.motechproject.nyvrs.osgi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Nyvrs bundle integration tests suite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MessageServiceIT.class
})
public class NyvrsIntegrationTests {
}
