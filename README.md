Logy - is a DSL-based Java logging library
------------------------------------------

Usage:
```java
import static logy.Logy.*;

public class Test {
  public void test() {
		String a[] = {"aaaa", "bbbbb"};
		info("Can't find", quote(upper("test")), "in array", quote(array(a)));
	}
}
```

will print:

<code>
Can't find "TEST" in array [aaaa, bbbbb]
</code>