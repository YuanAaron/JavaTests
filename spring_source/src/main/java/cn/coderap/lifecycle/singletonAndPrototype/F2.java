package cn.coderap.lifecycle.singletonAndPrototype;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class F2 {
}
