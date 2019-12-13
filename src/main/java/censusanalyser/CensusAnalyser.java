package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country {INIDIA, USA};
    Map<String, CensusDAO> censusStateMap = null;
    Map<SortFields, Comparator<CensusDAO>> fieldsComparatorMap = null;
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
        this.censusStateMap = new HashMap<>();
        this.fieldsComparatorMap = new HashMap<>();
        this.fieldsComparatorMap.put(SortFields.STATE, Comparator.comparing(field -> field.state));
        this.fieldsComparatorMap.put(SortFields.POPULATION, Comparator.comparing(field -> field.population));
        this.fieldsComparatorMap.put(SortFields.AREAINSQKM, Comparator.comparing(field -> field.totalArea));
        this.fieldsComparatorMap.put(SortFields.DENSITYPERSQKM, Comparator.comparing(field -> field.populationDensity));

        Comparator<CensusDAO> populationValue = Comparator.comparing(field -> field.population);
        Comparator<CensusDAO> densityValue = Comparator.comparing(field -> field.populationDensity);
        Comparator<CensusDAO> result = populationValue.thenComparing(densityValue);
        this.fieldsComparatorMap.put(SortFields.DOUBLEVALUES, result);
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusData = CensusAdapterFactory.getCensusData(country);
        censusStateMap = censusData.loadCensusData(country, csvFilePath);
        return censusStateMap.size();
    }

    public String getSortedCensusData(SortFields sortBy) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTO = censusStateMap.values().stream()
                .sorted(this.fieldsComparatorMap.get(sortBy))
                .map(censusDAO -> censusDAO.getDAO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }
}
