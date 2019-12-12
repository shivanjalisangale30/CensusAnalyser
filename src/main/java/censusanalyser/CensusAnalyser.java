package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, CensusDAO> censusStateMap = null;
    Map<SortFields, Comparator<CensusDAO>> fieldsComparatorMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
        this.fieldsComparatorMap = new HashMap<>();
        this.fieldsComparatorMap.put(SortFields.STATE, Comparator.comparing(field -> field.state));
        this.fieldsComparatorMap.put(SortFields.POPULATION, Comparator.comparing(field -> field.population));
        this.fieldsComparatorMap.put(SortFields.AREAINSQKM, Comparator.comparing(field -> field.totalArea));
        this.fieldsComparatorMap.put(SortFields.DENSITYPERSQKM, Comparator.comparing(field -> field.populationDensity));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVItearator = csvBuilder.getCSVFileIterartor(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVItearator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION);
        }
        return censusStateMap.size();
    }

    public int loadIndiaStateCodeData(String IndiaStateCodeCSV) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(IndiaStateCodeCSV));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterartor(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.stateName)!=null)
                    .forEach(csvState -> censusStateMap.get(csvState.stateName).stateCode=csvState.stateCode);
           return censusStateMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION);
        }

    }

    public int loadUSACensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USACensusData> censusCSVItearator = csvBuilder.getCSVFileIterartor(reader, USACensusData.class);
            Iterable<USACensusData> csvIterable = () -> censusCSVItearator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION);
        }
        return censusStateMap.size();
    }

    public String geSortedCensusData(SortFields sortBy) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().
                collect(Collectors.toList());
        this.sort(censusDAOS, this.fieldsComparatorMap.get(sortBy));
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
}