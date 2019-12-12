package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public  double populationDensity;
    public  double totalArea;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(USACensusData censusCSV) {
        state = censusCSV.state;
        stateCode= censusCSV.stateId;
        population = censusCSV.population;
        populationDensity = censusCSV.populationDensity;
        totalArea = censusCSV.totalArea;
    }

    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state, population,(int) populationDensity, (int)totalArea);
    }
}
