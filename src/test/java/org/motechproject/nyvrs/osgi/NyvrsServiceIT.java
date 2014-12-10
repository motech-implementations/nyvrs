package org.motechproject.nyvrs.osgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.nyvrs.service.NyvrsService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

/**
 * Verify that NyvrsService present, functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class NyvrsServiceIT extends BasePaxIT {

    @Inject
    private NyvrsService nyvrsService;

    @Test
    public void testNyvrsServicePresent() throws Exception {
        assertNotNull(nyvrsService);
    }
}
