# securekey-maven-plugin

This plugin is used to generate a AES key using `sun.security.ssl.SecureKey` which is then Base64 encoded during build time and store it in the file system.
The "secret" to generate the key is passed as a parameter in the plugin configuration

This plugin is available on [maven central](http://search.maven.org/#artifactdetails%7Ccom.github.agogs%7Csecurekey-maven-plugin%7C1.0.2%7Cmaven-plugin)

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



