package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndianCensusAdapterTest {

    String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    String INDIA_STATE_CODE_PATH = "/home/admin1/Desktop/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";


    @Test
    public void givenAdapter_WhenGivenContryIndia_ShouldReturnCount() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> stringCensusDAOMap = indiaCensusAdapter.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29,stringCensusDAOMap.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }}
