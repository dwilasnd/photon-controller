/*
 * Copyright 2016 VMware, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.vmware.photon.controller.apife.lib;

import com.vmware.photon.controller.apife.backends.HostBackend;
import com.vmware.photon.controller.apife.config.ImageConfig;
import com.vmware.photon.controller.common.clients.HostClientFactory;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;

/**
 * Test {@link ImageStoreFactory}.
 */
public class ImageStoreFactoryTest {

  /**
   * Dummy test case to make Intellij recognize this as a test class.
   */
  @Test(enabled = false)
  private void dummy() {
  }

  /**
   * Tests the create method.
   */
  public class CreateTest {

    private HostBackend hostBackend;
    private HostClientFactory hostClientFactory;

    private ImageConfig config;

    @BeforeMethod
    public void setUp() {
      hostBackend = mock(HostBackend.class);
      hostClientFactory = mock(HostClientFactory.class);

      config = new ImageConfig();
      config.setDatastore("ds");
    }

    @Test
    public void testLocalStore() {
      ImageStoreFactory factory = new ImageStoreFactory(hostBackend, hostClientFactory, config);
      assertThat(factory.create(), instanceOf(LocalImageStore.class));
    }

    @Test
    public void testVsphereStore() {
      config.setUseEsxStore(true);

      ImageStoreFactory factory = new ImageStoreFactory(hostBackend, hostClientFactory, config);
      assertThat(factory.create(), instanceOf(VsphereImageStore.class));
    }
  }
}
