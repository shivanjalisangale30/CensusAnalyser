package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USACensusCSV {
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    public USACensusCSV() {
    }

    public USACensusCSV(String state, int population, double totalArea, double populationDensity) {
        this.state = state;
        this.stateId = stateId;
        this.population = population;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
    }

    @Override
    public String toString() {
        return "USACensusCSV{" +
                "state='" + state + '\'' +
                ", stateId='" + stateId + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
