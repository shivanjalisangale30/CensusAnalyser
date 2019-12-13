package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USACensusAdapterTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String USA_CENSUS_DATA = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    String WRONG_FILE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/main/resources/USCensusData.csv";
    String FILE_TYPE_NOT_SUPPORTED = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.json";
    String DELIMETER_MISSING_FILE = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/DelimeterMissing_USACensusData.csv";
    String HEADER_MISSING_FILE = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/HeaderMissing_USACensusData.csv";
    String EMPTY_FILE = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/EMPTY_FILE.csv";


    @Test
    public void givenUSACensusData_ShouldReturnCorrectData() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            Map<String, CensusDAO> stringCensusDAOMap = usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA, USA_CENSUS_DATA);
            Assert.assertEquals(51,stringCensusDAOMap.size());
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenUSACensusData_WhenGivenWrongFilePath_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA,WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSACensusData_WhenGivenTypeIsNotSupported_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA,FILE_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSACensusData_WhenDelimeterImproper_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA,DELIMETER_MISSING_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenUSACensusData_whenHeaderImproper_shouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA,HEADER_MISSING_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenFile_whenFileIsEmpty_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(CensusAnalyser.Country.USA,EMPTY_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }
}
