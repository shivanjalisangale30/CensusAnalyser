package csvbuilder;

public class CSVBuilderException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType unableToParse) {
     super(message);
     this.type=type;
    }
}
