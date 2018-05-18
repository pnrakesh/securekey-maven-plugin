# securekey-maven-plugin

This plugin is used to generate a AES key using `javax.crypto.SecretKey` which is then Base64 encoded during build time and store it in the file system.
The "secret" to generate the key is passed as a parameter in the plugin configuration

![JDk8](https://camo.githubusercontent.com/96ab5485a7f1b4b9aa2b2fae1113ba9c9346bfbc/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a444b2d312e382d79656c6c6f772e737667) [![Build Status](https://travis-ci.org/agogs/securekey-maven-plugin.svg?branch=master)](https://travis-ci.org/agogs/securekey-maven-plugin)

This plugin is available on [maven central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.agogs%22%20AND%20a%3A%22securekey-maven-plugin%22)

**Apache Maven**

```
<plugins>
    .
    .
    .
    <plugin>
        <groupId>com.github.agogs</groupId>
        <artifactId>securekey-maven-plugin</artifactId>
        <executions>
            <execution>
                <configuration>
                    <keySize>256</keySize>                      <!-- optional, default is 128 bits-->
                    <algorithm>AES</algorithm>
                    <secret>secret</secret>                     <!-- optional, default = random string -->
                    <fileName>key.properties</fileName>         <!-- optional, default = securekey.properties -->
                    <filePath>/path/to/file</filePath>          <!-- optional, default is project root-->
                    <propertyName>property.name</propertyName>  <!-- optional, default = secure.key.encoded -->
                </configuration>
            </execution>
        </executions>
    </plugin>
    .
    .
    .
</plugins>
```



