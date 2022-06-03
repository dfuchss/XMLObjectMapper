# XML Object Mapper
![Maven Deploy (Dev)](https://github.com/dfuchss/XMLObjectMapper/workflows/Maven%20Deploy%20(Dev)/badge.svg)
[![Latest Release](https://img.shields.io/github/release/dfuchss/XMLObjectMapper.svg)](https://github.com/dfuchss/XMLObjectMapper/releases/latest)
[![GitHub issues](https://img.shields.io/github/issues/dfuchss/XMLObjectMapper.svg?style=square)](https://github.com/dfuchss/XMLObjectMapper/issues)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=square)](https://github.com/dfuchss/XMLObjectMapper/blob/master/LICENCE.md)

This project contains a simple object mapper for XML files. Currently, it only supports reading from XML files.

# Maven & Co.
If you want to use maven or some similar tool add the following code to your pom:
```xml
<repositories>
	<repository>
		<id>maven-snapshots</id>
		<url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
	</repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.fuchss</groupId>
    <artifactId>xml-object-mapper</artifactId>
    <version>develop-SNAPSHOT</version>
  </dependency>
</dependencies>
```
