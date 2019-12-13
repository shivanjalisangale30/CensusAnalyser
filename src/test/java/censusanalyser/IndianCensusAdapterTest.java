package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndianCensusAdapterTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED = "./src/test/resources/IndiaStateCensusData.json";
    String WRONG_CSV_FILE1_PATH = "./src/main/resources/IndiaStateCensusData.csv";

    String INDIA_STATE_CODE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    String PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.json";
    String WRONG_CSV_FILE2_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/main/resources/IndiaStateCode.csv";


    @Test
    public void givenIndiaCensusData_WhenGivenCorrectData_ShouldReturnCount() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> stringCensusDAOMap = indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INIDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29,stringCensusDAOMap.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenGivenOnlyCensusFile_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenGivenOnlyStateCodeFile_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenGivenCensusCorrectOneStateCodeWrongFile_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH,WRONG_CSV_FILE2_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenGivenStateCodeCorrectCensusWrongFile_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,WRONG_CSV_FILE1_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenCensusData_WhenGivenCodeFileTypeIsNotSupported_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH,PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenCensusData_WhenGivenCensusFileTypeIsNotSupported_ShouldHandleException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WhenDelimeterImproper_ShouldHandleDelimeterException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_whenHeaderImproper_shouldHandleDelimeterException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_FILE_ISSUE,e.type);
        }

    }
}
