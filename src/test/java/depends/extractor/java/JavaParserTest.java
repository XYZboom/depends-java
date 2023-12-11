package depends.extractor.java;

import depends.extractor.ParserTest;

public abstract class JavaParserTest extends ParserTest {
    protected String getPackageName() {
        return "";
    }
    public void init() {
        langProcessor = new JavaProcessor();
        super.init();
    }

    public JavaFileParser createParser() {
        return new JavaFileParser(entityRepo, bindingResolver);
    }
}
