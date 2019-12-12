package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {

    public static Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INIDIA)) {
            return new IndiaCensusAdapter().loadCensusData(IndiaCensusCSV.class,csvFilePath);
        } else if (country.equals(CensusAnalyser.Country.USA)) {
            return new USCensusAdapter().loadCensusData(USACensusData.class,csvFilePath);
        }
        throw new CensusAnalyserException("Unkonwn country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

}

