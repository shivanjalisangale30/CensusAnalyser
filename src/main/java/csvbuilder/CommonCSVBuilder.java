package csvbuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CommonCSVBuilder implements ICSVBuilder {
    @Override
    public Iterator getCSVFileIterartor(Reader reader, Class csvClass) throws CSVBuilderException {
        return null;
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return null;
    }
}
