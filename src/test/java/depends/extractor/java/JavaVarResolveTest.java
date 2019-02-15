package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;

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
        assertEquals(18,entityRepo.getEntity("LocalVarInferExample.setExample").getRelations().size());
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
        assertEquals(5,entityRepo.getEntity("x.LongExpressionWithAbsolutePath.setExample").getRelations().size());
	}
	
	
	
	@Test
	public void test_call_should_be_referred() throws IOException {
        String src = "./src/test/resources/java-code-examples/ExpressionCallTest.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(16,entityRepo.getEntity("ValidateAll.validate").getRelations().size());
	}
	
	@Test
	public void test_could_detect_type_argument_in_field() throws IOException {
        String src = "./src/test/resources/java-code-examples/TypeArgument.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(2,entityRepo.getEntity("JDepObject").getRelations().size());
	}
	
}
