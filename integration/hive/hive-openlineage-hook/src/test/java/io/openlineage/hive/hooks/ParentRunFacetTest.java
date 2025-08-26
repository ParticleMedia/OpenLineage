/*
/* Copyright 2018-2025 contributors to the OpenLineage project
/* SPDX-License-Identifier: Apache-2.0
*/
package io.openlineage.hive.hooks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.openlineage.client.OpenLineage;
import io.openlineage.hive.api.OpenLineageContext;
import io.openlineage.hive.client.HiveOpenLineageConfig;
import io.openlineage.hive.client.Versions;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.hooks.HookContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ParentRunFacetTest {

  @Mock private HookContext hookContext;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    clearEnvironmentVariables();
  }

  @AfterEach
  public void tearDown() {
    clearEnvironmentVariables();
  }

  @Test
  public void testParentRunFacetCreationFromHiveConfig() {
    HiveConf conf = new HiveConf();
    conf.set("openlineage.parentRunId", "550e8400-e29b-41d4-a716-446655440000");
    conf.set("openlineage.parentJobName", "parent-job");
    conf.set("openlineage.parentJobNamespace", "parent-namespace");
    when(hookContext.getConf()).thenReturn(conf);

    OpenLineage openLineage = new OpenLineage(Versions.OPEN_LINEAGE_PRODUCER_URI);
    OpenLineageContext olContext = OpenLineageContext.builder()
        .openLineage(openLineage)
        .openLineageConfig(mock(HiveOpenLineageConfig.class))
        .hookContext(hookContext)
        .readEntities(Collections.emptySet())
        .writeEntities(Collections.emptySet())
        .eventTime(ZonedDateTime.now())
        .eventType(OpenLineage.RunEvent.EventType.COMPLETE)
        .build();

    try {
      java.lang.reflect.Method method =
          Faceting.class.getDeclaredMethod("getParentRunFacet", OpenLineageContext.class);
      method.setAccessible(true);
      Optional<OpenLineage.ParentRunFacet> result =
          (Optional<OpenLineage.ParentRunFacet>) method.invoke(null, olContext);

      assertTrue(result.isPresent());
      OpenLineage.ParentRunFacet parentFacet = result.get();
      assertNotNull(parentFacet.getRun());
      assertNotNull(parentFacet.getJob());
      assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), parentFacet.getRun().getRunId());
      assertEquals("parent-job", parentFacet.getJob().getName());
      assertEquals("parent-namespace", parentFacet.getJob().getNamespace());
    } catch (Exception e) {
      fail("Failed to invoke getParentRunFacet method: " + e.getMessage());
    }
  }

  @Test
  public void testParentRunFacetCreationFromEnvVars() {
    setEnvironmentVariable("OPENLINEAGE_PARENT_RUN_ID", "550e8400-e29b-41d4-a716-446655440000");
    setEnvironmentVariable("OPENLINEAGE_PARENT_JOB_NAME", "parent-job");
    setEnvironmentVariable("OPENLINEAGE_PARENT_JOB_NAMESPACE", "parent-namespace");

    HiveConf conf = new HiveConf();
    when(hookContext.getConf()).thenReturn(conf);

    OpenLineage openLineage = new OpenLineage(Versions.OPEN_LINEAGE_PRODUCER_URI);
    OpenLineageContext olContext = OpenLineageContext.builder()
        .openLineage(openLineage)
        .openLineageConfig(mock(HiveOpenLineageConfig.class))
        .hookContext(hookContext)
        .readEntities(Collections.emptySet())
        .writeEntities(Collections.emptySet())
        .eventTime(ZonedDateTime.now())
        .eventType(OpenLineage.RunEvent.EventType.COMPLETE)
        .build();

    try {
      java.lang.reflect.Method method =
          Faceting.class.getDeclaredMethod("getParentRunFacet", OpenLineageContext.class);
      method.setAccessible(true);
      Optional<OpenLineage.ParentRunFacet> result =
          (Optional<OpenLineage.ParentRunFacet>) method.invoke(null, olContext);

      assertTrue(result.isPresent());
      OpenLineage.ParentRunFacet parentFacet = result.get();
      assertNotNull(parentFacet.getRun());
      assertNotNull(parentFacet.getJob());
      assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), parentFacet.getRun().getRunId());
      assertEquals("parent-job", parentFacet.getJob().getName());
      assertEquals("parent-namespace", parentFacet.getJob().getNamespace());
    } catch (Exception e) {
      fail("Failed to invoke getParentRunFacet method: " + e.getMessage());
    }
  }

  @Test
  public void testParentRunFacetMissingConfig() {
    HiveConf conf = new HiveConf();
    when(hookContext.getConf()).thenReturn(conf);

    OpenLineage openLineage = new OpenLineage(Versions.OPEN_LINEAGE_PRODUCER_URI);
    OpenLineageContext olContext = OpenLineageContext.builder()
        .openLineage(openLineage)
        .openLineageConfig(mock(HiveOpenLineageConfig.class))
        .hookContext(hookContext)
        .readEntities(Collections.emptySet())
        .writeEntities(Collections.emptySet())
        .eventTime(ZonedDateTime.now())
        .eventType(OpenLineage.RunEvent.EventType.COMPLETE)
        .build();

    try {
      java.lang.reflect.Method method =
          Faceting.class.getDeclaredMethod("getParentRunFacet", OpenLineageContext.class);
      method.setAccessible(true);
      Optional<OpenLineage.ParentRunFacet> result =
          (Optional<OpenLineage.ParentRunFacet>) method.invoke(null, olContext);

      assertFalse(result.isPresent());
    } catch (Exception e) {
      fail("Failed to invoke getParentRunFacet method: " + e.getMessage());
    }
  }

  @Test
  public void testParentRunFacetInvalidUuid() {
    HiveConf conf = new HiveConf();
    conf.set("openlineage.parentRunId", "invalid-uuid");
    conf.set("openlineage.parentJobName", "parent-job");
    conf.set("openlineage.parentJobNamespace", "parent-namespace");
    when(hookContext.getConf()).thenReturn(conf);

    OpenLineage openLineage = new OpenLineage(Versions.OPEN_LINEAGE_PRODUCER_URI);
    OpenLineageContext olContext = OpenLineageContext.builder()
        .openLineage(openLineage)
        .openLineageConfig(mock(HiveOpenLineageConfig.class))
        .hookContext(hookContext)
        .readEntities(Collections.emptySet())
        .writeEntities(Collections.emptySet())
        .eventTime(ZonedDateTime.now())
        .eventType(OpenLineage.RunEvent.EventType.COMPLETE)
        .build();

    try {
      java.lang.reflect.Method method =
          Faceting.class.getDeclaredMethod("getParentRunFacet", OpenLineageContext.class);
      method.setAccessible(true);
      Optional<OpenLineage.ParentRunFacet> result =
          (Optional<OpenLineage.ParentRunFacet>) method.invoke(null, olContext);

      assertFalse(result.isPresent());
    } catch (Exception e) {
      fail("Failed to invoke getParentRunFacet method: " + e.getMessage());
    }
  }

  @Test
  public void testParentRunFacetHiveConfigTakesPrecedence() {
    setEnvironmentVariable("OPENLINEAGE_PARENT_RUN_ID", "env-run-id");
    setEnvironmentVariable("OPENLINEAGE_PARENT_JOB_NAME", "env-job");
    setEnvironmentVariable("OPENLINEAGE_PARENT_JOB_NAMESPACE", "env-namespace");

    HiveConf conf = new HiveConf();
    conf.set("openlineage.parentRunId", "550e8400-e29b-41d4-a716-446655440000");
    conf.set("openlineage.parentJobName", "hive-job");
    conf.set("openlineage.parentJobNamespace", "hive-namespace");
    when(hookContext.getConf()).thenReturn(conf);

    OpenLineage openLineage = new OpenLineage(Versions.OPEN_LINEAGE_PRODUCER_URI);
    OpenLineageContext olContext = OpenLineageContext.builder()
        .openLineage(openLineage)
        .openLineageConfig(mock(HiveOpenLineageConfig.class))
        .hookContext(hookContext)
        .readEntities(Collections.emptySet())
        .writeEntities(Collections.emptySet())
        .eventTime(ZonedDateTime.now())
        .eventType(OpenLineage.RunEvent.EventType.COMPLETE)
        .build();

    try {
      java.lang.reflect.Method method =
          Faceting.class.getDeclaredMethod("getParentRunFacet", OpenLineageContext.class);
      method.setAccessible(true);
      Optional<OpenLineage.ParentRunFacet> result =
          (Optional<OpenLineage.ParentRunFacet>) method.invoke(null, olContext);

      assertTrue(result.isPresent());
      OpenLineage.ParentRunFacet parentFacet = result.get();
      assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), parentFacet.getRun().getRunId());
      assertEquals("hive-job", parentFacet.getJob().getName());
      assertEquals("hive-namespace", parentFacet.getJob().getNamespace());
    } catch (Exception e) {
      fail("Failed to invoke getParentRunFacet method: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private void setEnvironmentVariable(String key, String value) {
    try {
      Map<String, String> env = System.getenv();
      Class<?> cl = env.getClass();
      Field field = cl.getDeclaredField("m");
      field.setAccessible(true);
      Map<String, String> writableEnv = (Map<String, String>) field.get(env);
      writableEnv.put(key, value);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to set environment variable", e);
    }
  }

  @SuppressWarnings("unchecked")
  private void clearEnvironmentVariables() {
    try {
      Map<String, String> env = System.getenv();
      Class<?> cl = env.getClass();
      Field field = cl.getDeclaredField("m");
      field.setAccessible(true);
      Map<String, String> writableEnv = (Map<String, String>) field.get(env);
      writableEnv.remove("OPENLINEAGE_PARENT_RUN_ID");
      writableEnv.remove("OPENLINEAGE_PARENT_JOB_NAME");
      writableEnv.remove("OPENLINEAGE_PARENT_JOB_NAMESPACE");
    } catch (Exception e) {
      // Ignore cleanup errors
    }
  }
}
