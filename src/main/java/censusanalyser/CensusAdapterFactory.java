package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {

    public static CensusAdapter getCensusData(CensusAnalyser.Country country) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INIDIA)) {
            return  new IndiaCensusAdapter();
        } else if (country.equals(CensusAnalyser.Country.USA)) {
            return  new USACensusAdapter();
        }
        throw new CensusAnalyserException("Unkonwn country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

}

