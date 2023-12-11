package depends.extractor.java;

import depends.relations.Relation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class JavaPackageTest extends JavaParserTest {
    @Before
    public void setUp() {
        super.init();
    }
    @Override
    protected String getPackageName() {
        return "mypackage.mypackage";
    }

    @Test
    public void shouldHandlePackageSuccess0() throws IOException {
        String[] files = new String[]{
                "src/test/resources/java-code-examples/mypackage/mypackage0/p0/ProviderPackage0.java",
                "src/test/resources/java-code-examples/mypackage/mypackage0/p1/UserPackage0.java"
        };
        JavaFileParser parser = createParser();
        for (String file : files) {
            parser.parse(file);
        }
        resolveAllBindings();
        ArrayList<Relation> relations = entityRepo.getEntity(
                String.format("%s0.p1.UserPackage0.userFunc", getPackageName())
        ).getRelations();
        System.out.println(relations);
    }
}
