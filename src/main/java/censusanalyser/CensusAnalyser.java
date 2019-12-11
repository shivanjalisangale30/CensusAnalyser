package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
public class CensusAnalyser {

    List<IndiaCensusDAO> censusList =null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVItearator = csvBuilder.getCSVFileIterartor(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVItearator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV -> censusList.add(new IndiaCensusDAO(censusCSV)));
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMETER_EXCEPTION );
        }
        return censusList.size();
    }

    public int loadIndiaStateCodeData(String IndiaStateCodeCSV) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(IndiaStateCodeCSV));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterartor(reader, IndiaStateCodeCSV.class);
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
        if(censusList == null || censusList.size() == 0)
        {
            throw new CensusAnalyserException("No census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        this.sort(this.getComparator(sortBy));
        String sortedStateCensusJson = new Gson().toJson(censusList);
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

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for (int j = 0; j < censusList.size()-i-1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}