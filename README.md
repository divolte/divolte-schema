Divolte Schema
==============

This project contains the default schema used by [Divolte Collector][1]. You can
depend on this if you need to access the default schema.

Usage
-----

Prebuilt JARs are available via Maven Central. The current release version is 0.2.1.

There are two ways to access the schema at runtime. It can be accessed directly via
the Avro-generate record class:

```java
Schema defaultSchema = DefaultEventRecord.getClassSchema()
```

It is also available as the `/DefaultEventRecord.avsc` resource.

License
-------

This project and its artifacts are licensed under the terms of the [Apache License, Version 2.0][2].

  [1]: divolte/divolte-collector                        "Divolte Collector"
  [2]: http://www.apache.org/licenses/LICENSE-2.0.html  "Apache License 2.0"
