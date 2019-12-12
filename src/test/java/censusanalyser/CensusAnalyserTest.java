package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String PROPER_FILE1_BUT_TYPE_NOT_SUPPORTED = "./src/test/resources/IndiaStateCensusData.json";
    String WRONG_CSV_FILE1_PATH = "./src/main/resources/IndiaStateCensusData.csv";

    String INDIA_STATE_CODE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    String PROPER_FILE2_BUT_TYPE_NOT_SUPPORTED = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.json";
    String WRONG_CSV_FILE2_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/main/resources/IndiaStateCode.csv";

    String USA_CENSUS_DATA = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";

    //Sorting cases
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,"",INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndiaStateData_WhenGivenEmptyFile_ShouldHandleException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,"");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenTwoCSVFiles_WhenOneGivenEmpty_ShouldHandleException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INIDIA,INDIA_CENSUS_CSV_FILE_PATH,"");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION, e.type);
        }
    }

    @Test
    public void name() {
    }
}
