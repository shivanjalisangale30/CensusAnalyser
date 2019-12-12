package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USACensusAdapterTest {

    String USA_CENSUS_DATA = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    String WRONG_FILE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/main/resources/USCensusData.csv";
    String FILE_TYPE_NOT_SUPPORTED = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.json";



    @Test
    public void givenUSACensusData_ShouldReturnCorrectData() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            Map<String, CensusDAO> stringCensusDAOMap = usaCensusAdapter.loadCensusData(USACensusData.class, USA_CENSUS_DATA);
            Assert.assertEquals(51,stringCensusDAOMap.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSACensusData_WhenGivenWrongFile_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(USACensusData.class,WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSACensusData_WhenGivenTypeIsNotSupported_ShouldHandleException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(USACensusData.class,FILE_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenDelimeterImproper_ShouldHandleDelimeterException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(USACensusData.class,USA_CENSUS_DATA);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_whenHeaderImproper_shouldHandleDelimeterException() {
        USACensusAdapter usaCensusAdapter = new USACensusAdapter();
        try {
            usaCensusAdapter.loadCensusData(USACensusData.class,USA_CENSUS_DATA);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION,e.type);
        }

    }
}
