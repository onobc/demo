# Unable to invoke actuator/bindings when using test binder

https://github.com/spring-cloud/spring-cloud-stream/issues/2956

## Failure

- Edit `pom.xml` - change SC version from `2023.0.1` to `2023.0.2`
- Rebuild
- Run `DemoApplicationTests`

## Success

- Edit `pom.xml` - change SC version from `2023.0.2` to `2023.0.1`
- Rebuild
- Run `DemoApplicationTests`

## Workaround

- Add `/test/resources/META-INF/spring.binders` with following content: 
    
    ```
    integration:\
    org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
    ```
- Rebuild
- Run `DemoApplicationTests`
