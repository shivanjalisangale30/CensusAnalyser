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

    Map<String,IndiaCensusDAO> censusStateMap =null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVItearator = csvBuilder.getCSVFileIterartor(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVItearator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.state , new IndiaCensusDAO(censusCSV)));
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION );
        }
        return censusStateMap.size();
    }

    public int loadIndiaStateCodeData(String IndiaStateCodeCSV) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(IndiaStateCodeCSV));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterartor(reader, IndiaStateCodeCSV.class);
            while (stateCSVIterator.hasNext()){
                IndiaStateCodeCSV stateCSV = stateCSVIterator.next();
                IndiaCensusDAO censusDAO = censusStateMap.get(stateCSV.stateName);
                if(censusDAO == null) continue;
                censusDAO.stateCode = stateCSV.stateCode;
            }
            return this.getCount(stateCSVIterator);
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION );
        }

    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numberOfEntries;
    }

    public String getStateWiseSortedCensusData(String sortBy) throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0)
        {
            throw new CensusAnalyserException("No census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().
                collect(Collectors.toList());
        this.sort(censusDAOS,this.getComparator(sortBy));
        String sortedStateCensusJson = new Gson().toJson(censusStateMap);
        return sortedStateCensusJson;
    }
    public Comparator<IndiaCensusDAO> getComparator(String field){
        Comparator<IndiaCensusDAO> censusComparator ;
        switch (field.toLowerCase()) {
            case "state":
                censusComparator= Comparator.comparing(census -> census.state);
                break;
            case "population":
                censusComparator= Comparator.comparing(census -> census.population);
                break;
            case "area":
                censusComparator= Comparator.comparing(census -> census.areaInSqKm);
                break;
            case "density":
                censusComparator= Comparator.comparing(census -> census.densityPerSqKm);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
        }
        return censusComparator;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size()-1; i++) {
            for (int j = 0; j < censusDAOS.size()-i-1; j++) {
                IndiaCensusDAO census1 = censusDAOS.get(j);
                IndiaCensusDAO census2 = censusDAOS.get(j+1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
}