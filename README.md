# Cron Expression Parser

[![Build](https://github.com/michaelruocco/cron-expression-parser-java/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/cron-expression-parser-java/actions)
[![codecov](https://codecov.io/gh/michaelruocco/cron-expression-parser-java/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/cron-expression-parser-java)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/cron-expression-parser-java/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/cron-expression-parser-java&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/cron-expression-parser-java?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_cron-expression-parser-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_cron-expression-parser-java)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_cron-expression-parser-java&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_cron-expression-parser-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_cron-expression-parser-java&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_cron-expression-parser-java)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_cron-expression-parser-java&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_cron-expression-parser-java)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/cron-expression-parser-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22cron-expression-parser-java%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

Parses Cron Expressions of the following format:

*   (Minute) (hour) (day of month) (month) (day of week) (command)
*   `*` means all possible time units
*   `-` a range of time units
*   `,` a comma separated list of individual time units
*   `/` intervals time units, the left value is the starting value and the right value is the max value

For example given the input argument:

`3,45/15 0 1,15 * 1-5 /usr/bin/find`

The output should be:

```zsh
minute        3 18 33 45 48
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Running from gradle

`./gradlew run --args="3,45/15 0 1,15 * 1-5 /usr/bin/find"`

or

`./gradlew run --args="-arguments 3,45/15 0 1,15 * 1-5 /usr/bin/find"`

## Running built jar

The build will build two jars:

1.  A plain jar that is built for use a library at build/libs/cron-parser.jar
2.  A shadow jar that is built for running as a standalone application build/libs/cron-parser-all.jar

To run the shadow jar if you are planning to pass any arguments with `*` notation you may
need to configure your terminal to disable globbing. I found that using zsh shell on my mac this was
and issue. If I try to run the following command:

`java -jar build/libs/cron-expression-parser-java-{version}-all.jar 3,45/15 0 1,15 * 1-5 /usr/bin/find`

or

`java -jar build/libs/cron-expression-parser-java-{version}-all.jar -arguments 3,45/15 0 1,15 * 1-5 /usr/bin/find`

Then the following output is returned:

`notation parser not found for value gradlew.bat`

As you can see the * command has been expanded to include all the files and folders
from the current directory where the command is being run from. This can be fixed by
running the following command to disable globbing:

`set -o noglob`

Once globbing has been disabled, when the jar command list above is run again the following output will
be displayed:

```zsh
minute        3 18 33 45 48
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates lintGradle spotlessApply build
```

## Commit History

The code in this project was originally written in [this exercises repo](https://github.com/michaelruocco/exercises)
it was extracted out into its this repo once it became reasonably. The original commit history can be found there.

## Using Published Versions

A new version of the parser is published on each successful pipeline run against the
master branch. You can download the runnable jar using the following URL (with the latest version inserted)

`https://repo1.maven.org/maven2/com/github/michaelruocco/cron-expression-parser-java/{version}/cron-expression-parser-java-{version}-all.jar`

When the jar is downloaded you can run it using the following command:

`cron-expression-parser-java-{version}-all.jar 3,45/15 0 1,15 * 1-5 /usr/bin/find`

The library jar can also be used in a maven or gradle project using the standard dependency definitions outlined

`https://search.maven.org/artifact/com.github.michaelruocco/cron-expression-parser-java`