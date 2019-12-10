package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_CENSUS_DATA,DELIMETER_EXCEPTION,NO_SUCH_FIELD
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType censusFileProblem, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}
