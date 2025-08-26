# Apache Hive Integration

This document provides an overview of the OpenLineage Apache Hive integration.

## Overview

The OpenLineage Hive integration captures lineage metadata from Hive queries using JVM instrumentation. It automatically tracks:

- Input and output datasets
- Schema information
- Query execution details
- Parent-child job relationships

## Features

- **Automatic Lineage Capture**: No code changes required
- **Parent Run Facets**: Link child runs to parent jobs
- **Dual Configuration**: Support for both Hive config and environment variables
- **Schema Tracking**: Captures table and column metadata
- **Query Metadata**: Records SQL queries and execution context

## Configuration Methods

### 1. Hive Configuration Properties
```
openlineage.parentRunId=<uuid>
openlineage.parentJobName=<job-name>
openlineage.parentJobNamespace=<namespace>
```

### 2. Environment Variables
```
OPENLINEAGE_PARENT_RUN_ID=<uuid>
OPENLINEAGE_PARENT_JOB_NAME=<job-name>
OPENLINEAGE_PARENT_JOB_NAMESPACE=<namespace>
```

## Compatibility

- **Hive Versions**: 2.3+
- **Java**: 8+
- **OpenLineage**: 1.0+

## Documentation

- [Parent Run Facet Configuration](./hive/PARENT_RUN_FACET.md)
- [Main Hive Integration](./hive/README.md)

----
SPDX-License-Identifier: Apache-2.0\
Copyright 2018-2025 contributors to the OpenLineage project