package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;

public class JavaVarResolveTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_field_var_should_be_parsed() throws IOException {
        String src = "./src/test/resources/java-code-examples/FieldVar.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity classEntity = entityRepo.getEntity("FieldVar");
        assertEquals(3,((TypeEntity)classEntity).getVars().size()); 
	}
	
	@Test
	public void test_local_var_should_be_parsed() throws IOException {
        String src = "./src/test/resources/java-code-examples/LocalVar.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(1,((TypeEntity)entityRepo.getEntity("LocalVar")).getVars().size());
        assertEquals(1,((FunctionEntity)entityRepo.getEntity("LocalVar.foo")).getVars().size());
	}
	
	@Test
	public void test_local_var_type_could_be_inferred() throws IOException {
        String src = "./src/test/resources/java-code-examples/LocalVarInferExample.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        ContainerEntity e = (ContainerEntity) entityRepo.getEntity("LocalVarInferExample.setExample");
        System.out.println(e.dumpExpressions());
        assertEquals(16,entityRepo.getEntity("LocalVarInferExample.setExample").getRelations().size());
	}
	
	@Test
	public void test_field_access_could_be_inferred() throws IOException {
        String src = "./src/test/resources/java-code-examples/ComplexExpressionExample.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(23,entityRepo.getEntity("test.ComplexExpressionExample.setExample").getRelations().size());
	}
	
	@Test
	public void test_long_static_function_should_be_inferred() throws IOException {
        String src = "./src/test/resources/java-code-examples/LongExpressionWithAbsolutePath.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        System.out.println(((ContainerEntity)(entityRepo.getEntity("x.LongExpressionWithAbsolutePath.setExample"))).dumpExpressions());
        assertEquals(6,entityRepo.getEntity("x.LongExpressionWithAbsolutePath.setExample").getRelations().size());
	}
	
	
}
