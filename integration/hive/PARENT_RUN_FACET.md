# Parent Run Facet Configuration

The Hive integration supports parent run facets to link child runs to their parent jobs. Configuration can be provided via Hive configuration properties or environment variables (Hive config takes precedence).

## Hive Configuration

```
openlineage.parentRunId=550e8400-e29b-41d4-a716-446655440000
openlineage.parentJobName=parent-job-name
openlineage.parentJobNamespace=parent-namespace
```

## Environment Variables

```
OPENLINEAGE_PARENT_RUN_ID=550e8400-e29b-41d4-a716-446655440000
OPENLINEAGE_PARENT_JOB_NAME=parent-job-name
OPENLINEAGE_PARENT_JOB_NAMESPACE=parent-namespace
```

## Configuration Priority

1. Hive configuration properties are checked first
2. If not found, environment variables are used as fallback
3. All three values must be present for parent run facet to be created

## Example Usage

### Using Hive Configuration
```sql
SET openlineage.parentRunId=550e8400-e29b-41d4-a716-446655440000;
SET openlineage.parentJobName=etl-pipeline;
SET openlineage.parentJobNamespace=production;

-- Your Hive query here
SELECT * FROM my_table;
```

### Using Environment Variables
```bash
export OPENLINEAGE_PARENT_RUN_ID=550e8400-e29b-41d4-a716-446655440000
export OPENLINEAGE_PARENT_JOB_NAME=etl-pipeline
export OPENLINEAGE_PARENT_JOB_NAMESPACE=production

hive -e "SELECT * FROM my_table;"
```

----
SPDX-License-Identifier: Apache-2.0\
Copyright 2018-2025 contributors to the OpenLineage project