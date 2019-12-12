package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    Map<String, CensusDAO> censusStateMap = new HashMap<>();

    public  <E>  Map<String, CensusDAO> loadCensusData( CensusAnalyser.Country country , String... csvFilePath) throws CensusAnalyserException {
     if(country.equals(CensusAnalyser.Country.INIDIA))
         return this.loadCensusData(IndiaCensusCSV.class,csvFilePath);
     else if(country.equals(CensusAnalyser.Country.USA))
         return this.loadCensusData(USACensusData.class,csvFilePath);
     else throw new CensusAnalyserException("Incorrect Country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    private   <E>  Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVItearator = csvBuilder.getCSVFileIterartor(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVItearator;
            if (censusCSVClass.getName().equals("censusanalyser/IndiaCensusCSV.java")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusanalyser/USACensusData.java")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USACensusData.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFilePath.length == 1) return censusStateMap;
            this.loadIndiaStateCodeData(censusStateMap,csvFilePath[1]);
            return censusStateMap;
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION);
        }
    }

    public int loadIndiaStateCodeData(Map<String, CensusDAO> censusStateMap, String IndiaStateCodeCSV) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(IndiaStateCodeCSV));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterartor(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> this.censusStateMap.get(csvState.stateName) != null)
                    .forEach(csvState -> this.censusStateMap.get(csvState.stateName).stateCode = csvState.stateCode);
            return this.censusStateMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION);
        }
    }

}
