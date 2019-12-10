package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED = "./src/test/resources/IndiaStateCensusData.json";
    String WRONG_CSV_FILE1_PATH = "./src/main/resources/IndiaStateCensusData.csv";

    String INDIA_STATE_CODE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    String PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.json";
    String WRONG_CSV_FILE2_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/main/resources/IndiaStateCode.csv";


    //INDIA_CENSUS_DATA_CSV_FILE
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE1_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenNotSupportedFileType_ShouldHandlNoSuchFileException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenDelimeterImproper_ShouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_whenHeaderImproper_shouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    //INDIA_STATE_DATA_CSV_FILE
    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numberOfStateCode = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_PATH);
            Assert.assertEquals(37, numberOfStateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCSV_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE2_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenIndianStateCSV_WhenNotSupportedFileType_ShouldHandlNoSuchFileException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaStateCodeData(PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_WhenDelimeterImproper_ShouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_whenHeaderImproper_shouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }
    //Sorting cases
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
