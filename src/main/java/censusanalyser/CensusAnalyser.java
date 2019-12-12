package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;


public class CensusAnalyser {

    public enum Country {INIDIA ,USA};
    Map<String, CensusDAO> censusStateMap = null;
    Map<SortFields, Comparator<CensusDAO>> fieldsComparatorMap = null;
    private Country country;

    public CensusAnalyser() {
        this.country = country;
        this.censusStateMap = new HashMap<>();
        this.fieldsComparatorMap = new HashMap<>();
        this.fieldsComparatorMap.put(SortFields.STATE, Comparator.comparing(field -> field.state));
        this.fieldsComparatorMap.put(SortFields.POPULATION, Comparator.comparing(field -> field.population));
        this.fieldsComparatorMap.put(SortFields.AREAINSQKM, Comparator.comparing(field -> field.totalArea));
        this.fieldsComparatorMap.put(SortFields.DENSITYPERSQKM, Comparator.comparing(field -> field.populationDensity));
    }

    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusStateMap.size();
    }

    public String geSortedCensusData(SortFields sortBy) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusStateMap.values().stream()
                                     .collect(Collectors.toList());


       String sortedStateCensusJson = new Gson().toJson(censusDAOS);
       return sortedStateCensusJson;
    }
}