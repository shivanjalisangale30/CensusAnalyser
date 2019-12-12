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

    String USA_CENSUS_DATA = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";

    //INDIA_CENSUS_DATA_CSV_FILE
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
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
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE1_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenNotSupportedFileType_ShouldHandlNoSuchFileException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED,INDIA_STATE_CODE_PATH);
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
            int result = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29, result);
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
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH, WRONG_CSV_FILE2_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }


    @Test
    public void givenIndianStateCSV_WhenNotSupportedFileType_ShouldHandlNoSuchFileException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_WhenDelimeterImproper_ShouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_whenHeaderImproper_shouldHandleDelimeterException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    //Sorting cases
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.geSortedCensusData(SortFields.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnPopulousState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.geSortedCensusData(SortFields.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnMostPopulationDensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.geSortedCensusData(SortFields.DENSITYPERSQKM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnMostLargestStateByArea() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.geSortedCensusData(SortFields.AREAINSQKM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenGivenEmptyFile_ShouldHandleException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData("",INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndiaStateData_WhenGivenEmptyFile_ShouldHandleException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,"");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenTwoCSVFiles_WhenOneGivenEmpty_ShouldHandleException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,"");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    //USA_CENSUS_DATA_CSV_FILE
    @Test
    public void givenUSACensusData_ShouldReturnCorrectData() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int result = censusAnalyser.loadUSACensusData(USA_CENSUS_DATA);
            Assert.assertEquals(51, result);
        } catch (CensusAnalyserException e) {}

    }
}
