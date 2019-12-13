package censusanalyser;

import java.util.Map;

public class USACensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        return loadCensusData(USACensusCSV.class, csvFilePath);
    }
}
