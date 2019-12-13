package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String INDIA_STATE_CODE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    String USA_CENSUS_DATA = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";


    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INIDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortFields.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnPopulousState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INIDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortFields.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnMostPopulationDensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INIDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortFields.DENSITYPERSQKM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[28].state);
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnMostLargestStateByArea() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INIDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortFields.AREAINSQKM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[28].state);
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenUSACensusData_WhenSortedOnState_ShouldRturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.USA);
            censusAnalyser.loadCensusData(USA_CENSUS_DATA);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortFields.STATE);
            USACensusCSV[] usaCensusCSVS = new Gson().fromJson(sortedCensusData, USACensusCSV[].class);
            Assert.assertEquals("Alabama",usaCensusCSVS[0].state);
        } catch (CensusAnalyserException e) {}
    }
}
